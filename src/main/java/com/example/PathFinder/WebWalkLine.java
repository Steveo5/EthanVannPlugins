package com.example.PathFinder;

import net.runelite.api.coords.WorldPoint;

public class WebWalkLine {
    private WorldPoint wp1;
    private WorldPoint wp2;

    protected WebWalkLine(WorldPoint wp1, WorldPoint wp2) {
        this.wp1 = wp1;
        this.wp2 = wp2;
    }

    public WorldPoint getWp1() {
        return this.wp1;
    }

    public WorldPoint getWp2() {
        return this.wp2;
    }
}
