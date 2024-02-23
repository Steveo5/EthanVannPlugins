package com.example.Mouse;

import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.StevesPlugin.StevesPlugin;
import net.runelite.client.ui.overlay.Overlay;

import java.awt.*;

public class MouseOverlay extends Overlay {
    @Override
    public Dimension render(Graphics2D graphics) {
        Mouse m = StevesPlugin.getInstance().getMouse();
        graphics.drawLine(0, m.getMousePos().getY(), EthanApiPlugin.getClient().getCanvasWidth(), m.getMousePos().getY());
        graphics.drawLine(m.getMousePos().getX(), 0, m.getMousePos().getX(), EthanApiPlugin.getClient().getCanvasHeight());

        return null;
    }
}
