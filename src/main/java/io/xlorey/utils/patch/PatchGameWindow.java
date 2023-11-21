package io.xlorey.utils.patch;

import io.xlorey.utils.Constants;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;


/**
 * Embedding in the game window
 */
public class PatchGameWindow extends PatchFile{
    /**
     * Injector constructor
     */
    public PatchGameWindow() {
        super("GameWindow.class");
    }

    /**
     * Injection into a game file
     */
    @Override
    public void inject() throws Exception {
        super.inject();

        PatchTools.injectIntoClass(filePath, "InitDisplay",true, method -> {
            String oldTitle = "Project Zomboid";
            String newTitle = String.format("Project Zomboid by %s (v%s)",  Constants.FLUX_NAME, Constants.FLUX_VERSION);

            for (AbstractInsnNode insn : method.instructions.toArray()) {
                if (insn instanceof LdcInsnNode ldcInsnNode) {
                    if (ldcInsnNode.cst.equals(oldTitle)) {
                        ldcInsnNode.cst = newTitle;
                    }
                }
            }
        });

        PatchTools.injectIntoClass(filePath, "init", true, method -> {
            AbstractInsnNode lastInsn = method.instructions.getLast();
            if (lastInsn.getOpcode() == Opcodes.RETURN) {
                InsnList loaderInit = new InsnList();
                loaderInit.add(new MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "io/xlorey/core/Core",
                        "getInstance",
                        "()Lio/xlorey/core/Core;",
                        false
                ));
                loaderInit.add(new MethodInsnNode(
                        Opcodes.INVOKEVIRTUAL,
                        "io/xlorey/core/Core",
                        "init",
                        "()V",
                        false
                ));
                method.instructions.insertBefore(lastInsn, loaderInit);
            } else {
                throw new IllegalStateException("Cannot find RETURN instruction in the method");
            }
        });
    }
}
