package io.xlorey.fluxloader.patches;

import io.xlorey.fluxloader.shared.EventManager;
import io.xlorey.fluxloader.utils.PatchTools;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: BanSteamIDCommand command patcher
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class PatchBanSteamIDCommand extends PatchFile{
    /**
     * Patcher constructor
     * @param className the full class name, e.g. 'zombie.GameWindow'
     */
    public PatchBanSteamIDCommand(String className) {
        super(className);
    }

    /**
     * The patch method, which is called to make changes to the class
     */
    @Override
    public void patch() {
        PatchTools.patchMethod(getClassName(), "Command", (ctClass, ctMethod) -> {
            try {
                ctMethod.instrument(new ExprEditor() {
                    public void edit(MethodCall m) throws CannotCompileException {
                        if (m.getClassName().equals("zombie.core.raknet.UdpConnection") && m.getMethodName().equals("forceDisconnect")) {
                            String code =  "{ "
                                    + "zombie.characters.IsoPlayer player = io.xlorey.fluxloader.server.api.PlayerUtils.getPlayerByUdpConnection($0);"
                                    + "if (player != null) {"
                                    + EventManager.class.getName() + ".invokeEvent(\"onPlayerBan\", new Object[]{player, \"\"});"
                                    + "}"
                                    + "$proceed($$);"
                                    + "}";
                            m.replace(code);
                        }
                    }
                });
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
