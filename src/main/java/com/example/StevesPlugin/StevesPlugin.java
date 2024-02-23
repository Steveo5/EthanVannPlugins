package com.example.StevesPlugin;

import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.EthanApiPlugin.PathFinding.GlobalCollisionMap;
import com.example.Mouse.Mouse;
import com.example.Mouse.MouseOverlay;
import com.example.PacketUtils.PacketUtilsPlugin;
import com.example.PathFinder.CollisionMap;
import com.example.PathFinder.SplitFlagMap;
import com.example.PathFinder.Util;
import com.example.Script.*;
import com.example.Script.Combat.StevesCombatScript;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginManager;
import net.runelite.client.ui.overlay.OverlayManager;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@PluginDependency(EthanApiPlugin.class)
@PluginDependency(PacketUtilsPlugin.class)

@Slf4j
@PluginDescriptor(
        name = "TestPlugin2",
        enabledByDefault = true
)
public class StevesPlugin extends Plugin {
    private static StevesPlugin reference;

    private CollisionMap map;
    private final Map<WorldPoint, List<WorldPoint>> transports = new HashMap<>();
    private final ArrayList<Script> scriptList = new ArrayList<>();
    private ScriptState activeScriptState;
    private Mouse mouse;

    @Inject
    Client client;

    @Inject
    OverlayManager overlayManager;

    TileMarkerOverlay tileMarkerOverlay;
    MouseOverlay mouseOverlay;

    @Inject
    PluginManager pluginManager;

    @Inject
    EthanApiPlugin api;

    public void registerScript(Script script) {
        scriptList.add(script);
    }

    public ArrayList<Script> getScriptList() {
        return scriptList;
    }

    public Script getScript(String name) {
        return scriptList.
                stream()
                .filter((script -> script.getName().equalsIgnoreCase(name)))
                .findFirst()
                .orElse(null);
    }

    public void setActiveScript(Script script) {
        this.activeScriptState = new ScriptState(script, ScriptStatus.STOPPED);
    }

    public boolean setActiveScriptStatus(ScriptStatus status) {
        if (this.activeScriptState != null) {
            this.activeScriptState.setStatus(status);

            switch (status) {
                case RUNNING:
                    activeScriptState.getScript().onStart();

                    break;
                case PAUSED:
                    break;
                case STOPPED:
                    activeScriptState.getScript().onStop();

                    break;
            }

            return true;
        }

        return false;
    }

    @Override
    protected void startUp() throws Exception {
        reference = this;
        this.tileMarkerOverlay = new TileMarkerOverlay();
        this.overlayManager.add(tileMarkerOverlay);
        log.info("Our plugin started up");

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

        registerScript(new StevesCombatScript());

        TimerTask task = new TimerTask() {
            public void run() {
                if (activeScriptState != null) {
                    long currentTime = System.currentTimeMillis();

                    if ((currentTime - activeScriptState.getLastLoopedAt()) > activeScriptState.getLastLoopInterval()) {
                        long newLastLoopInterval = activeScriptState.getScript().onLoop();
                        long newLastLoopedAt = System.currentTimeMillis();

                        activeScriptState.setLastLoopedAt(newLastLoopedAt);
                        activeScriptState.setLastLoopInterval(newLastLoopInterval);
                    }
                }
            }
        };

        Timer timer = new Timer("Timer");

        timer.schedule(task, 0L, 100L);

        mouse = new Mouse();
        mouseOverlay = new MouseOverlay();
        overlayManager.add(mouseOverlay);
    }

    @Override
    public void shutDown() {
    }

    public static StevesPlugin getInstance() {
        return reference;
    }

    public TileMarkerOverlay getTileMarkerOverlay() {
        return this.tileMarkerOverlay;
    }

    public CollisionMap getMap() {
        return map;
    }

    public Map<WorldPoint, List<WorldPoint>> getTransports() {
        return transports;
    }

    public MouseOverlay getMouseOverlay() {
        return mouseOverlay;
    }

    public Mouse getMouse() {
        return mouse;
    }

}
