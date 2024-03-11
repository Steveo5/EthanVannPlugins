package com.example.StevesPlugin.Walking;

import net.runelite.api.coords.WorldPoint;

public class NamedLocation {
    private String name;
    private WorldPoint location;

    public NamedLocation(String name, WorldPoint location) {

        this.name = name;
        this.location = location;
    }

    public WorldPoint getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}
