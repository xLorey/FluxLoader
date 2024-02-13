package io.xlorey.fluxloader.client.handlers;

import io.xlorey.fluxloader.client.core.WidgetManager;
import io.xlorey.fluxloader.events.OnPostTickRenderThread;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 09.02.2024
 * Description: Tick handler from render thread
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class OnPostTickRenderThreadHandler extends OnPostTickRenderThread{
    @Override
    public void handleEvent() {
        WidgetManager.drawImGui();
    }
}
