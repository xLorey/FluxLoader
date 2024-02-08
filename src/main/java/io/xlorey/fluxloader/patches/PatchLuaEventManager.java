package io.xlorey.fluxloader.patches;

import io.xlorey.fluxloader.shared.EventManager;
import io.xlorey.fluxloader.utils.PatchTools;
import javassist.CannotCompileException;
import javassist.NotFoundException;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Lua Event Manager patcher
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class PatchLuaEventManager extends PatchFile{
    /**
     * Patcher constructor
     * @param className the full class name, e.g. 'zombie.GameWindow'
     */
    public PatchLuaEventManager(String className) {
        super(className);
    }

    /**
     * The patch method, which is called to make changes to the class
     */
    @Override
    public void patch() {
        // The maximum number of arguments that the triggerEvent method can accept
        int maxArgs = 8;

        for (int argCount = 0; argCount <= maxArgs; argCount++) {
            String signature  = "java.lang.String" + ", java.lang.Object".repeat(Math.max(0, argCount));

            PatchTools.patchMethod(getClassName(), "triggerEvent", signature, (ctClass, ctMethod) -> {
                try {
                    StringBuilder code = new StringBuilder("{ ");
                    code.append("Object[] args = new Object[").append(ctMethod.getParameterTypes().length - 1).append("]; ");
                    for (int i = 2; i <= ctMethod.getParameterTypes().length; i++) {
                        code.append("args[").append(i - 2).append("] = $").append(i).append("; ");
                    }
                    code.append(EventManager.class.getName()).append(".invokeEvent($1, args); }");

                    ctMethod.insertBefore(code.toString());
                } catch (CannotCompileException | NotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
