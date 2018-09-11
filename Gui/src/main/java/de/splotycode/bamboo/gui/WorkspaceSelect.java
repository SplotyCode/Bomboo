package de.splotycode.bamboo.gui;

import de.splotycode.bamboo.gui.api.BambooWindow;

import javax.swing.*;
import java.awt.*;

public class WorkspaceSelect extends BambooWindow {

    public WorkspaceSelect() {
        setSize(800, 460);
        setTitle("splash.title");
        setResizable(false);
        setLayout(new GridLayout(1, 2));
        add(getProjects());
        JButton button = new JButton("Create New");
        add(button);
        center();
        pack();
        setVisible(true);
    }

    public JPanel getProjects() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Test"));
        panel.add(new JLabel("Test2"));
        panel.add(new JLabel("Test3"));
        return panel;
    }

}
