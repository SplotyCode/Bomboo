package de.splotycode.bamboo.core.actions.impl;

import de.splotycode.bamboo.core.Bamboo;
import de.splotycode.bamboo.core.actions.AbstractAction;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.actions.CommonAction;
import de.splotycode.bamboo.core.actions.EventCause;
import de.splotycode.bamboo.core.boot.BootLoader;
import de.splotycode.bamboo.core.i18n.I18N;
import de.splotycode.bamboo.core.project.WorkSpace;

import javax.swing.*;
import java.awt.*;

public class ExitAction extends AbstractAction {

    @Override
    public void onAction(BambooEvent event) {
        if (event.getCause() == EventCause.WORKSPACE_SELECT_SCREEN) {
            if (Bamboo.getInstance().getOpenProjects().isEmpty()) {
                showCloseDialog(event.getWindow());
            } else {
                BootLoader.getBootLoader().getCloseSelectWorkspace().run();
            }
        } else if (event.getCause() != EventCause.WORKSPACE_CLOSE || (Bamboo.getInstance().getOpenProjects().size() < 2 && !BootLoader.getBootLoader().getSelectWorkspaceOpen().get())) {
            showCloseDialog(event.getWindow());
        } else {
            event.getWorkSpace().destroy();
        }
    }

    private void showCloseDialog(Window window) {
        Object[] options = {I18N.get("workspace.close.exit"),
                            I18N.get("workspace.close.cancel")};
        int result = JOptionPane.showOptionDialog(window,
                I18N.get("workspace.close.message"),
                I18N.get("workspace.close.title"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (result == JOptionPane.YES_OPTION) {
            Bamboo.getInstance().destroy();
            System.exit(0);
        }
    }

    @Override
    public String internalName() {
        return CommonAction.EXIT.getInternalName();
    }
}
