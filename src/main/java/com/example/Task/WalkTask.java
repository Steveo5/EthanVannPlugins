package com.example.Task;

import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.Packets.MousePackets;
import com.example.Packets.MovementPackets;
import com.example.PathFinder.PathFinder;
import com.example.PathFinder.Util;
import com.example.StevesPlugin.StevesPlugin;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class WalkTask extends Task {
    private WorldPoint targetLocation;
    private List<WorldPoint> fullPath;
    private int currentPointIndex = 0;

    public WalkTask(WorldPoint targetLocation) {
        this.targetLocation = targetLocation;

        fullPath = new PathFinder(StevesPlugin.getInstance().getMap(), StevesPlugin.getInstance().getTransports(), EthanApiPlugin.playerPosition(), targetLocation, true).find();

        StevesPlugin.getInstance().getTileMarkerOverlay().drawTiles(fullPath, Color.BLUE);
    }

    @Override
    public int onLoop() {
        if (fullPath.size() > 0) {
            if (currentPointIndex == fullPath.size() - 1 && isNearPathingGoal(fullPath.get(currentPointIndex))) {
                onCompleteCallback.accept(true);
                StevesPlugin.getInstance().getTileMarkerOverlay().drawTiles(new ArrayList<>(), Color.BLUE);
                this.stop();
            }

            int newPointIndex = currentPointIndex + Util.randomInteger(5, 10);

            if (newPointIndex >= fullPath.size()) {
                newPointIndex = fullPath.size() - 1;
            }

            if (isNearPathingGoal(fullPath.get(newPointIndex)) || !EthanApiPlugin.isMoving()) {
                Point p = Perspective.localToCanvas(EthanApiPlugin.getClient(), LocalPoint.fromWorld(EthanApiPlugin.getClient(), fullPath.get(newPointIndex)), fullPath.get(newPointIndex).getPlane());
                StevesPlugin.getInstance().getMouse().setMousePos(p);
                MousePackets.queueClickPacket();
                MovementPackets.queueMovement(fullPath.get(newPointIndex));

                this.currentPointIndex = newPointIndex;
            }
        }

        return Util.randomInteger(200, 800);
    }

    public boolean isNearPathingGoal(WorldPoint goal) {
        return goal.distanceTo2D(EthanApiPlugin.playerPosition()) <= 3;
    }
}
