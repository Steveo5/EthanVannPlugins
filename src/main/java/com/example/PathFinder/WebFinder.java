package com.example.PathFinder;

import net.runelite.api.coords.WorldPoint;

import java.util.ArrayList;
import java.util.List;

public class WebFinder {
    private WebWalkMap webWalkMap;

    public WebFinder(WebWalkMap webWalkMap) {
        this.webWalkMap = webWalkMap;
    }

    public WebWalkNode getNodeAt(WorldPoint location) {
        return this.webWalkMap.getWebWalkNodes().stream()
                .filter(webWalkNode -> webWalkNode.getLocation().equals(location))
                .findFirst()
                .orElse(null);
    }

    public List<WebWalkNode> getConnectedNodes(WebWalkNode node, WebWalkNode... excluded) {
        List<WebWalkNode> connectedNodes = new ArrayList<>();

        Outer:
        for (WebWalkLine line : this.webWalkMap.getWebWalkLines()) {
            for (WebWalkNode excludedNode : excluded) {
                if (line.getWp1().equals(excludedNode.getLocation()) || line.getWp2().equals(excludedNode.getLocation()))
                    continue Outer;
            }

            if (line.getWp1().equals(node.getLocation())) {
                connectedNodes.add(this.getNodeAt(line.getWp2()));
            }

            if (line.getWp2().equals(node.getLocation())) {
                connectedNodes.add(this.getNodeAt(line.getWp1()));
            }
        }

        return connectedNodes;
    }

    public WebWalkNode getClosestNode(WorldPoint location) {
        return this.webWalkMap.getWebWalkNodes().stream()
                .reduce((result, next) -> location.distanceTo(result.getLocation()) < location.distanceTo(next.getLocation()) ? result : next)
                .orElse(null);
    }

    private WebWalkNode getNextWalkNode(List<WebWalkNode> history, WebWalkNode currentNode, WebWalkNode target) {
        return this.getConnectedNodes(currentNode, history.toArray(new WebWalkNode[0])).stream()
                .reduce((result, next) -> target.getLocation().distanceTo(result.getLocation()) < target.getLocation().distanceTo(next.getLocation()) ? result : next)
                .orElse(target);
    }

    public List<WorldPoint> getWebWalkPath(WorldPoint from, WorldPoint to) {
        WebWalkNode currentNode = this.getClosestNode(from);
        WebWalkNode toNode = this.getClosestNode(to);
        List<WorldPoint> path = new ArrayList<>();

        if (currentNode.getLocation().equals(to)) {
            path.add(currentNode.getLocation());

            return path;
        }

        List<WebWalkNode> history = new ArrayList<>();
        while (currentNode.getLocation().distanceTo(to) > 20) {
            WebWalkNode nextNode = getNextWalkNode(history, currentNode, toNode);
            path.add(nextNode.getLocation());

            history.add(currentNode);
            currentNode = nextNode;
        }

        return path;
    }
}
