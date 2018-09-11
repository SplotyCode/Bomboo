package de.splotycode.bamboo.gui;

import de.splotycode.bamboo.gui.api.BambooWindow;

import java.awt.*;

public class WorkspaceSelect extends BambooWindow {

    public WorkspaceSelect() {
        setSize(800, 460);
        setTitle("splash.title");
        setResizable(false);
        setLayout(new GridLayout(1, 2));
        add();
        center();
        pack();
        setVisible(true);
    }

    public Panel getProjects() {

    }

}
