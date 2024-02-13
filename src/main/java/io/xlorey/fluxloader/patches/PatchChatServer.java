package io.xlorey.fluxloader.patches;

import io.xlorey.fluxloader.shared.EventManager;
import io.xlorey.fluxloader.utils.PatchTools;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: ChatServer patcher
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class PatchChatServer extends PatchFile{
    /**
     * Patcher constructor
     * @param className the full class name, e.g. 'zombie.GameWindow'
     */
    public PatchChatServer(String className) {
        super(className);
    }

    /**
     * The patch method, which is called to make changes to the class
     */
    @Override
    public void patch() {
        PatchTools.patchMethod(getClassName(), "processMessageFromPlayerPacket", (ctClass, ctMethod) -> {
            try {
                ctMethod.instrument(new ExprEditor() {
                    public void edit(MethodCall m) throws CannotCompileException {
                        if (m.getMethodName().equals("unpackMessage")) {
                            m.replace("{ $_ = $proceed($$); " +
                                    "zombie.chat.ChatBase base = (zombie.chat.ChatBase)this.chats.get(new Integer($1.rewind().getInt()));"
                                    + EventManager.class.getName() + ".invokeEvent(\"onChatMessageProcessed\", new Object[]{base, $_}); }");
                        }
                    }
                });
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
