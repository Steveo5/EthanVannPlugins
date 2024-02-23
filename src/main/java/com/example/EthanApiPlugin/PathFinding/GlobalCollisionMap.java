package com.example.EthanApiPlugin.PathFinding;

import com.example.EthanApiPlugin.EthanApiPlugin;
import com.google.common.base.Strings;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.RuneLite;
import org.roaringbitmap.RoaringBitmap;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class GlobalCollisionMap {
    static RoaringBitmap bitmap = init();

    static byte[] load() {
        try {
            InputStream is = GlobalCollisionMap.class.getResourceAsStream("map");
            return new GZIPInputStream(is).readAllBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RoaringBitmap init() {
        RoaringBitmap bitmap = new RoaringBitmap();
        try {
            bitmap.deserialize(ByteBuffer.wrap(load()));
            bitmap.runOptimize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bitmap;
    }

    public static boolean east(WorldPoint wp) {
        return bitmap.contains(packed(wp) | (1 << 30));
    }

    public static boolean north(WorldPoint wp) {
        return bitmap.contains(packed(wp));
    }

    public static boolean south(WorldPoint wp) {
        return north(wp.dy(-1));
    }

    public static boolean west(WorldPoint wp) {
        return east(wp.dx(-1));
    }

    public static int packed(int x, int y, int plane) {
        return (x & 16383) | ((y & 16383) << 14) | (plane << 28);
    }

    public static WorldPoint unpack(int packed) {
        return new WorldPoint(packed & 16383, (packed >> 14) & 16383, packed >> 28);
    }

    public static int packed(WorldPoint wp) {
        return (wp.getX() & 16383) | ((wp.getY() & 16383) << 14) | (wp.getPlane() << 28);
    }

    private static boolean objectCompCheck(ObjectComposition comp)
    {
        return (comp != null && !Strings.isNullOrEmpty(comp.getName()) && !comp.getName().equals("null"));
    }

    private static boolean gameObjectCheck(TileObject object)
    {
        return object != null && object.getLocalLocation().distanceTo(EthanApiPlugin.getClient().getLocalPlayer().getLocalLocation()) <= 30;
    }

    private static ObjectComposition replaceObjectImposters(ObjectComposition comp)
    {
        return comp.getImpostorIds() != null ? comp.getImpostor() : comp;
    }

    public static List<WorldPoint> getNeighbours(WorldPoint wp) {
        List<WorldPoint> points = new ArrayList<>();

        points.add(wp.dy(1).dx(1));
        points.add(wp.dy(1).dx(-1));
        points.add(wp.dy(-1).dx(1));
        points.add(wp.dy(-1).dx(-1));
        points.add(wp.dx(-1));
        points.add(wp.dx(1));
        points.add(wp.dy(-1));
        points.add(wp.dy(1));

        return points;
    }

    public static WorldPoint getClosestNeighbour(WorldPoint current, WorldPoint target) {

        int lastClosest = 10000000;
        WorldPoint closest = null;
        for (WorldPoint test : getNeighbours(current)) {
            LocalPoint testLocal = LocalPoint.fromWorld(EthanApiPlugin.getClient(), test);

            if (testLocal != null) {
                Tile tileAtTest = EthanApiPlugin.getClient().getScene().getTiles()[test.getPlane()][testLocal.getSceneX()][testLocal.getSceneY()];

                if (tileAtTest != null) {
                    System.out.println("The tile isnt null");
                    WallObject wallObject = tileAtTest.getWallObject();

                    if (gameObjectCheck(wallObject))
                    {
                        int objectId = wallObject.getId();
                        ObjectComposition comp = replaceObjectImposters(EthanApiPlugin.getClient().getObjectDefinition(objectId));

                        if (objectCompCheck(comp))
                        {
                            continue;
                        }
                    }

                }
            }

            int distanceTo = test.distanceTo(target);
            if (distanceTo < lastClosest) {
                lastClosest = distanceTo;
                closest = test;
            }
        }

        return closest;
    }

    public static List<WorldPoint> findPath(WorldPoint p) {
        long start = System.currentTimeMillis();
        WorldPoint starting = EthanApiPlugin.getClient().getLocalPlayer().getWorldLocation();
        HashSet<WorldPoint> visited = new HashSet<>();
        ArrayDeque<Node> queue = new ArrayDeque<Node>();
        queue.add(new Node(starting));
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            WorldPoint currentData = current.getData();
            if (currentData.equals(p)) {
                List<WorldPoint> ret = new ArrayList<>();
                while (current != null) {
                    ret.add(current.getData());
                    current = current.getPrevious();
                }
                Collections.reverse(ret);
                ret.remove(0);
                System.out.println("Path took " + (System.currentTimeMillis() - start) + "ms");
                return ret;
            }

            if (getClosestNeighbour(currentData, p) != null) {
                queue.add(new Node(getClosestNeighbour(currentData, p), current));
            }

//            // northeast
//            if (north(currentData) && east(currentData) && visited.add(currentData.dy(1).dx(1))) {
//                queue.add(new Node(currentData.dy(1).dx(1), current));
//            }
//
//            // northwest
//            if (north(currentData) && west(currentData) && visited.add(currentData.dy(1).dx(-1))) {
//                queue.add(new Node(currentData.dy(1).dx(-1), current));
//            }
//
//            // southeast
//            if (south(currentData) && east(currentData) && visited.add(currentData.dy(-1).dx(1))) {
//                queue.add(new Node(currentData.dy(-1).dx(1), current));
//            }
//
//            // southwest
//            if (south(currentData) && west(currentData) && visited.add(currentData.dy(-1).dx(-1))) {
//                queue.add(new Node(currentData.dy(-1).dx(-1), current));
//            }
//
//            // west
//            if (west(currentData) && visited.add(currentData.dx(-1))) {
//                queue.add(new Node(currentData.dx(-1), current));
//            }
//            // east
//            if (east(currentData) && visited.add(currentData.dx(1))) {
//                queue.add(new Node(currentData.dx(1), current));
//            }
//            // south
//            if (south(currentData) && visited.add(currentData.dy(-1))) {
//                queue.add(new Node(currentData.dy(-1), current));
//            }
//            //north
//            if (north(currentData) && visited.add(currentData.dy(1))) {
//                queue.add(new Node(currentData.dy(1), current));
//            }
        }
        return null;
    }
}
