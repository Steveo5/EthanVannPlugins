package com.example.PathFinder;

import com.example.EthanApiPlugin.PathFinding.GlobalCollisionMap;
import net.runelite.api.coords.WorldPoint;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebWalkMap {
    private final List<WebWalkLine> webWalkLines = new ArrayList<>();
    private final List<WebWalkNode> webWalkNodes = new ArrayList<>();

    public WebWalkMap() {
        try(FileReader reader = new FileReader(GlobalCollisionMap.class.getResource("web.json").getFile())) {
            Object obj = new JSONParser().parse(reader);
            JSONObject jo = (JSONObject) obj;

            JSONArray linesArray = (JSONArray) jo.get("lines");

            for (Object lineObj : linesArray) {
                JSONObject line = (JSONObject) lineObj;

                JSONObject locFromObj = (JSONObject) line.get("from");
                JSONObject locToObj = (JSONObject) line.get("to");

                WebWalkLine walkLine = new WebWalkLine(
                        new WorldPoint(((Long)locFromObj.get("x")).intValue(), ((Long)locFromObj.get("y")).intValue(), ((Long)locFromObj.get("z")).intValue()),
                        new WorldPoint(((Long)locToObj.get("x")).intValue(), ((Long)locToObj.get("y")).intValue(), ((Long)locToObj.get("z")).intValue())
                );

                this.webWalkLines.add(walkLine);
            }

            JSONArray nodesArray = (JSONArray) jo.get("nodes");

            for (Object nodeObj : nodesArray) {
                JSONObject node = (JSONObject) nodeObj;
                JSONObject locObj = (JSONObject) node.get("position");

                WebWalkNode walkNode = new WebWalkNode(
                        new WorldPoint(((Long)locObj.get("x")).intValue(), ((Long)locObj.get("y")).intValue(), ((Long)locObj.get("z")).intValue())
                );

                this.webWalkNodes.add(walkNode);
            }

            System.out.println("Loaded " + this.getWebWalkNodes().size() + " web walk nodes");
            System.out.println("Loaded " + this.getWebWalkLines().size() + " web walk lines");
        } catch(FileNotFoundException exception) {
            throw new RuntimeException(exception);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public List<WebWalkLine> getWebWalkLines() {
        return webWalkLines;
    }

    public List<WebWalkNode> getWebWalkNodes() {
        return webWalkNodes;
    }
}
