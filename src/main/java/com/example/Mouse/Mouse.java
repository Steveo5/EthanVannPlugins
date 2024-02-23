package com.example.Mouse; /**
 * WindMouse from SMART by Benland100
 * Copyright to Benland100, (Benjamin J. Land)
 *
 * Prepped for DreamBot 3
 **/
import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.Packets.MousePackets;
import com.example.PathFinder.Util;
import net.runelite.api.Point;

import javax.swing.*;
import java.awt.event.MouseEvent;

import static java.lang.Thread.sleep;

public class Mouse {
    private Point mousePos;

    public Point getMousePosition() {
        return mousePos;
    }

    public void setMousePos(net.runelite.api.Point point) {
        this.mousePos = point;
        int x = point.getX();
        int y = point.getY();

        java.awt.Point p = new java.awt.Point(x, y);

        SwingUtilities.convertPointToScreen(p, EthanApiPlugin.getClient().getCanvas());
        EthanApiPlugin.getClient().getCanvas().dispatchEvent(new MouseEvent(EthanApiPlugin.getClient().getCanvas(), MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, x, y, 0, false, 0));
    }

    public void doLeftClick() {
        java.awt.Point p = new java.awt.Point(getMousePosition().getX(), getMousePosition().getY());
        SwingUtilities.convertPointToScreen(p, EthanApiPlugin.getClient().getCanvas());
        MousePackets.queueClickPacket(p.x, p.y);
    }

    public net.runelite.api.Point getMousePos() {
        return mousePos;
    }
}