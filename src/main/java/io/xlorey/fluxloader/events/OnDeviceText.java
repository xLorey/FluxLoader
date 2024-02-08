package io.xlorey.fluxloader.events;

import zombie.inventory.types.Radio;
import zombie.iso.objects.IsoWaveSignal;
import zombie.vehicles.VehiclePart;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a media device is displaying text.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnDeviceText extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnDeviceText"; }
    
    /**
    * Called Event Handling Method
    * @param interactCodes The interaction codes of the media device.
    * @param x The x coordinate of the media device displaying the text.
    * @param y The y coordinate of the media device displaying the text.
    * @param z The z coordinate of the media device displaying the text.
    * @param line The line of text which the media device is displaying.
    * @param device The media device that is displaying the text.
    */
    public void handleEvent(String interactCodes, Float x, Float y, Float z, String line, IsoWaveSignal device) {}

    /**
     * Called Event Handling Method
     * @param interactCodes The interaction codes of the media device.
     * @param x The x coordinate of the media device displaying the text.
     * @param y The y coordinate of the media device displaying the text.
     * @param z The z coordinate of the media device displaying the text.
     * @param line The line of text which the media device is displaying.
     * @param device The media device that is displaying the text.
     */
    public void handleEvent(String interactCodes, Float x, Float y, Float z, String line, Radio device) {}

    /**
     * Called Event Handling Method
     * @param interactCodes The interaction codes of the media device.
     * @param x The x coordinate of the media device displaying the text.
     * @param y The y coordinate of the media device displaying the text.
     * @param z The z coordinate of the media device displaying the text.
     * @param line The line of text which the media device is displaying.
     * @param device The media device that is displaying the text.
     */
    public void handleEvent(String interactCodes, Float x, Float y, Float z, String line, VehiclePart device) {}
}
