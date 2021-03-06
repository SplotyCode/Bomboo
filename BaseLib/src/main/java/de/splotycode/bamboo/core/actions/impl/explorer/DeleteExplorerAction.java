package de.splotycode.bamboo.core.actions.impl.explorer;

import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.project.Explorer;

import java.io.File;

public class DeleteExplorerAction extends AbstractExplorerAction {

    @Override
    protected void explorerAction(Explorer explorer, File file, BambooEvent event) {
        file.delete();
        event.getWorkSpace().reloadFileSystem();
    }

    @Override
    public String internalName() {
        return "explorer.deletefile";
    }

}
