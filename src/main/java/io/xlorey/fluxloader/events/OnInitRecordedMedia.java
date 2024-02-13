package io.xlorey.fluxloader.events;

import zombie.radio.media.RecordedMedia;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a media is being recorded.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnInitRecordedMedia extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnInitRecordedMedia"; }
    
    /**
    * Called Event Handling Method
    * @param recordedMedia The recorded media to be initialized.
    */
    public void handleEvent(RecordedMedia recordedMedia) {}
}
