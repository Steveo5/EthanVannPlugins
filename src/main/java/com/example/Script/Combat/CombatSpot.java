package com.example.Script.Combat;

import com.example.StevesPlugin.TileMarkerOverlay;
import com.google.inject.Inject;
import net.runelite.api.Tile;
import net.runelite.api.coords.WorldPoint;

import java.util.ArrayList;

public class CombatSpot {
    private WorldPoint base;
    private int baseTileRadius;
    private ArrayList<String> npcNames;

    public CombatSpot(WorldPoint base, int baseTileRadius, ArrayList<String> npcNames) {
        this.base = base;
        this.baseTileRadius = baseTileRadius;
        this.npcNames = npcNames;
    }

    public WorldPoint getBase() {
        return base;
    }

    public int getBaseTileRadius() {
        return baseTileRadius;
    }

    public ArrayList<String> getNpcNames() {
        return npcNames;
    }
}
