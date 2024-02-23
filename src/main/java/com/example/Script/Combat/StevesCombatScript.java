package com.example.Script.Combat;

import com.example.Script.Combat.CombatSpotRequirements;
import com.example.Script.Script;

import java.util.ArrayList;
import java.util.Random;

public class StevesCombatScript extends Script {
    private ArrayList<CombatSpotRequirements> spotRequirements = new ArrayList<>();
    private boolean pickupDrops = true;
    private boolean bankFullInv = true;
    private ArrayList<String> food = new ArrayList<>();
    private boolean useBestFood = true;

    public StevesCombatScript() {
        super("StevesCombat");
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {

    }

//    private List<WorldPoint> findPath(Node start, Node destination) {
//        Queue<Node> queue;
//        queue.add(start);
//        start.visited = true;
//        while (!queue.isEmpty()) {
//            Node current = queue.peek();
//
//            if (current.getData().equals(destination.getData())) {
//                return queue;
//            }
//
//            for (Node neighbor : current.getNeighbours())
//                if !neighbor.visited()
//                queue.enqueue(neighbor)
//                neighbor.visited = true
//        }
//    }

    @Override
    public int onLoop() {
        System.out.println("Loop");

        return new Random().nextInt(4000 - 1000 + 1) + 1000;
    }
//    @Override
//    public void startUp() {
//        StevenScriptManager.registerScript(this);
//    }
//    @Override
//    public void onStart() {
//
//    }
//
//    @Override
//    public void onStop() {
//
//    }
//
//    @Override
//    public int onLoop() {
//        log.info("Looped2");
//        return new Random().nextInt(4000 - 1000 + 1) + 1000;
//    }
}
