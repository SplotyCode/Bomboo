package de.splotycode.bamboo.core.actions.impl.explorer;

import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.gui.DialogHelper;
import de.splotycode.bamboo.core.project.Explorer;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class OpenFileAction extends AbstractExplorerAction {

    private Desktop desktop = Desktop.getDesktop();

    @Override
    protected void explorerAction(Explorer explorer, File file, BambooEvent event) {
        if (file.isDirectory()) {
            return;
        }
        if (desktop.isSupported(Desktop.Action.OPEN)) {
            try {
                desktop.open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            DialogHelper.showMessage(event.getWindow(), "base.dialogs.opnotsupported", DialogHelper.Type.WARNING);
        }
    }

    @Override
    public String internalName() {
        return "explorer.openfile";
    }

}
