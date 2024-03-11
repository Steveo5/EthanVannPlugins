package com.example.Script.Combat;

import com.example.Script.Script;
import com.example.StevesPlugin.Walking.Walker;
import net.runelite.api.coords.WorldPoint;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Random;

public class StevesCombatScript extends Script {
    private ArrayList<CombatSpotRequirements> spotRequirements = new ArrayList<>();
    private boolean pickupDrops = true;
    private boolean bankFullInv = true;
    private ArrayList<String> food = new ArrayList<>();
    private boolean useBestFood = true;

    @Inject
    public Walker walker;

    public StevesCombatScript() {
        super("StevesCombat");
        System.out.println("The script wasintialized");
    }

    @Override
    public void onStart() {
        this.walker.webWalkTo(new WorldPoint(3259, 3336, 0));
    }

    @Override
    public void onStop() {

    }


    @Override
    public int onLoop() {

//        if (this.walker != null && !walker.isWalking()) {
//            walker.walkTo("varrock_east_bank");
//        }

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
