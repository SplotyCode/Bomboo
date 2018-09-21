package de.splotycode.bamboo.core.actions.impl.explorer;

import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.gui.DialogHelper;
import de.splotycode.bamboo.core.project.Explorer;

import javax.swing.*;
import java.io.File;

public class CreateFolder extends ExplorerAction {

    @Override
    protected void explorerAction(Explorer explorer, File file, BambooEvent event) {
        if (!file.isDirectory()) file = file.getParentFile();

        String result = JOptionPane.showInputDialog(event.getWindow(), "Specify Folder Name", "Create New Folder", JOptionPane.PLAIN_MESSAGE);
        if (result != null && result.length() > 0) {
            result = result.replace('.', '/');
            if (result.startsWith("/")) {
                DialogHelper.showMessage(event.getWindow(), "Wrong Format", DialogHelper.Type.ERROR);
                return;
            }
            new File(file, result).mkdirs();
            explorer.update();
        }
    }

    @Override
    public String internalName() {
        return "explorer.createfolder";
    }
}
