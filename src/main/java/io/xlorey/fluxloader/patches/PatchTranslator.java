package io.xlorey.fluxloader.patches;

import io.xlorey.fluxloader.shared.EventManager;
import io.xlorey.fluxloader.utils.PatchTools;
import javassist.CannotCompileException;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 29.02.2024
 * Description: Translator patcher
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class PatchTranslator extends PatchFile{
    /**
     * Patcher constructor
     * @param className the full class name, e.g. 'zombie.GameWindow'
     */
    public PatchTranslator(String className) {
        super(className);
    }

    /**
     * The patch method, which is called to make changes to the class
     */
    @Override
    public void patch() {
        PatchTools.patchMethod(getClassName(), "setLanguage", "zombie.core.Language", (ctClass, ctMethod) -> {
            try {
                String classCode =
                        "{" +
                         EventManager.class.getName() + ".invokeEvent(\"onChangeLanguage\", new Object[]{$1});" +
                        "}";
                ctMethod.insertAfter(classCode);
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });
    }
}