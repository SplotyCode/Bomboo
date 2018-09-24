package de.splotycode.bamboo.core.actions.impl.explorer;

import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.gui.DialogHelper;
import de.splotycode.bamboo.core.project.Explorer;
import sun.security.krb5.internal.crypto.Des;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PrintFileAction extends ExplorerAction {

    private Desktop desktop = Desktop.getDesktop();

    @Override
    protected void explorerAction(Explorer explorer, File file, BambooEvent event) {
        if (file.isDirectory()) {
            return;
        }
        if (desktop.isSupported(Desktop.Action.PRINT)) {
            try {
                desktop.print(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            DialogHelper.showMessage(event.getWindow(), "base.dialogs.opnotsupported", DialogHelper.Type.WARNING);
        }
    }

    @Override
    public String internalName() {
        return "explorer.printfile";
    }

}
