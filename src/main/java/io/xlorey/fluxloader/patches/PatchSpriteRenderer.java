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
 * Description: SpriteRenderer patcher
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class PatchSpriteRenderer extends PatchFile{
    /**
     * Patcher constructor
     * @param className the full class name, e.g. 'zombie.GameWindow'
     */
    public PatchSpriteRenderer(String className) {
        super(className);
    }

    /**
     * The patch method, which is called to make changes to the class
     */
    @Override
    public void patch() {
        PatchTools.patchMethod(getClassName(), "postRender", (ctClass, ctMethod) -> {
            try {
                ctMethod.insertAfter(EventManager.class.getName() + ".invokeEvent(\"onPostTickRenderThread\", new Object[0]);");
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
