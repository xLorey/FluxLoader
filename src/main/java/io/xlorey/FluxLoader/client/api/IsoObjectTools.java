package io.xlorey.FluxLoader.client.api;

import zombie.core.Core;
import zombie.iso.IsoCamera;
import zombie.iso.IsoObject;
import zombie.iso.IsoUtils;

/**
 * The IsoObjectTools class provides utilities for working with IsoObjects.
 */
public class IsoObjectTools {
    /**
     * Calculates the distance between two IsoObjects.
     * This method calculates the Euclidean distance between two points in 2D space,
     * represented by IsoObjects. The distance is calculated based on their
     * X and Y coordinates.
     * @param object1 The first IsoObject.
     * @param object2 Second IsoObject.
     * @return A real number representing the distance between object1 and object2.
     */
    public static float getDistanceBetweenObjects(IsoObject object1, IsoObject object2) {
        float dx = object1.getX() - object2.getX();
        float dy = object1.getY() - object2.getY();
        return (float) Math.sqrt((dx * dx) + (dy * dy));
    }

    /**
     * Gets the screen X coordinate for a given object in the game world.
     * @param object The IsoObject in the game world.
     * @return X coordinate of the object on the screen.
     */
    public static float getScreenPositionX(IsoObject object) {
        int playerIndex = IsoCamera.frameState.playerIndex;
        float posScreenX = IsoUtils.XToScreen(object.getX(), object.getY(), object.getZ(), 0);
        float scale = Core.getInstance().getZoom(playerIndex);
        return (posScreenX - IsoCamera.getOffX()) / scale;
    }

    /**
     * Gets the screen Y coordinate for a given object in the game world.
     * @param object The IsoObject in the game world.
     * @return Y coordinate of the object on the screen.
     */
    public static float getScreenPositionY(IsoObject object) {
        int playerIndex = IsoCamera.frameState.playerIndex;
        float posScreenY = IsoUtils.YToScreen(object.getX(), object.getY(), object.getZ(), 0);
        float scale = Core.getInstance().getZoom(playerIndex);
        return ((posScreenY - IsoCamera.getOffY()) - ((float) 128 / ((float) 2 / Core.TileScale))) / scale;
    }
}
