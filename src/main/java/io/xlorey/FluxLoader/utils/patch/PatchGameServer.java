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
            InsnList initFluxLoader = new InsnList();
            initFluxLoader.add(new MethodInsnNode(
                    Opcodes.INVOKESTATIC,
                    "io/xlorey/FluxLoader/server/core/Core",
                    "init",
                    "()V",
                    false
            ));
            method.instructions.insert(initFluxLoader);

            /*
              Event onServerInitialize
             */
            AbstractInsnNode targetServerInit = method.instructions.getFirst();
            while (targetServerInit != null) {
                if (targetServerInit instanceof MethodInsnNode methodCall) {
                    if (methodCall.owner.contains("GlobalObject") && methodCall.name.equals("refreshAnimSets")) {
                        InsnList eventInvoker = PatchTools.createEventInvokerInsnList("onServerInitialize", argumentTypes, true);
                        method.instructions.insert(targetServerInit, eventInvoker);
                        break;
                    }
                }
                targetServerInit = targetServerInit.getNext();
            }


            /*
              Event onServerShutDown
             */
            AbstractInsnNode targetServerShutdown = method.instructions.getFirst();
            while (targetServerShutdown != null) {
                if (targetServerShutdown instanceof MethodInsnNode methodCall) {
                    if (methodCall.owner.contains("System") && methodCall.name.equals("exit")) {
                        InsnList eventInvoker = PatchTools.createEventInvokerInsnList("onServerShutdown", new Type[]{}, true);
                        method.instructions.insertBefore(targetServerShutdown, eventInvoker);
                    }
                }
                targetServerShutdown = targetServerShutdown.getNext();
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
                    "io/xlorey/FluxLoader/server/core/CommandsManager",
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
                        "io/xlorey/FluxLoader/server/core/CommandsManager",
                        "handleCustomCommand",
                        "(Lzombie/core/raknet/UdpConnection;Ljava/lang/String;)Ljava/lang/String;",
                        false
                ));
                toInject.add(new VarInsnNode(Opcodes.ASTORE, 4));

                method.instructions.insertBefore(targetNode, toInject);
            }
        });
        PatchTools.injectIntoClass(filePath, "receiveReceiveCommand", true, method -> {
            InsnList instructions = method.instructions;
            AbstractInsnNode currentNode = instructions.getFirst();

            while (currentNode != null) {
                if (currentNode instanceof MethodInsnNode) {
                    MethodInsnNode methodInsnNode = (MethodInsnNode) currentNode;
                    if (methodInsnNode.owner.equals("zombie/network/chat/ChatServer") &&
                            methodInsnNode.name.equals("sendMessageToServerChat")) {

                        LabelNode continueLabel = new LabelNode();
                        InsnList toInject = new InsnList();
                        toInject.add(new VarInsnNode(Opcodes.ALOAD, 4));
                        toInject.add(new MethodInsnNode(
                                Opcodes.INVOKEVIRTUAL,
                                "java/lang/String",
                                "isEmpty",
                                "()Z",
                                false
                        ));
                        toInject.add(new JumpInsnNode(Opcodes.IFEQ, continueLabel));
                        toInject.add(new InsnNode(Opcodes.RETURN));
                        toInject.add(continueLabel);

                        instructions.insertBefore(currentNode, toInject);
                    }
                }
                currentNode = currentNode.getNext();
            }
        });
        PatchTools.injectIntoClass(filePath, "receivePlayerConnect", true, method -> {
            Type[] argumentTypes = Type.getArgumentTypes(method.desc);

            if (argumentTypes.length >= 3 && argumentTypes[2].getDescriptor().equals("Ljava/lang/String;")) {
                InsnList onPlayerConnectInvoker = PatchTools.createEventInvokerInsnList("onPlayerConnect", argumentTypes, true);
                method.instructions.insertBefore(method.instructions.getFirst(), onPlayerConnectInvoker);

                AbstractInsnNode currentNode = method.instructions.getFirst();
                while (currentNode != null) {
                    if (currentNode instanceof MethodInsnNode) {
                        MethodInsnNode methodInsnNode = (MethodInsnNode) currentNode;
                        if (methodInsnNode.getOpcode() == Opcodes.INVOKEVIRTUAL && methodInsnNode.owner.equals("zombie/network/ServerLOS") && methodInsnNode.name.equals("addPlayer")) {
                            InsnList onPlayerFullyConnectedInvoker = PatchTools.createEventInvokerInsnList("onPlayerFullyConnected", argumentTypes, true);
                            method.instructions.insert(currentNode.getNext(), onPlayerFullyConnectedInvoker);
                            break;
                        }
                    }
                    currentNode = currentNode.getNext();
                }
            }
        });
        PatchTools.injectEventInvoker(filePath, "disconnectPlayer", "onPlayerDisconnect", true);
        PatchTools.injectIntoClass(filePath, "addIncoming", true, method -> {
            InsnList toInject = new InsnList();

            toInject.add(new VarInsnNode(Opcodes.ALOAD, 1));
            toInject.add(new MethodInsnNode(
                    Opcodes.INVOKESTATIC,
                    "io/xlorey/FluxLoader/server/api/IncomingPacket",
                    "checkAndResetPacketBlocking",
                    "(Ljava/nio/ByteBuffer;)Z",
                    false
            ));

            LabelNode continueLabel = new LabelNode();
            toInject.add(new JumpInsnNode(Opcodes.IFEQ, continueLabel));
            toInject.add(new InsnNode(Opcodes.RETURN));
            toInject.add(continueLabel);

            method.instructions.insert(toInject);
        });
        PatchTools.injectEventInvoker(filePath, "addIncoming", "onAddIncoming", true);
        PatchTools.injectIntoClass(filePath, "addIncoming", true, method -> {
            InsnList instructions = method.instructions;
            InsnList markInstructions = new InsnList();
            markInstructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
            markInstructions.add(new MethodInsnNode(
                    Opcodes.INVOKEVIRTUAL,
                    "java/nio/ByteBuffer",
                    "mark",
                    "()Ljava/nio/ByteBuffer;",
                    false
            ));
            markInstructions.add(new InsnNode(Opcodes.POP));

            instructions.insert(markInstructions);

            AbstractInsnNode currentNode = instructions.getFirst();
            while (currentNode != null) {
                if (currentNode instanceof MethodInsnNode) {
                    MethodInsnNode methodInsnNode = (MethodInsnNode) currentNode;
                    if (methodInsnNode.owner.equals("io/xlorey/FluxLoader/shared/EventManager") &&
                            methodInsnNode.name.equals("invokeEvent")) {

                        InsnList resetInstructions = new InsnList();
                        resetInstructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        resetInstructions.add(new MethodInsnNode(
                                Opcodes.INVOKEVIRTUAL,
                                "java/nio/ByteBuffer",
                                "reset",
                                "()Ljava/nio/ByteBuffer;",
                                false
                        ));
                        resetInstructions.add(new InsnNode(Opcodes.POP));
                        instructions.insert(currentNode, resetInstructions);

                        break;
                    }
                }
                currentNode = currentNode.getNext();
            }
        });
    }
}
