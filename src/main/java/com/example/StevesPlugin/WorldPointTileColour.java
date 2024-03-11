package com.example.StevesPlugin;

import net.runelite.api.coords.WorldPoint;

import java.awt.*;

public class WorldPointTileColour {
    private WorldPoint worldPoint;
    private Color color;

    public WorldPointTileColour(WorldPoint worldPoint, Color color) {
        this.worldPoint = worldPoint;
        this.color = color;
    }

    public WorldPoint getWorldPoint() {
        return worldPoint;
    }

    public Color getColor() {
        return color;
    }
}
