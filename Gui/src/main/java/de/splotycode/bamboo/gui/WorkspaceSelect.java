package de.splotycode.bamboo.gui;

import de.splotycode.bamboo.core.Bamboo;
import de.splotycode.bamboo.core.actions.ActionManager;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.actions.CommonAction;
import de.splotycode.bamboo.core.actions.EventCause;
import de.splotycode.bamboo.core.gui.BambooActionButton;
import de.splotycode.bamboo.core.gui.BambooHtmlLabel;
import de.splotycode.bamboo.core.project.SimpleProjectInformation;
import de.splotycode.bamboo.core.gui.BambooButton;
import de.splotycode.bamboo.gui.api.BambooWindow;

import javax.swing.*;
import java.awt.*;

public class WorkspaceSelect extends BambooWindow {

    private static JPanel workspaces = new JPanel();

    public WorkspaceSelect() {
        setSize(800, 460);
        setTitle("splash.title");
        setResizable(false);
        setLayout(new GridLayout(1, 2));
        add(workspaces);
        reloadWorkspaces();
        JPanel right = new JPanel();
        right.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        right.add(new BambooActionButton("splash.button.new", CommonAction.CREATE_WORKSPACE, EventCause.WORKSPACE_SELECT_SCREEN), gbc);
        add(right);
        center();
        pack();
        setVisible(true);
    }

    public static void reloadWorkspaces() {
        workspaces.removeAll();
        workspaces.setLayout(new BoxLayout(workspaces, BoxLayout.Y_AXIS));
        for (SimpleProjectInformation information : Bamboo.getInstance().getAllWorkSpaces()) {
            JPanel project = new JPanel();
            project.setLayout(new BoxLayout(project, BoxLayout.Y_AXIS));
            project.add(new BambooHtmlLabel("<b>" + information.getName() + "</b>"));
            project.add(new JLabel(information.getBambooFile().getAbsolutePath()));
            workspaces.add(project);
        }
        workspaces.revalidate();
        workspaces.repaint();
    }

}
