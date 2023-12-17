package io.xlorey.FluxLoader.utils.patch;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;

/**
 * Embedding in the SpriteRenderer logic
 */
public class PatchSpriteRenderer extends PatchFile{
    /**
     * Injector constructor
     */
    public PatchSpriteRenderer() {
        super("core/SpriteRenderer.class");
    }

    /**
     * Injection into a game file
     */
    @Override
    public void inject() throws Exception {
        super.inject();

        PatchTools.injectIntoClass(filePath, "postRender", false, method -> {
            InsnList toInject = new InsnList();
            toInject.add(new MethodInsnNode(
                    Opcodes.INVOKESTATIC,
                    "io/xlorey/FluxLoader/client/core/EventsHandler",
                    "onDrawWithRenderThreadHandler",
                    "()V",
                    false
            ));

            method.instructions.insertBefore(method.instructions.getLast(), toInject);
        });
    }
}
