package com.example.StevesPlugin;

import com.example.Task.WalkTask;
import net.runelite.api.coords.WorldPoint;

public class Walker {
    private WalkTask currentWalkTask;

    public void walkTo(WorldPoint wp) {
        currentWalkTask = (WalkTask) new WalkTask(wp).start().onComplete((result) -> {
            currentWalkTask = null;
        });
    }

    public boolean isWalking() {
        return currentWalkTask != null;
    }
}
