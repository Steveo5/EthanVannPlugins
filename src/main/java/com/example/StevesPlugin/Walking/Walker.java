package com.example.StevesPlugin.Walking;

import com.google.inject.Singleton;
import net.runelite.api.coords.WorldPoint;

public interface Walker {
//    void walkTo(WorldPoint wp);
    void webWalkTo(WorldPoint wp);
//    boolean walkTo(String name);
    boolean isWalking();
}
