package io.xlorey.FluxLoader.utils.patch;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;


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
            InsnList loaderInit = new InsnList();
            loaderInit.add(new MethodInsnNode(
                    Opcodes.INVOKESTATIC,
                    "io/xlorey/FluxLoader/server/core/Core",
                    "init",
                    "()V",
                    false
            ));

            method.instructions.insert(loaderInit);
        });
    }
}
