package de.splotycode.bamboo.core.actions.impl.explorer;

import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.gui.DialogHelper;
import de.splotycode.bamboo.core.project.Explorer;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class OpenInFilesAction extends ExplorerAction {

    private Desktop desktop = Desktop.getDesktop();

    @Override
    protected void explorerAction(Explorer explorer, File file, BambooEvent event) {
        if (!file.isDirectory()) file = file.getParentFile();
        if (desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            DialogHelper.showMessage(event.getWindow(), getLanguagePrefix() + "errordialog", DialogHelper.Type.WARNING);
        }
    }

    @Override
    public String internalName() {
        return "explorer.showinfiles";
    }
}
