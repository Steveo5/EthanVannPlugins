package com.example;

import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.Gui.MainGui;
import com.example.PacketUtils.PacketUtilsPlugin;
import com.example.StevesPlugin.StevesPlugin;
import com.example.StevesPlugin.Walking.WalkerModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Platform;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ExamplePluginTest {
    public static void main(String[] args) throws Exception {
        Platform.startup(MainGui::new);
        ExternalPluginManager.loadBuiltin(EthanApiPlugin.class, PacketUtilsPlugin.class, StevesPlugin.class);
        RuneLite.main(args);



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
