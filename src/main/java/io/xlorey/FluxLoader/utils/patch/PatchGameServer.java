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
            /*
              Init Flux Loader
             */
            InsnList loaderInit = new InsnList();
            loaderInit.add(new MethodInsnNode(
                    Opcodes.INVOKESTATIC,
                    "io/xlorey/FluxLoader/server/core/Core",
                    "init",
                    "()V",
                    false
            ));
            method.instructions.insert(loaderInit);

            /*
              Event onServerInitialize
             */
            AbstractInsnNode currentNode = method.instructions.getFirst();
            while (currentNode != null) {
                if (currentNode instanceof MethodInsnNode) {
                    MethodInsnNode methodCall = (MethodInsnNode) currentNode;
                    if (methodCall.owner.contains("GlobalObject") && methodCall.name.equals("refreshAnimSets")) {
                        InsnList eventInvoker = new InsnList();
                        eventInvoker.add(new LdcInsnNode("onServerInitialize"));
                        eventInvoker.add(new MethodInsnNode(
                                Opcodes.INVOKESTATIC,
                                "io/xlorey/FluxLoader/shared/EventManager",
                                "invokeEvent",
                                "(Ljava/lang/String;)V",
                                false
                        ));
                        method.instructions.insert(currentNode, eventInvoker);
                        break;
                    }
                }
                currentNode = currentNode.getNext();
            }
        });

        PatchTools.injectIntoClass(filePath, "receivePlayerConnect", true, method -> {
            Type[] argumentTypes = Type.getArgumentTypes(method.desc);

            if (argumentTypes.length >= 3 && argumentTypes[2].getDescriptor().equals("Ljava/lang/String;")) {
                InsnList eventInvoker = new InsnList();

                eventInvoker.add(new LdcInsnNode("onPlayerConnect"));

                eventInvoker.add(new IntInsnNode(Opcodes.BIPUSH, argumentTypes.length));
                eventInvoker.add(new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"));

                for (int i = 0; i < argumentTypes.length; i++) {
                    eventInvoker.add(new InsnNode(Opcodes.DUP));
                    eventInvoker.add(new IntInsnNode(Opcodes.BIPUSH, i));
                    eventInvoker.add(new VarInsnNode(Opcodes.ALOAD, i));
                    eventInvoker.add(new InsnNode(Opcodes.AASTORE));
                }

                eventInvoker.add(new MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "io/xlorey/FluxLoader/shared/EventManager",
                        "invokeEvent",
                        "(Ljava/lang/String;[Ljava/lang/Object;)V",
                        false));

                method.instructions.insertBefore(method.instructions.getFirst(), eventInvoker);
            }
        });

        PatchTools.injectIntoClass(filePath, "disconnectPlayer", true, method -> {
            Type[] argumentTypes = Type.getArgumentTypes(method.desc);
            InsnList eventInvoker = new InsnList();

            eventInvoker.add(new LdcInsnNode("onPlayerDisconnect"));

            eventInvoker.add(new IntInsnNode(Opcodes.BIPUSH, argumentTypes.length));
            eventInvoker.add(new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"));

            for (int i = 0; i < argumentTypes.length; i++) {
                eventInvoker.add(new InsnNode(Opcodes.DUP));
                eventInvoker.add(new IntInsnNode(Opcodes.BIPUSH, i));
                eventInvoker.add(new VarInsnNode(Opcodes.ALOAD, i));
                eventInvoker.add(new InsnNode(Opcodes.AASTORE));
            }

            eventInvoker.add(new MethodInsnNode(
                    Opcodes.INVOKESTATIC,
                    "io/xlorey/FluxLoader/shared/EventManager",
                    "invokeEvent",
                    "(Ljava/lang/String;[Ljava/lang/Object;)V",
                    false));

            method.instructions.insertBefore(method.instructions.getFirst(), eventInvoker);
        });
    }
}
