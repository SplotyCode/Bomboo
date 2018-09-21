package de.splotycode.bamboo.core.actions.impl;

import de.splotycode.bamboo.core.actions.Action;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.boot.BootLoader;

public class CloseWorkspaceAction extends Action {

    @Override
    public void onAction(BambooEvent event) {
        event.getWorkSpace().close();

        if (!BootLoader.getBootLoader().getSelectWorkspaceOpen().get()) {
            BootLoader.getBootLoader().getShowOpenedProjects().run();
        }
    }

    @Override
    public String internalName() {
        return "file.close";
    }
}
