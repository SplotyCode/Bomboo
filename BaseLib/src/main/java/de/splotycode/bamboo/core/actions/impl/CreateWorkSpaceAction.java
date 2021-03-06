package de.splotycode.bamboo.core.actions.impl;

import de.splotycode.bamboo.core.Bamboo;
import de.splotycode.bamboo.core.actions.AbstractAction;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.actions.CommonAction;
import de.splotycode.bamboo.core.boot.BootLoader;
import de.splotycode.bamboo.core.gui.components.field.BambooTextBox;
import de.splotycode.bamboo.core.gui.DialogHelper;
import de.splotycode.bamboo.core.gui.components.field.BambooFileChooserField;
import de.splotycode.bamboo.core.project.SimpleProjectInformation;
import de.splotycode.bamboo.core.util.FileUtils;
import de.splotycode.bamboo.core.yaml.YamlConfiguration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateWorkSpaceAction extends AbstractAction {

    @Override
    public void onAction(BambooEvent event) {
        BambooFileChooserField destination = new BambooFileChooserField("workspace.create.filechooser", true, BambooFileChooserField.Checks.FOLDER_MUST_EMPTY);
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

            FileUtils.createFile(bambooFile);
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(bambooFile);
            configuration.set("name", name.getText());
            configuration.set("projects", new ArrayList<String>());
            try {
                configuration.save(bambooFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bamboo.getInstance().getAllWorkSpaces().add(new SimpleProjectInformation(bambooFile, name.getText()));
            BootLoader.getBootLoader().getReloadWorkspaces().run();
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
        List<String> allProjectStrings = workspaces.getStringList("workspaces");
        allProjectStrings.add(file.getAbsolutePath());

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
