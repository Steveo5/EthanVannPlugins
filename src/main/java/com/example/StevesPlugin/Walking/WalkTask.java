package com.example.StevesPlugin.Walking;

import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.PathFinder.PathFinder;
import com.example.PathFinder.Util;
import com.example.PathFinder.WebFinder;
import com.example.PathFinder.WebWalkNode;
import com.example.StevesPlugin.StevesPlugin;
import com.example.StevesPlugin.Task;
import com.example.StevesPlugin.TileMarkerOverlay;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WalkTask extends Task {
    private List<WorldPoint> activeWebPath = new ArrayList<>();
    List<WorldPoint> pathToActiveNode = new ArrayList<>();
    private List<WebWalkNode> path = new ArrayList<>();
    private int activeNodeIndex = 1;

    TileMarkerOverlay tileMarkerOverlay;
    private PathFinder pathFinder;
    private WebFinder webFinder;
    private WorldPoint target;

    public WalkTask(TileMarkerOverlay tileMarkerOverlay,
                    PathFinder pathFinder,
                    WebFinder webFinder,
                    WorldPoint target) {
        this.pathFinder = pathFinder;
        this.webFinder = webFinder;
        this.target = target;
        this.tileMarkerOverlay = tileMarkerOverlay;
        activeWebPath = webFinder.getWebWalkPath(EthanApiPlugin.playerPosition(), target);
    }

    private WorldPoint getNextClickableTile() {
        WorldPoint clickablePoint = null;

        for (int i=this.pathToActiveNode.size()-1; i>0; i--) {
            if (this.pathToActiveNode.get(i).isInScene(EthanApiPlugin.getClient())) {
                clickablePoint = this.pathToActiveNode.get(i);

                break;
            }
        }

        return clickablePoint;
    }

    @Override
    public int onLoop() {
        WorldPoint currentNode = this.activeWebPath.get(this.activeNodeIndex - 1);

        if (currentNode != null) {
            pathToActiveNode = this.pathFinder.find(EthanApiPlugin.playerPosition(), currentNode);

            if (EthanApiPlugin.playerPosition().distanceTo(currentNode) < 3) {
                this.activeNodeIndex++;

                if (this.activeNodeIndex > this.activeWebPath.size()) {
                    this.activeNodeIndex = this.activeWebPath.size();
                }
            }

            if (EthanApiPlugin.playerPosition().distanceTo(target) < 20) {
                System.out.println("Made it near enough to target to be successful");
                pathToActiveNode = this.pathFinder.find(EthanApiPlugin.playerPosition(), this.target);
            }

            tileMarkerOverlay.resetHighlighted();
            tileMarkerOverlay.drawTiles(this.activeWebPath, Color.BLUE);
            tileMarkerOverlay.drawTiles(pathToActiveNode, Color.ORANGE);

            if (!EthanApiPlugin.isMoving()) {
                LocalPoint clickablePoint = LocalPoint.fromWorld(EthanApiPlugin.getClient(), getNextClickableTile());
                StevesPlugin.getInstance().getMouse().setMousePos(Perspective.localToCanvas(EthanApiPlugin.getClient(), clickablePoint, 0));
                StevesPlugin.getInstance().getMouse().doLeftClick();
            }
        }

        if (EthanApiPlugin.playerPosition().distanceTo(target) < 3) {
            System.out.println("Made it near enough to target to be successful");
            tileMarkerOverlay.resetHighlighted();
            this.stop();
        }
        return Util.randomInteger(200, 800);
    }
}
