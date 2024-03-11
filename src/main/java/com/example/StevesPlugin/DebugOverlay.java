package com.example.StevesPlugin;

import net.runelite.client.ui.overlay.Overlay;
import java.awt.*;

public class DebugOverlay extends Overlay {
    @Override
    public Dimension render(Graphics2D graphics) {
        graphics.fillRect(20, 20, 300, 150);

        return null;
    }
}
