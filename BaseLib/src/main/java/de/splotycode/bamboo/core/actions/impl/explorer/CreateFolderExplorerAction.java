package de.splotycode.bamboo.core.actions.impl.explorer;

import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.gui.DialogHelper;
import de.splotycode.bamboo.core.i18n.I18N;
import de.splotycode.bamboo.core.project.Explorer;

import javax.swing.*;
import java.io.File;

public class CreateFolderExplorerAction extends AbstractExplorerAction {

    @Override
    protected void explorerAction(Explorer explorer, File file, BambooEvent event) {
        if (!file.isDirectory()) file = file.getParentFile();

        String result = JOptionPane.showInputDialog(event.getWindow(), I18N.get("actions.explorer.createfolder.dialog.title"), I18N.get("actions.explorer.createfolder.dialog.message"), JOptionPane.PLAIN_MESSAGE);
        if (result != null && result.length() > 0) {
            result = result.replace('.', '/');
            if (result.startsWith("/")) {
                DialogHelper.showMessage(event.getWindow(), "actions.explorer.createfolder.wrongformat", DialogHelper.Type.ERROR);
                return;
            }
            new File(file, result).mkdirs();
            event.getWorkSpace().reloadFileSystem();
        }
    }

    @Override
    public String internalName() {
        return "explorer.createfolder";
    }
}
