package de.splotycode.bamboo.core.actions.impl;

import de.splotycode.bamboo.core.Bamboo;
import de.splotycode.bamboo.core.actions.Action;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.actions.CommonAction;
import de.splotycode.bamboo.core.gui.BambooTextBox;
import de.splotycode.bamboo.core.gui.DialogHelper;
import de.splotycode.bamboo.core.gui.FileChooserField;
import de.splotycode.bamboo.core.util.FileUtil;
import de.splotycode.bamboo.core.yaml.YamlConfiguration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateWorkSpaceAction extends Action {

    @Override
    public void onAction(BambooEvent event) {
        FileChooserField destination = new FileChooserField("workspace.create.filechooser", true, FileChooserField.Checks.FOLDER_MUST_EMPTY);
        BambooTextBox name = new BambooTextBox("workspace.create.name");

        DialogHelper.Result result;
        while (true) {
            result = DialogHelper.showInput(event.getWindow(), "workspace.create.title", name, destination);
            if (result == DialogHelper.Result.YES) {
                if (destination.getFile() == null || !destination.runChecks() || !checkName(name, event.getWindow()))
                    continue;
            }
            break;
        }
        if (result == DialogHelper.Result.YES) {

            File bambooFile = new File(destination.getFile(), "bamboo.babo");
            registerWorkspace(bambooFile);

            FileUtil.createFile(bambooFile);
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(bambooFile);
            configuration.set("name", name.getText());
            try {
                configuration.save(bambooFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkName(BambooTextBox name, Window window) {
        if (name.getText().isEmpty()) {
            DialogHelper.showMessage(window, "workspace.create.namenull", DialogHelper.Type.ERROR);
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
