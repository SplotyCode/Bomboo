package de.splotycode.bamboo.core.actions.impl;

import de.splotycode.bamboo.core.actions.Action;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.actions.CommonAction;

public class LoadWorkspace extends Action {

    @Override
    public void onAction(BambooEvent event) {

    }

    @Override
    public String internalName() {
        return CommonAction.LOAD_WORKSPACE.getInternalName();
    }
}
