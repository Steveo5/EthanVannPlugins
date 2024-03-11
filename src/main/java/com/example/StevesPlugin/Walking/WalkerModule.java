package com.example.StevesPlugin.Walking;

import com.google.inject.*;
import com.google.inject.util.Modules;
import net.runelite.client.RuneLite;
import net.runelite.client.RuneLiteModule;


public class WalkerModule extends AbstractModule {
    @Override
    public void configure() {
        this.bind(Walker.class).to(WalkerImpl.class);
    }
}
