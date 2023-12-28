package io.xlorey.FluxLoader.utils.patch;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

/**
 * Embedding in the BanSteamID Command
 */
public class PatchBanSteamIDCommand extends PatchFile{
    /**
     * Injector constructor
     */
    public PatchBanSteamIDCommand() {
        super("commands/serverCommands/BanSteamIDCommand.class");
    }

    /**
     * Injection into a game file
     */
    @Override
    public void inject() throws Exception {
        super.inject();

        PatchTools.injectIntoClass(filePath, "Command", false, method -> {
            AbstractInsnNode targetInst = method.instructions.getFirst();
            while (targetInst != null) {
                if (targetInst instanceof MethodInsnNode methodCall) {
                    if (methodCall.owner.contains("UdpConnection") && methodCall.name.equals("forceDisconnect")) {
                        InsnList playerGetter = new InsnList();
                        playerGetter.add(new VarInsnNode(Opcodes.ALOAD, 5));
                        playerGetter.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                                "io/xlorey/FluxLoader/server/api/PlayerUtils",
                                "getPlayerByUdpConnection",
                                "(Lzombie/core/raknet/UdpConnection;)Lzombie/characters/IsoPlayer;",
                                false));
                        playerGetter.add(new VarInsnNode(Opcodes.ASTORE, 7));
                        method.instructions.insertBefore(targetInst, playerGetter);

                        InsnList invokeEvent = new InsnList();
                        invokeEvent.add(new LdcInsnNode("onPlayerBan"));
                        invokeEvent.add(new InsnNode(Opcodes.ICONST_2));
                        invokeEvent.add(new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"));
                        invokeEvent.add(new InsnNode(Opcodes.DUP));
                        invokeEvent.add(new InsnNode(Opcodes.ICONST_0));
                        invokeEvent.add(new VarInsnNode(Opcodes.ALOAD, 7));
                        invokeEvent.add(new InsnNode(Opcodes.AASTORE));
                        invokeEvent.add(new InsnNode(Opcodes.DUP));
                        invokeEvent.add(new InsnNode(Opcodes.ICONST_1));
                        invokeEvent.add(new LdcInsnNode(""));
                        invokeEvent.add(new InsnNode(Opcodes.AASTORE));
                        invokeEvent.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                                "io/xlorey/FluxLoader/shared/EventManager",
                                "invokeEvent",
                                "(Ljava/lang/String;[Ljava/lang/Object;)V",
                                false));

                        method.instructions.insertBefore(targetInst, invokeEvent);
                        break;
                    }
                }
                targetInst = targetInst.getNext();
            }
        });
    }
}
