package com.example.Script.Combat;

import java.util.ArrayList;
import java.util.HashMap;

public class CombatSpotRequirements {
    private ArrayList<CombatSpot> spots = new ArrayList<>();
    private HashMap<CombatSkillType, Integer> levelRequirements = new HashMap<>();
    private final long minTimeEachSpot;
    private final long maxTimeEachSpot;
    private final boolean pickRandomSpot;

    public CombatSpotRequirements(ArrayList<CombatSpot> spots, HashMap<CombatSkillType, Integer> levelRequirements, long minTimeEachSpot, long maxTimeEachSpot, boolean pickRandomSpot) {
        this.spots = spots;
        this.levelRequirements = levelRequirements;
        this.minTimeEachSpot = minTimeEachSpot;
        this.maxTimeEachSpot = maxTimeEachSpot;
        this.pickRandomSpot = pickRandomSpot;
    }

    public ArrayList<CombatSpot> getSpots() {
        return spots;
    }

    public HashMap<CombatSkillType, Integer> getLevelRequirements() {
        return levelRequirements;
    }

    public long getMinTimeEachSpot() {
        return minTimeEachSpot;
    }

    public long getMaxTimeEachSpot() {
        return maxTimeEachSpot;
    }

    public boolean isPickRandomSpot() {
        return pickRandomSpot;
    }
}
