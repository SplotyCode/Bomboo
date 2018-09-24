package de.splotycode.bamboo.gui.workspace;

import de.splotycode.bamboo.core.actions.ActionManager;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.actions.CommonAction;
import de.splotycode.bamboo.core.actions.EventCause;
import de.splotycode.bamboo.core.gui.components.BambooWindow;
import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.project.WorkspaceWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WorkspaceGui extends BambooWindow implements WorkspaceWindow {

    private WorkspaceMenuBar menuBar;

    private WorkSpace workSpace;

    public WorkspaceGui(WorkSpace workSpace) {
        this.workSpace = workSpace;
        menuBar = new WorkspaceMenuBar(workSpace);
        fullSize();
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                ActionManager.getInstance().callAction(new BambooEvent(e.getSource(), e.getID(), null, workSpace, EventCause.WORKSPACE_CLOSE), CommonAction.EXIT);
            }
        });
        setRawTitle("Bamboo 1.0");
        center();
        setJMenuBar(menuBar);
        pack();
        setVisible(true);
    }

    @Override
    public void add(JComponent component) {
        super.add(component);
    }

    @Override
    public void closeWindow() {
        closeQuietly();
    }

    @Override
    public BambooWindow getWindow() {
        return this;
    }
}
