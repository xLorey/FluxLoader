package io.xlorey.fluxloader.client.handlers;

import io.xlorey.fluxloader.client.core.WidgetManager;
import io.xlorey.fluxloader.events.OnPostUIDraw;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 10.02.2024
 * Description: Handling the render event after the UI has been rendered
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class OnPostUIDrawHandler extends OnPostUIDraw {
    @Override
    public void handleEvent() {
        WidgetManager.drawCredits();
    }
}
