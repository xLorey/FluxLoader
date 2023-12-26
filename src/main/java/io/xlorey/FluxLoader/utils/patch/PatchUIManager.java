package io.xlorey.FluxLoader.utils.patch;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

/**
 * Embedding in the UI Manager
 */
public class PatchUIManager extends PatchFile{
    /**
     * Injector constructor
     */
    public PatchUIManager() {
        super("ui/UIManager.class");
    }

    /**
     * Injection into a game file
     */
    @Override
    public void inject() throws Exception {
        super.inject();

        PatchTools.injectIntoClass(filePath, "update", true, methodNode -> {
            InsnList instructions = methodNode.instructions;
            AbstractInsnNode currentNode = instructions.getFirst();

            while (currentNode != null) {
                if (currentNode instanceof MethodInsnNode && currentNode.getOpcode() == Opcodes.INVOKESTATIC) {
                    MethodInsnNode methodInsn = (MethodInsnNode) currentNode;
                    if ("zombie/input/Mouse".equals(methodInsn.owner) && "isLeftPressed".equals(methodInsn.name)) {
                        InsnList toInject = new InsnList();
                        toInject.add(new MethodInsnNode(
                                Opcodes.INVOKESTATIC,
                                "io/xlorey/FluxLoader/client/core/WidgetManager",
                                "isMouseCapture",
                                "()Z",
                                false
                        ));
                        LabelNode continueLabel = new LabelNode();
                        toInject.add(new JumpInsnNode(Opcodes.IFEQ, continueLabel));
                        toInject.add(new InsnNode(Opcodes.RETURN));
                        toInject.add(continueLabel);

                        instructions.insertBefore(currentNode, toInject);

                        break;
                    }
                }
                currentNode = currentNode.getNext();
            }
        });
    }
}
