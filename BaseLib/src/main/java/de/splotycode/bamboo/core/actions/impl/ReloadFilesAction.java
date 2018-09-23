package de.splotycode.bamboo.core.actions.impl;

import de.splotycode.bamboo.core.actions.Action;
import de.splotycode.bamboo.core.actions.BambooEvent;

public class ReloadFilesAction extends Action {

    @Override
    public void onAction(BambooEvent event) {
        event.getWorkSpace().reloadFileSystem();
    }

    @Override
    public String internalName() {
        return "file.reload";
    }
}
