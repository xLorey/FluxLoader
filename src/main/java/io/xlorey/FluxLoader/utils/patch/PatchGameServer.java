package io.xlorey.FluxLoader.utils.patch;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

/**
 * Embedding in the game server logic
 */
public class PatchGameServer extends PatchFile{
    /**
     * Injector constructor
     */
    public PatchGameServer() {
        super("network/GameServer.class");
    }

    /**
     * Injection into a game file
     */
    @Override
    public void inject() throws Exception {
        super.inject();

        PatchTools.injectIntoClass(filePath, "main", true, method -> {
            Type[] argumentTypes = Type.getArgumentTypes(method.desc);

            /*
              Init Flux Loader
             */
            AbstractInsnNode targetInsn = null;

            for (AbstractInsnNode currentNode = method.instructions.getFirst(); currentNode != null; currentNode = currentNode.getNext()) {
                if (currentNode instanceof MethodInsnNode methodInsnNode) {
                    if (methodInsnNode.owner.equals("zombie/debug/DebugLog") && methodInsnNode.name.equals("init")) {
                        targetInsn = currentNode;
                        break;
                    }
                }
            }

            if (targetInsn != null) {
                InsnList toInject = new InsnList();

                toInject.add(new FieldInsnNode(
                        Opcodes.GETSTATIC,
                        "zombie/network/GameServer",
                        "bCoop",
                        "Z"
                ));

                LabelNode skipInitLabel = new LabelNode();
                toInject.add(new JumpInsnNode(Opcodes.IFNE, skipInitLabel));

                toInject.add(new MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "io/xlorey/FluxLoader/server/core/Core",
                        "init",
                        "()V",
                        false
                ));

                toInject.add(skipInitLabel);

                method.instructions.insertBefore(targetInsn, toInject);
            }

            /*
              Event onServerInitialize
             */
            AbstractInsnNode currentNode = method.instructions.getFirst();
            while (currentNode != null) {
                if (currentNode instanceof MethodInsnNode methodCall) {
                    if (methodCall.owner.contains("GlobalObject") && methodCall.name.equals("refreshAnimSets")) {
                        InsnList eventInvoker = PatchTools.createEventInvokerInsnList("onServerInitialize", argumentTypes, true);
                        method.instructions.insert(currentNode, eventInvoker);
                        break;
                    }
                }
                currentNode = currentNode.getNext();
            }

        });
        PatchTools.injectEventInvokerWithLocalField(filePath, "receiveReceiveCommand", 3, "onSendChatCommand", new int[] {1, 3}, true);
        PatchTools.injectIntoClass(filePath, "handleServerCommand", true, method -> {
            InsnList toInject = new InsnList();

            toInject.add(new VarInsnNode(Opcodes.ALOAD, 1));
            LabelNode labelIfNotNull = new LabelNode();
            toInject.add(new JumpInsnNode(Opcodes.IFNONNULL, labelIfNotNull));

            toInject.add(new LdcInsnNode("onSendConsoleCommand"));
            toInject.add(new InsnNode(Opcodes.ICONST_1));
            toInject.add(new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"));
            toInject.add(new InsnNode(Opcodes.DUP));
            toInject.add(new InsnNode(Opcodes.ICONST_0));
            toInject.add(new VarInsnNode(Opcodes.ALOAD, 0));
            toInject.add(new InsnNode(Opcodes.AASTORE));
            toInject.add(new MethodInsnNode(
                    Opcodes.INVOKESTATIC,
                    "io/xlorey/FluxLoader/shared/EventManager",
                    "invokeEvent",
                    "(Ljava/lang/String;[Ljava/lang/Object;)V",
                    false
            ));

            toInject.add(new InsnNode(Opcodes.ACONST_NULL));
            toInject.add(new VarInsnNode(Opcodes.ALOAD, 0));
            toInject.add(new MethodInsnNode(
                    Opcodes.INVOKESTATIC,
                    "io/xlorey/FluxLoader/shared/CommandsManager",
                    "handleCustomCommand",
                    "(Lzombie/core/raknet/UdpConnection;Ljava/lang/String;)Ljava/lang/String;",
                    false
            ));

            toInject.add(new InsnNode(Opcodes.DUP));
            LabelNode labelIfResultNotNull = new LabelNode();
            toInject.add(new JumpInsnNode(Opcodes.IFNULL, labelIfResultNotNull));

            toInject.add(new InsnNode(Opcodes.ARETURN));

            toInject.add(labelIfResultNotNull);
            toInject.add(new InsnNode(Opcodes.POP));

            toInject.add(labelIfNotNull);

            method.instructions.insert(toInject);
        });
        PatchTools.injectIntoClass(filePath, "receiveReceiveCommand", true, method -> {
            AbstractInsnNode targetNode = null;
            for (AbstractInsnNode currentNode = method.instructions.getFirst(); currentNode != null; currentNode = currentNode.getNext()) {
                if (currentNode instanceof VarInsnNode) {
                    VarInsnNode varInsnNode = (VarInsnNode) currentNode;
                    if (varInsnNode.getOpcode() == Opcodes.ALOAD && varInsnNode.var == 4) {
                        AbstractInsnNode checkNode = currentNode.getNext();
                        if (checkNode instanceof JumpInsnNode && checkNode.getOpcode() == Opcodes.IFNONNULL) {
                            targetNode = currentNode;
                            break;
                        }
                    }
                }
            }

            if (targetNode != null) {
                InsnList toInject = new InsnList();
                toInject.add(new VarInsnNode(Opcodes.ALOAD, 1));
                toInject.add(new VarInsnNode(Opcodes.ALOAD, 3));
                toInject.add(new MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "io/xlorey/FluxLoader/shared/CommandsManager",
                        "handleCustomCommand",
                        "(Lzombie/core/raknet/UdpConnection;Ljava/lang/String;)Ljava/lang/String;",
                        false
                ));
                toInject.add(new VarInsnNode(Opcodes.ASTORE, 4));

                method.instructions.insertBefore(targetNode, toInject);
            }
        });
        PatchTools.injectIntoClass(filePath, "receivePlayerConnect", true, method -> {
            Type[] argumentTypes = Type.getArgumentTypes(method.desc);

            if (argumentTypes.length >= 3 && argumentTypes[2].getDescriptor().equals("Ljava/lang/String;")) {
                InsnList eventInvoker = PatchTools.createEventInvokerInsnList("onPlayerConnect", argumentTypes, true);
                method.instructions.insertBefore(method.instructions.getFirst(), eventInvoker);
            }
        });
        PatchTools.injectEventInvoker(filePath, "disconnectPlayer", "onPlayerDisconnect", true);
        PatchTools.injectEventInvoker(filePath, "addIncoming", "onAddIncoming", true);
    }
}
