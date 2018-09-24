package de.splotycode.bamboo.core.actions.impl;

import de.splotycode.bamboo.core.actions.Action;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.editor.Editor;

public class SaveAction extends Action {

    @Override
    public void onAction(BambooEvent event) {
        Editor editor = event.getWorkSpace().getSelectedEditor();
        if (editor != null) {
            editor.save();
        }
    }

    @Override
    public String internalName() {
        return "file.save";
    }

}
