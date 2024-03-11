package com.example.StevesPlugin.Walking;

import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.EthanApiPlugin.PathFinding.GlobalCollisionMap;
import com.example.PathFinder.*;
import com.example.StevesPlugin.TileMarkerOverlay;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.runelite.api.coords.WorldPoint;

import java.awt.*;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Singleton
public class WalkerImpl implements Walker {
    private WalkTask currentWalkTask;
    private CollisionMap map;
    private final Map<WorldPoint, List<WorldPoint>> transports = new HashMap<>();
    private final List<NamedLocation> namedLocations = new ArrayList<>();
    private WebWalkMap webWalkMap;

    @Inject
    TileMarkerOverlay tileMarkerOverlay;

    public WalkerImpl() {
        System.out.println("Walker initialized");
        this.webWalkMap = new WebWalkMap();
        Map<SplitFlagMap.Position, byte[]> compressedRegions = new HashMap<>();

        try (ZipInputStream in = new ZipInputStream(GlobalCollisionMap.class.getResourceAsStream("collision-map.zip"))) {
            ZipEntry entry;
            while ((entry = in.getNextEntry()) != null) {
                String[] n = entry.getName().split("_");

                compressedRegions.put(
                        new SplitFlagMap.Position(Integer.parseInt(n[0]), Integer.parseInt(n[1])),
                        Util.readAllBytes(in)
                );
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        map = new CollisionMap(64, compressedRegions);

        try {
            String s = new String(Util.readAllBytes(GlobalCollisionMap.class.getResourceAsStream("transports.txt")), StandardCharsets.UTF_8);
            Scanner scanner = new Scanner(s);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }

                String[] l = line.split(" ");
                WorldPoint a = new WorldPoint(Integer.parseInt(l[0]), Integer.parseInt(l[1]), Integer.parseInt(l[2]));
                WorldPoint b = new WorldPoint(Integer.parseInt(l[3]), Integer.parseInt(l[4]), Integer.parseInt(l[5]));
                transports.computeIfAbsent(a, k -> new ArrayList<>()).add(b);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            String s = new String(Util.readAllBytes(GlobalCollisionMap.class.getResourceAsStream("named_locations.txt")), StandardCharsets.UTF_8);
            Scanner scanner = new Scanner(s);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }

                String[] l = line.split(",");
                String[] wp = l[1].split(" ");
                namedLocations.add(new NamedLocation(l[0], new WorldPoint(Integer.parseInt(wp[0]), Integer.parseInt(wp[1]), Integer.parseInt(wp[2]))));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public void walkTo(WorldPoint wp) {
//        PathFinder pathFinder = new PathFinder(this.map, this.transports, EthanApiPlugin.playerPosition(), wp, true);
//
//        if (currentWalkTask != null) {
//            currentWalkTask.stop();
//        }
//
//        this.currentWalkTask = new WalkTask(tileMarkerOverlay, pathFinder);
//
//        this.currentWalkTask.start().onComplete((result) -> {
//            currentWalkTask = null;
//        });
//
//    }

//    @Override
//    public boolean walkTo(String name) {
//        for (NamedLocation namedLocation : namedLocations) {
//            if (namedLocation.getName().equalsIgnoreCase(name)) {
//                this.walkTo(namedLocation.getLocation());
//                return true;
//            }
//        }
//
//        return false;
//    }

    @Override
    public boolean isWalking() {
        return currentWalkTask != null;
    }

    public void webWalkTo(WorldPoint location) {
        PathFinder pathFinder = new PathFinder(this.map, this.transports, EthanApiPlugin.playerPosition(), location, true);
        WebFinder webFinder = new WebFinder(this.webWalkMap);

        WalkTask walkTask = new WalkTask(this.tileMarkerOverlay, pathFinder, webFinder, location);
        walkTask.start().onComplete(success -> {

        });
    }
}
