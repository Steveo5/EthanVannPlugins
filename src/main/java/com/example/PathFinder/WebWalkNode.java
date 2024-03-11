package com.example.PathFinder;

import net.runelite.api.coords.WorldPoint;

public class WebWalkNode {
    private WorldPoint location;

    protected WebWalkNode(WorldPoint location) {
        this.location = location;
    }

    public WorldPoint getLocation() {
        return location;
    }
}
