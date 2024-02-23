package com.example.Gui;

import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.EthanApiPlugin.PathFinding.GlobalCollisionMap;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import net.runelite.client.RuneLite;
import net.runelite.client.ui.ContainableFrame;

import java.awt.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class MainGui extends JFrame {
    public MainGui() {
        JFXPanel jfxPanel = new JFXPanel();
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(GlobalCollisionMap.class.getResource("MainGui.fxml"));
                Parent root = loader.load();
                jfxPanel.setScene(new Scene(root, 895, 750));

                SwingUtilities.invokeLater(() -> {
                    this.add(jfxPanel);
                    this.pack();
                    this.setAlwaysOnTop(true);
                    this.setVisible(true);

                    SwingUtilities.invokeLater(() -> {
                        this.setAlwaysOnTop(false);
                    });
                });

                FXMLLoader scriptPoolItemLoader = new FXMLLoader(GlobalCollisionMap.class.getResource("ScriptPoolItem.fxml"));
                try {
                    Parent scriptPoolItemRoot = scriptPoolItemLoader.load();
                    GridPane gridScriptPool = (GridPane)root.getScene().lookup("#gridScriptPool");

                    gridScriptPool.add(scriptPoolItemRoot, 0, 0);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        });
//
//        Timer t = new Timer();
//        t.schedule(new TimerTask() {
//            @Override
//            public void run() {
//
//                for (Window window : Window.getWindows()) {
//                    System.out.println(window.getClass());
//                    if (window.getClass() == ContainableFrame.class) {
//                        ContainableFrame runeliteFrame = (ContainableFrame) window;
////                        jfxPanel.add(EthanApiPlugin.getClient().getCanvas().getParent());
//                        this.cancel();
//                    }
//                }
//            }
//        }, 1000L, 1000L);
    }
}
