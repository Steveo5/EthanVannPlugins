package com.example.TestPlugin;

import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.PacketUtils.PacketUtilsPlugin;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginManager;

@Slf4j
@PluginDescriptor(
        name = "TestPlugin",
        enabledByDefault = true
)
@PluginDependency(EthanApiPlugin.class)
@PluginDependency(PacketUtilsPlugin.class)

public class TestPlugin extends Plugin {

    public int timeout = 0;
    @Inject
    Client client;
    @Inject
    PluginManager pluginManager;
    @Inject
    EthanApiPlugin api;

    @Override
    protected void startUp() throws Exception
    {
        log.info("Example started!");
    }
}