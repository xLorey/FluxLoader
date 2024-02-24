package io.xlorey.fluxloader.shared.api;

import lombok.experimental.UtilityClass;
import zombie.iso.IsoObject;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 24.02.2024
 * Description: A set of tools for working with isometric objects
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
@UtilityClass
public class IsoObjectUtils {
    /**
     * Calculates the distance between two objects in isometric space. Height distance is ignored (axis Z).
     * @param object1 The first object (object of type IsoObject) whose coordinates are used to calculate the distance.
     * @param object2 The second object (object of type IsoObject) whose coordinates are used to calculate the distance.
     * @return The distance between two objects in isometric space.
     */
    public static float getDistance(IsoObject object1, IsoObject object2) {
        return (float) Math.sqrt(Math.pow(object1.getX() - object2.getX(), 2) + Math.pow(object1.getY() - object2.getY(), 2));
    }
}
