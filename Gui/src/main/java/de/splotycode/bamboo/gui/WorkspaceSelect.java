package de.splotycode.bamboo.gui;

import de.splotycode.bamboo.core.Bamboo;
import de.splotycode.bamboo.core.actions.ActionManager;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.actions.CommonAction;
import de.splotycode.bamboo.core.actions.EventCause;
import de.splotycode.bamboo.core.gui.BambooActionButton;
import de.splotycode.bamboo.core.project.SimpleProjectInformation;
import de.splotycode.bamboo.core.gui.BambooButton;
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
        JPanel left = new JPanel();
        left.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        left.add(new BambooActionButton("splash.button.new", CommonAction.CREATE_WORKSPACE, EventCause.WORKSPACE_SELECT_SCREEN), gbc);
        add(left);
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
