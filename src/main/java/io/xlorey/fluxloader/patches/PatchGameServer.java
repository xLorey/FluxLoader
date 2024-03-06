package io.xlorey.fluxloader.patches;

import io.xlorey.fluxloader.server.core.CommandsManager;
import io.xlorey.fluxloader.server.core.Core;
import io.xlorey.fluxloader.shared.EventManager;
import io.xlorey.fluxloader.utils.Logger;
import io.xlorey.fluxloader.utils.PatchTools;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.util.Arrays;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Game Server patcher
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class PatchGameServer extends PatchFile{
    /**
     * Patcher constructor
     * @param className the full class name, e.g. 'zombie.GameWindow'
     */
    public PatchGameServer(String className) {
        super(className);
    }

    /**
     * The patch method, which is called to make changes to the class
     */
    @Override
    public void patch() {
        // Adding a server initialization event and starting the Flux Loader core
        PatchTools.patchMethod(getClassName(), "main", (ctClass, ctMethod) -> {
            try {
                ctMethod.insertBefore("{" +
                        "if (" + Arrays.class.getName() + ".asList($1).contains(\"-coop\")) {" +
                        Logger.class.getName() + ".print(\"Launching a co-op server...\");" +
                        "} else {" +
                        Logger.class.getName() + ".printCredits();" +
                        "}" +
                        Logger.class.getName() + ".print(\"FluxLoader Core initialization for the server..\");" +
                        "}");

                ctMethod.instrument(new ExprEditor() {
                    public void edit(MethodCall m) throws CannotCompileException {
                        if (m.getClassName().contains("Translator") && m.getMethodName().equals("loadFiles")) {
                            m.replace("{" +
                                    "$proceed($$);" +
                                    Core.class.getName() + ".init();" +
                                    "}");
                        }
                    }
                });

                ctMethod.instrument(new ExprEditor() {
                    public void edit(MethodCall m) throws CannotCompileException {
                        if (m.getClassName().contains("GlobalObject") && m.getMethodName().equals("refreshAnimSets")) {
                            m.replace("{ $proceed($$);" +
                                    EventManager.class.getName() + ".invokeEvent(\"onServerInitialize\", new Object[0]); }");
                        }
                    }
                });
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });

        // Event of receiving new packages from the player
        PatchTools.patchMethod(getClassName(), "addIncoming", (ctClass, ctMethod) -> {
            try {
                ctMethod.insertBefore( "{ " +
                        "java.nio.ByteBuffer bb = $2.duplicate();" +
                        EventManager.class.getName() + ".invokeEvent(\"onAddIncoming\", new Object[]{new java.lang.Short($1), bb, $3}); " +
                        "}");
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });

        // Event of sending a command to the console
        PatchTools.patchMethod(getClassName(), "handleServerCommand", (ctClass, ctMethod) -> {
            try {
                ctMethod.insertBefore( "{ " +
                        "if($1 != null) {" +
                        EventManager.class.getName() + ".invokeEvent(\"onSendConsoleCommand\", new Object[]{$1}); " +
                        "java.lang.String customResult = " + CommandsManager.class.getName() + ".handleCustomCommand($2, $1);" +
                        "if (customResult != null) return customResult;"+
                        "}" +
                        "}");
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });

        // Player connection event to server
        PatchTools.patchMethod(getClassName(), "receivePlayerConnect", "java.nio.ByteBuffer, zombie.core.raknet.UdpConnection, java.lang.String", (ctClass, ctMethod) -> {
            try {
                ctMethod.insertBefore( "{ " +
                        "java.nio.ByteBuffer bb = $1.duplicate();" +
                        EventManager.class.getName() + ".invokeEvent(\"onPlayerConnect\", new Object[]{bb, $2, $3}); " +
                        "}");
                ctMethod.insertAfter( "{ " +
                        "java.nio.ByteBuffer bb = $1.rewind().duplicate();" +
                        EventManager.class.getName() + ".invokeEvent(\"onPlayerFullyConnected\", new Object[]{bb, $2, $3}); " +
                        "}");
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });

        // Event when a player disconnects from the server
        PatchTools.patchMethod(getClassName(), "disconnectPlayer", "zombie.characters.IsoPlayer, zombie.core.raknet.UdpConnection", (ctClass, ctMethod) -> {
            try {
                ctMethod.insertBefore( "{ " +
                        EventManager.class.getName() + ".invokeEvent(\"onPlayerDisconnect\", $args); " +
                        "}");
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });

        // Chat message sending event
        PatchTools.patchMethod(getClassName(), "receiveReceiveCommand", (ctClass, ctMethod) -> {
            try {
                String code = "{ java.lang.String readString = zombie.GameWindow.ReadString($1);" +
                        EventManager.class.getName() + ".invokeEvent(\"onSendChatCommand\", new Object[]{$2, readString});" +
                        "java.lang.String handleCommand = " + CommandsManager.class.getName() + ".handleCustomCommand($2, readString);" +
                        "if (handleCommand == null) {" +
                        "    handleCommand = handleClientCommand(readString.substring(1), $2);" +
                        "}" +
                        "if (handleCommand == null) {" +
                        "    handleCommand = handleServerCommand(readString.substring(1), $2);" +
                        "}" +
                        "if (handleCommand == null) {" +
                        "    handleCommand = \"Unknown command \" + readString;" +
                        "}" +
                        "if (handleCommand.isEmpty()) return;" +
                        "zombie.network.chat.ChatServer.getInstance().sendMessageToServerChat($2, handleCommand);" +
                        "}";
                ctMethod.setBody(code);
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
