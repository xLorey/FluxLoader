package io.xlorey.FluxLoader.utils.patch;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

/**
 * Embedding in the chat server logic
 */
public class PatchChatServer extends PatchFile{
    /**
     * Injector constructor
     */
    public PatchChatServer() {
        super("network/chat/ChatServer.class");
    }

    /**
     * Injection into a game file
     */
    @Override
    public void inject() throws Exception {
        super.inject();

        PatchTools.injectIntoClass(filePath, "processMessageFromPlayerPacket", false, method -> {
            AbstractInsnNode targetNode = null;
            for (AbstractInsnNode instruction : method.instructions.toArray()) {
                if (instruction instanceof VarInsnNode && instruction.getOpcode() == Opcodes.ASTORE) {
                    VarInsnNode varInsnNode = (VarInsnNode) instruction;
                    if (varInsnNode.var == 5) {
                        targetNode = instruction.getNext();
                        break;
                    }
                }
            }

            if (targetNode != null) {
                InsnList eventInvoker = new InsnList();

                eventInvoker.add(new InsnNode(Opcodes.ICONST_2));
                eventInvoker.add(new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"));

                eventInvoker.add(new InsnNode(Opcodes.DUP));
                eventInvoker.add(new InsnNode(Opcodes.ICONST_0));
                eventInvoker.add(new VarInsnNode(Opcodes.ALOAD, 4));
                eventInvoker.add(new InsnNode(Opcodes.AASTORE));

                eventInvoker.add(new InsnNode(Opcodes.DUP));
                eventInvoker.add(new InsnNode(Opcodes.ICONST_1));
                eventInvoker.add(new VarInsnNode(Opcodes.ALOAD, 5));
                eventInvoker.add(new InsnNode(Opcodes.AASTORE));

                eventInvoker.add(new LdcInsnNode("onChatServerMessage"));
                eventInvoker.add(new InsnNode(Opcodes.SWAP));
                eventInvoker.add(new MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "io/xlorey/FluxLoader/shared/EventManager",
                        "invokeEvent",
                        "(Ljava/lang/String;[Ljava/lang/Object;)V",
                        false
                ));

                method.instructions.insert(targetNode, eventInvoker);
            }
        });
    }
}
