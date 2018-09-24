package de.splotycode.bamboo.core.actions.impl;

import de.splotycode.bamboo.core.actions.AbstractAction;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.editor.Editor;

public class SaveAllAction extends AbstractAction {

    @Override
    public void onAction(BambooEvent event) {
        event.getWorkSpace().getEditors().forEach(Editor::save);
    }

    @Override
    public String internalName() {
        return "file.saveall";
    }

}
