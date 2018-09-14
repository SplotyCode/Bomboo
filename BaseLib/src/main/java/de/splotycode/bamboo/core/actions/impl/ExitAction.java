package de.splotycode.bamboo.core.actions.impl;

import de.splotycode.bamboo.core.Bamboo;
import de.splotycode.bamboo.core.actions.Action;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.actions.CommonAction;
import de.splotycode.bamboo.core.actions.EventCause;
import de.splotycode.bamboo.core.gui.DialogHelper;
import de.splotycode.bamboo.core.i18n.I18N;

import javax.swing.*;
import java.awt.*;

public class ExitAction extends Action {

    @Override
    public void onAction(BambooEvent event) {
        if (event.getCause() == EventCause.WORKSPACE_SELECT_SCREEN) {
            if (Bamboo.getInstance().getOpenProjects().isEmpty()) {
                showCloseDialog(event.getWindow());
            }
        }
        //TODO check if workspace select is open
        else if (Bamboo.getInstance().getOpenProjects().size() < 2) {
            showCloseDialog(event.getWindow());
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
            System.exit(0);
        }
    }

    @Override
    public String internalName() {
        return CommonAction.EXIT.getInternalName();
    }
}
