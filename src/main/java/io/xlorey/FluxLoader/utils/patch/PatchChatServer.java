package io.xlorey.FluxLoader.utils.patch;

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

        PatchTools.injectEventInvoker(filePath, "processMessageFromPlayerPacket", "onChatServerMessage", false);
        PatchTools.injectEventInvoker(filePath, "processPlayerStartWhisperChatPacket", "onChatWhisperMessage", false);
    }
}
