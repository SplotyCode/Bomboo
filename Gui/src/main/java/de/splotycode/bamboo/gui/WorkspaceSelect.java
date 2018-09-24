package de.splotycode.bamboo.gui;

import de.splotycode.bamboo.core.Bamboo;
import de.splotycode.bamboo.core.actions.ActionManager;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.actions.CommonAction;
import de.splotycode.bamboo.core.actions.EventCause;
import de.splotycode.bamboo.core.data.WorkspaceDataKeys;
import de.splotycode.bamboo.core.gui.components.label.BambooLabel;
import de.splotycode.bamboo.core.gui.components.button.BambooActionButton;
import de.splotycode.bamboo.core.gui.components.label.BambooHtmlLabel;
import de.splotycode.bamboo.core.gui.components.BambooPanel;
import de.splotycode.bamboo.core.project.SimpleProjectInformation;
import de.splotycode.bamboo.core.gui.components.BambooWindow;
import de.splotycode.bamboo.gui.listener.WorkspaceSelectCloseListener;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class WorkspaceSelect extends BambooWindow implements WorkspaceSelectCloseListener {

    private static BambooPanel workspaces = new BambooPanel();
    @Getter private static boolean shown;

    @Getter private static List<WorkspaceSelectCloseListener> closeListeners = new ArrayList<>();

    @Override
    public void close() {
        shown = false;
        closeQuietly();
    }

    public WorkspaceSelect() {
        super();
        shown = true;
        closeListeners.add(this);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                ActionManager.getInstance().callAction(new BambooEvent(e.getSource(), e.getID(), null, null, EventCause.WORKSPACE_SELECT_SCREEN), CommonAction.EXIT);
            }
        });
        setSize(800, 460);
        setTitle("splash.title");
        setResizable(false);
        setLayout(new GridLayout(1, 2));
        add(workspaces);
        reloadWorkspaces();
        BambooPanel right = new BambooPanel();
        right.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        right.add(new BambooActionButton("splash.button.new", CommonAction.CREATE_WORKSPACE, EventCause.WORKSPACE_SELECT_SCREEN, null), gbc);
        add(right);
        center();
        pack();
        setVisible(true);
    }

    public static void reloadWorkspaces() {
        workspaces.removeAll();
        workspaces.setLayout(new BoxLayout(workspaces, BoxLayout.Y_AXIS));
        for (SimpleProjectInformation information : Bamboo.getInstance().getAllWorkSpaces()) {
            BambooPanel project = new BambooPanel();
            project.setLayout(new BoxLayout(project, BoxLayout.Y_AXIS));
            project.add(new BambooHtmlLabel("<b>" + information.getName() + "</b>"));
            project.add(new BambooLabel(information.getBambooFile().getAbsolutePath()));
            project.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent mouseEvent) {
                    BambooEvent event = new BambooEvent(mouseEvent.getSource(), mouseEvent.getID(), null, null, EventCause.WORKSPACE_SELECT_SCREEN).
                            factoryBuilder().addData(WorkspaceDataKeys.WORKSPACE_RAW, information).build();
                    ActionManager.getInstance().callAction(event, CommonAction.LOAD_WORKSPACE);
                }
            });
            workspaces.add(project);
        }
        workspaces.revalidate();
        workspaces.repaint();
    }

    public static void closeAll() {
        closeListeners.forEach(WorkspaceSelectCloseListener::close);
    }

}