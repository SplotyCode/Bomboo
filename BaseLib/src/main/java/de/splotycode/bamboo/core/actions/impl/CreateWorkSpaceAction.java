package de.splotycode.bamboo.core.actions.impl;

import de.splotycode.bamboo.core.Bamboo;
import de.splotycode.bamboo.core.actions.Action;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.actions.CommonAction;
import de.splotycode.bamboo.core.gui.BambooTextBox;
import de.splotycode.bamboo.core.gui.DialogHelper;
import de.splotycode.bamboo.core.gui.FileChooserField;
import de.splotycode.bamboo.core.yaml.YamlConfiguration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateWorkSpaceAction extends Action {

    @Override
    public void onAction(BambooEvent event) {
        FileChooserField destination = new FileChooserField("workspace.create.filechooser", true);
        BambooTextBox name = new BambooTextBox("workspace.create.name");
        DialogHelper.Result result = DialogHelper.showInput(event.getWindow(), "workspace.create.title", name, destination);
        if (result == DialogHelper.Result.YES) {
            if (!checkFile(destination, event.getWindow()) || !checkName(name, event.getWindow())) return;
            registerWorkspace(destination.getFile());

            File bambooFile = new File(destination.getFile(), "bamboo.babo");
        }
    }

    private boolean checkName(BambooTextBox name, Window window) {
        if (name.getText().isEmpty()) {
            DialogHelper.showMessage(window, "workspace.create.namenull", DialogHelper.Type.ERROR);
            return false;
        }
        return true;
    }

    private boolean checkFile(FileChooserField destination, Window window) {
        if (destination.getFile() == null) {
            DialogHelper.showMessage(window, "workspace.create.filenull", DialogHelper.Type.ERROR);
            return false;
        }
        if (!destination.getFile().isDirectory()) {
            DialogHelper.showMessage(window, "workspace.create.nodirectory", DialogHelper.Type.ERROR);
            return false;
        }
        if (!destination.getFile().exists()) {
            DialogHelper.showMessage(window, "workspace.create.exsits", DialogHelper.Type.ERROR);
            return false;
        }
        if (destination.getFile().listFiles().length == 0) {
            DialogHelper.showMessage(window, "workspace.create.notempty", DialogHelper.Type.ERROR);
            return false;
        }
        return true;
    }

    private void registerWorkspace(File file) {
        YamlConfiguration workspaces = YamlConfiguration.loadConfiguration(Bamboo.getInstance().getWorkspacesFile());
        List<File> allProjectFiles = new ArrayList<>();
        allProjectFiles.add(file);
        List<String> allProjectStrings = new ArrayList<>();
        for (File workspace : allProjectFiles)
            allProjectStrings.add(workspace.getAbsolutePath());

        workspaces.set("workspaces", allProjectStrings);
        try {
            workspaces.save(Bamboo.getInstance().getWorkspacesFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String internalName() {
        return CommonAction.CREATE_WORKSPACE.getInternalName();
    }
}
