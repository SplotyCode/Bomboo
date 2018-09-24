package de.splotycode.bamboo.core.actions.impl;

import de.splotycode.bamboo.core.actions.AbstractAction;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.boot.BootLoader;

public class CloseWorkspaceAction extends AbstractAction {

    @Override
    public void onAction(BambooEvent event) {
        event.getWorkSpace().close();

        if (!BootLoader.getBootLoader().getSelectWorkspaceOpen().get()) {
            BootLoader.getBootLoader().getShowOpenedProjects().run();
        }
    }

    @Override
    public String internalName() {
        return "file.workspace.close";
    }
}
