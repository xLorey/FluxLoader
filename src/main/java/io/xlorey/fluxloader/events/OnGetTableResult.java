package io.xlorey.fluxloader.events;

import java.util.ArrayList;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the game client is receiving a table result from the server.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnGetTableResult extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnGetTableResult"; }
    
    /**
    * Called Event Handling Method
    * @param result The row data of the table result.
    * @param rowId The row identifier of the table result.
    * @param tableName The name of the table result.
    */
    public void handleEvent(ArrayList result, Integer rowId, String tableName) {}
}
