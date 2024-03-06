package io.xlorey.fluxloader.patches;

import io.xlorey.fluxloader.client.core.Core;
import io.xlorey.fluxloader.client.core.StateManager;
import io.xlorey.fluxloader.shared.EventManager;
import io.xlorey.fluxloader.utils.Constants;
import io.xlorey.fluxloader.utils.Logger;
import io.xlorey.fluxloader.utils.PatchTools;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Game window patcher
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class PatchGameWindow extends PatchFile{
    /**
     * Patcher constructor
     * @param className the full class name, e.g. 'zombie.GameWindow'
     */
    public PatchGameWindow(String className) {
        super(className);
    }

    /**
     * The patch method, which is called to make changes to the class
     */
    @Override
    public void patch() {
        // Basic initialization of FluxLoader
        PatchTools.patchMethod(getClassName(), "init", (ctClass, ctMethod) -> {
            try {
                ctMethod.insertBefore("{" +
                        Logger.class.getName() + ".printCredits();" +
                        Logger.class.getName() + ".print(\"FluxLoader Core initialization for the client...\");" +
                        "}");
                ctMethod.instrument(new ExprEditor() {
                    public void edit(MethodCall m) throws CannotCompileException {
                        if (m.getClassName().contains("LuaManager") && m.getMethodName().equals("init")) {
                            String code =  "DoLoadingText(\"Loading Flux Loader\"); " + Core.class.getName() + ".init();";
                            m.replace("{" +
                                    "$proceed($$);" +
                                    code +
                                    "}");
                        }
                    }
                });
                ctMethod.insertAfter(StateManager.class.getName() + ".init();");
                ctMethod.insertAfter(EventManager.class.getName() + ".invokeEvent(\"onGameWindowInitialized\", new Object[0]);");
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });

        // Changing the window title
        PatchTools.patchMethod(getClassName(), "InitDisplay", (ctClass, ctMethod) -> {
            try {
                ctMethod.instrument(new ExprEditor() {
                    public void edit(MethodCall m) throws CannotCompileException {
                        if (m.getClassName().equals("org.lwjglx.opengl.Display") && m.getMethodName().equals("setTitle")) {
                            String newTitle =  String.format("\"Project Zomboid with %s (v%s)\"", Constants.FLUX_NAME, Constants.FLUX_VERSION);
                            m.replace("$proceed(" + newTitle + ");");
                        }
                    }
                });
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
