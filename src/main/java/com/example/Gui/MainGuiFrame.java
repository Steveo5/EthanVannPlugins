package com.example.Gui;

import com.example.Script.Script;
import com.example.StevesPlugin.StevesPlugin;
import com.example.Script.ScriptStatus;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainGuiFrame extends JFrame implements ComponentListener {
    public MainGuiFrame() {
        this.setLayout(new MigLayout("", "[grow][60%][grow]"));

        ArrayList<String> scriptNames = new ArrayList<>();

        for (Script script : StevesPlugin.getInstance().getScriptList()) {
            scriptNames.add(script.getName());
        }

        JComboBox<String> comboScripts = new JComboBox<>(scriptNames.toArray(new String[0]));

        this.add(new JLabel("Running script: StevesCombat"));
        this.add(new JLabel("Runtime: 00:00:24"), "align center");

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            Script script = StevesPlugin.getInstance().getScript("StevesCombat");

            if (script != null) {
                StevesPlugin.getInstance().setActiveScript(script);
                StevesPlugin.getInstance().setActiveScriptStatus(ScriptStatus.RUNNING);
            }
        });

        JPanel rightWrap = new JPanel();
        rightWrap.add(startButton);
        JButton configButton = new JButton("Config");

        configButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfigFrame configFrame = new ConfigFrame();
                configFrame.pack();
                configFrame.setVisible(true);
            }
        });

        rightWrap.add(configButton);

        this.add(rightWrap, "align right");
    }

    @Override
    public void componentResized(ComponentEvent e) {

    }

    @Override
    public void componentMoved(ComponentEvent e) {
        System.out.println(e.getComponent().getClass());
        update((Window)e.getComponent());
    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    public void update(Window window) {
        this.toFront();
        this.setMinimumSize(new Dimension(window.getWidth(), 60));
        this.setMaximumSize(new Dimension(window.getWidth(), 60));
        this.setBackground(new Color(255, 255, 255));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
//        int taskBarSize = scnMax.bottom;

//        if (window.getHeight() > ((screenSize.height - 60) - taskBarSize)) {
//            window.setSize(window.getWidth(), (screenSize.height - 60) - taskBarSize);
//        }

        System.out.println(window.getX() + " " + window.getY());
        this.setLocation(window.getLocation().x, window.getLocation().y + window.getHeight());
    }
}
