package de.splotycode.bamboo.core.actions.impl.explorer;

import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.gui.DialogHelper;
import de.splotycode.bamboo.core.i18n.I18N;
import de.splotycode.bamboo.core.project.Explorer;
import de.splotycode.bamboo.core.util.FileUtils;

import javax.swing.*;
import java.io.File;

public class AddFileExplorerAction extends ExplorerAction {

    @Override
    protected void explorerAction(Explorer explorer, File file, BambooEvent event) {
        if (!file.isDirectory()) file = file.getParentFile();

        String result = JOptionPane.showInputDialog(event.getWindow(), I18N.get("actions.explorer.addfile.dialog.title"), I18N.get("actions.explorer.addfile.dialog.message"), JOptionPane.PLAIN_MESSAGE);
        if (result != null && result.length() > 0) {
            if (result.contains("\\") || result.contains("/")) {
                DialogHelper.showMessage(event.getWindow(), "actions.explorer.addfile.wrongformat", DialogHelper.Type.ERROR);
                return;
            }
            FileUtils.createFile(new File(file, result));
            explorer.update();
        }
    }

    @Override
    public String internalName() {
        return "explorer.addfile";
    }

}
