package de.splotycode.bamboo.core.actions.impl;

import de.splotycode.bamboo.core.actions.Action;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.editor.Editor;

public class SaveAllAction extends Action {

    @Override
    public void onAction(BambooEvent event) {
        event.getWorkSpace().getEditors().forEach(Editor::save);
    }

    @Override
    public String internalName() {
        return "file.saveall";
    }

}
