package com.example.StevesPlugin;

import com.example.StevesPlugin.Walking.WalkTask;
import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Module;

public class StevesPluginModule extends AbstractModule {
    @Override
    public void configure() {
        bind(TileMarkerOverlay.class);
    }
}
