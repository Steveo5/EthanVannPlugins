package com.example.StevesPlugin;

import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.Mouse.Mouse;
import com.example.Mouse.MouseOverlay;
import com.example.PacketUtils.PacketUtilsPlugin;
import com.example.Script.*;
import com.example.Script.Combat.StevesCombatScript;
import com.example.StevesPlugin.Walking.WalkerModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.RuneLite;
import net.runelite.client.RuneLiteModule;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginManager;
import net.runelite.client.ui.overlay.OverlayManager;

import java.util.*;

@PluginDependency(EthanApiPlugin.class)
@PluginDependency(PacketUtilsPlugin.class)

@Slf4j
@PluginDescriptor(
        name = "TestPlugin2",
        enabledByDefault = true
)
public class StevesPlugin extends Plugin {
    private static StevesPlugin reference;

    private final ArrayList<Script> scriptList = new ArrayList<>();
    private ScriptState activeScriptState;
    private Mouse mouse;

    @Inject
    Client client;

    @Inject
    private Injector injector;

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
        this.activeScriptState = new ScriptState(script, ScriptStatus.RUNNING);
        activeScriptState.getScript().onStart();
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
        log.info("Our plugin started up");

        Injector stevesPluginInjector = getInjector().createChildInjector(new WalkerModule(), new StevesPluginModule());

        if (getInjector() != null) {
            registerScript(stevesPluginInjector.getInstance(StevesCombatScript.class));
            this.tileMarkerOverlay = stevesPluginInjector.getInstance(TileMarkerOverlay.class);
            this.overlayManager.add(tileMarkerOverlay);
            this.overlayManager.add(new DebugOverlay());
        }


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

    public MouseOverlay getMouseOverlay() {
        return mouseOverlay;
    }

    public Mouse getMouse() {
        return mouse;
    }

}
