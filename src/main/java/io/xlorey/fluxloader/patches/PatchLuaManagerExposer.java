package io.xlorey.fluxloader.patches;

import io.xlorey.fluxloader.shared.LuaExposer;
import io.xlorey.fluxloader.utils.PatchTools;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 27.02.2024
 * Description: LuaManager$Exposer patcher
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class PatchLuaManagerExposer extends PatchFile{
    /**
     * Patcher constructor
     * @param className the full class name, e.g. 'zombie.GameWindow'
     */
    public PatchLuaManagerExposer(String className) {
        super(className);
    }

    /**
     * The patch method, which is called to make changes to the class
     */
    @Override
    public void patch() {
        PatchTools.patchMethod(getClassName(), "exposeAll", (ctClass, ctMethod) -> {
            try {
                String classCode =
                        "{" +
                        "java.util.Set exposedClasses = " + LuaExposer.class.getName() + ".getExposedClasses();" +
                        "java.util.Iterator iterator = exposedClasses.iterator();" +
                        "while(iterator.hasNext()) {" +
                        "java.lang.Class clazz = (java.lang.Class) iterator.next();" +
                        "this.setExposed(clazz);" +
                        "}" +
                        "}";
                ctMethod.insertBefore(classCode);

                ctMethod.instrument(new ExprEditor() {
                    public void edit(MethodCall m) throws CannotCompileException {
                        if (m.getMethodName().equals("exposeGlobalFunctions")) {
                            String code =  "{" +
                                        "java.util.Set exposedObjects = " + LuaExposer.class.getName() + ".getExposedGlobalObjects();" +
                                        "java.util.Iterator iterator = exposedObjects.iterator();" +
                                        "while(iterator.hasNext()) {" +
                                        "java.lang.Object object = (java.lang.Object) iterator.next();" +
                                        "this.exposeGlobalFunctions(object);" +
                                        "}" +
                                        "$proceed($$);" +
                                        "}";
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