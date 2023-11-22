package io.xlorey.FluxLoader.utils.patch;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;


/**
 * Embedding in the lua event manager
 */
public class PatchLuaEventManager extends PatchFile{
    /**
     * Injector constructor
     */
    public PatchLuaEventManager() {
        super("Lua/LuaEventManager.class");
    }

    /**
     * Injection into a game file
     */
    @Override
    public void inject() throws Exception {
        super.inject();

        PatchTools.injectIntoClass(filePath, "triggerEvent", true, method -> {
            Type[] argumentTypes = Type.getArgumentTypes(method.desc);
            InsnList eventInvoker = new InsnList();

            eventInvoker.add(new VarInsnNode(Opcodes.ALOAD, 0));

            if (argumentTypes.length > 1) {
                eventInvoker.add(new IntInsnNode(Opcodes.BIPUSH, argumentTypes.length - 1));
                eventInvoker.add(new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"));

                for (int i = 1; i < argumentTypes.length; i++) {
                    eventInvoker.add(new InsnNode(Opcodes.DUP));
                    eventInvoker.add(new IntInsnNode(Opcodes.BIPUSH, i - 1));
                    eventInvoker.add(new VarInsnNode(Opcodes.ALOAD, i));
                    eventInvoker.add(new InsnNode(Opcodes.AASTORE));
                }
            } else {
                eventInvoker.add(new InsnNode(Opcodes.ACONST_NULL));
            }

            eventInvoker.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "io/xlorey/FluxLoader/shared/EventManager", "invokeEvent", "(Ljava/lang/String;[Ljava/lang/Object;)V", false));

            method.instructions.insert(eventInvoker);
        });
    }
}
