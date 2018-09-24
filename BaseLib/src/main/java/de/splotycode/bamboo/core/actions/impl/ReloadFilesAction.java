package de.splotycode.bamboo.core.actions.impl;

import de.splotycode.bamboo.core.actions.AbstractAction;
import de.splotycode.bamboo.core.actions.BambooEvent;

public class ReloadFilesAction extends AbstractAction {

    @Override
    public void onAction(BambooEvent event) {
        event.getWorkSpace().reloadFileSystem();
    }

    @Override
    public String internalName() {
        return "file.reload";
    }
}
