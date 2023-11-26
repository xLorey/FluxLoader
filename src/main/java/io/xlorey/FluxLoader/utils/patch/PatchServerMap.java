package io.xlorey.FluxLoader.utils.patch;

/**
 * Embedding in the server map
 */
public class PatchServerMap extends PatchFile{
    /**
     * Injector constructor
     */
    public PatchServerMap() {
        super("network/ServerMap.class");
    }

    /**
     * Injection into a game file
     */
    @Override
    public void inject() throws Exception {
        super.inject();

        PatchTools.injectEventInvoker(filePath, "QueueQuit", "onServerShutdown", false);
    }
}
