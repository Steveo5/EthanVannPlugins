/*
 * Copyright (c) 2018, TheLonelyDev <https://github.com/TheLonelyDev>
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.example.StevesPlugin;

import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.EthanApiPlugin.PathFinding.GlobalCollisionMap;
import com.google.common.base.Strings;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.util.Pair;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.Tile;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.plugins.groundmarkers.GroundMarkerConfig;
import net.runelite.client.plugins.groundmarkers.GroundMarkerPlugin;
import net.runelite.client.ui.overlay.*;

@Singleton
public class TileMarkerOverlay extends Overlay
{
	private static final int MAX_DRAW_DISTANCE = 32;

	Client client;

	private List<WorldPointTileColour> points = new ArrayList<>();

	public TileMarkerOverlay() {
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
		setPriority(OverlayPriority.MED);
	}

	public void drawTiles(List<WorldPoint> points, Color color) {
		this.points.addAll(points.stream().map((wp) -> new WorldPointTileColour(wp, color)).collect(Collectors.toList()));
		this.client = EthanApiPlugin.getClient();
	}

	public void resetHighlighted() {
		this.points.clear();
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
//		System.out.println(points.size());
		if (points.isEmpty() || client == null)
		{
			return null;
		}

//		for (WorldPoint wp : GlobalCollisionMap.getNeighbours(client.getLocalPlayer().getWorldLocation())) {
//			drawTile(graphics, wp, color, "", new BasicStroke());
//		}
//		return null;

		Stroke stroke = new BasicStroke();
		for (final WorldPointTileColour pointTileColour : points)
		{
			if (pointTileColour.getWorldPoint().getPlane() != client.getPlane())
			{
				continue;
			}

			drawTile(graphics, pointTileColour.getWorldPoint(), pointTileColour.getColor(), "", stroke);
		}

		return null;
	}

	private void drawTile(Graphics2D graphics, WorldPoint point, Color color, @Nullable String label, Stroke borderStroke)
	{
		WorldPoint playerLocation = client.getLocalPlayer().getWorldLocation();

		if (point.distanceTo(playerLocation) >= MAX_DRAW_DISTANCE)
		{
			System.out.println("Return for " + point + " " + playerLocation);
			return;
		}

		LocalPoint lp = LocalPoint.fromWorld(client, point);
		if (lp == null)
		{
			System.out.println("LP NULL " + point + " " + playerLocation);
			return;
		}

		Polygon poly = Perspective.getCanvasTilePoly(client, lp);
		if (poly != null)
		{
			OverlayUtil.renderPolygon(graphics, poly, color, new Color(0, 0, 0, 20), borderStroke);
		}

		if (!Strings.isNullOrEmpty(label))
		{
			Point canvasTextLocation = Perspective.getCanvasTextLocation(client, graphics, lp, label, 0);
			if (canvasTextLocation != null)
			{
				OverlayUtil.renderTextLocation(graphics, canvasTextLocation, label, color);
			}
		}
	}
}
