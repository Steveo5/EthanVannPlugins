package com.example;

import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.Gui.MainGui;
import com.example.PacketUtils.PacketUtilsPlugin;
import com.example.StevesPlugin.StevesPlugin;
import com.example.Gui.MainGuiFrame;
import javafx.application.Platform;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;
import net.runelite.client.ui.ContainableFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class ExamplePluginTest {
    public static void main(String[] args) throws Exception {
//        ExternalPluginManager.loadBuiltin(EthanApiPlugin.class, PacketUtilsPlugin.class, StevesPlugin.class);
//        RuneLite.main(args);

        Platform.startup(MainGui::new);

//        MainGuiFrame mainGuiFrame = new MainGuiFrame();
//
//        Timer t = new Timer();
//        t.schedule(new TimerTask() {
//            @Override
//            public void run() {
//
//                for (Window window : Window.getWindows()) {
//                    System.out.println(window.getClass());
//                    if (window.getClass() == ContainableFrame.class) {
//                        mainGuiFrame.setUndecorated(true);
////                        window.setSize(window.getWidth(), window.getHeight() - 120);
//                        mainGuiFrame.update(window);
//                        mainGuiFrame.setVisible(true);
//                        mainGuiFrame.pack();
//                        window.revalidate();
//                        window.repaint();
////                        window.
//
//                        window.addComponentListener(mainGuiFrame);
//                    }
//                }
//            }
//        }, 10000);
    }
}