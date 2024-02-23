package com.example.Gui;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class ConfigFrame extends JFrame {
    public ConfigFrame() {
        this.setMinimumSize(new Dimension(1280, 720));

        JTabbedPane mainTabs = new JTabbedPane();
        mainTabs.add("Player", new JPanel());
        mainTabs.add("Scripts", getScriptsGui());
        mainTabs.add("Schedule", new JPanel());

        this.add(mainTabs);
    }

    public JPanel getScriptsGui() {
        MigLayout layout = new MigLayout();
        JPanel panel = new JPanel(layout);

        panel.add(new H1Label("Add scripts"), "wrap");
        panel.add(new JSeparator(), "wrap");

        MigLayout scriptsGridLayout = new MigLayout("wrap 10, fillx");

        JPanel scriptsGrid = new JPanel(scriptsGridLayout);

        JPanel addScriptPanel = new JPanel();
        addScriptPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 25)));
        addScriptPanel.add(new JLabel("Add new"));
//        addScriptPanel.setMinimumSize(new Dimension(200, 200));

        scriptsGrid.add(addScriptPanel, "grow");
        panel.add(scriptsGrid, "wrap, growx");

        return panel;
    }
}
