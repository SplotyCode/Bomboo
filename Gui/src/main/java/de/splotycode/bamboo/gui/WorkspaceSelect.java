package de.splotycode.bamboo.gui;

import de.splotycode.bamboo.core.Bamboo;
import de.splotycode.bamboo.core.project.SimpleProjectInformation;
import de.splotycode.bamboo.gui.api.BambooButton;
import de.splotycode.bamboo.gui.api.BambooWindow;

import javax.swing.*;
import java.awt.*;

public class WorkspaceSelect extends BambooWindow {

    public WorkspaceSelect() {
        setPreferredSize(new Dimension(800, 460));
        setTitle("splash.title");
        setResizable(false);
        setLayout(new GridLayout(1, 2));
        add(getProjects());
        BambooButton button = new BambooButton("Create New", event -> {

        });
        add(button);
        center();
        pack();
        setVisible(true);
    }

    public JPanel getProjects() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(Bamboo.getInstance().getAllWorkSpaces().size(), 1));
        for (SimpleProjectInformation information : Bamboo.getInstance().getAllWorkSpaces()) {
            JPanel project = new JPanel();
            project.setLayout(new GridLayout(2, 1));
            project.add(new JLabel(information.getName()));
            project.add(new JLabel(information.getBambooFile().getAbsolutePath()));
        }
        return panel;
    }

}
