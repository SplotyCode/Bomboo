package de.splotycode.bamboo.core.actions.impl;

import de.splotycode.bamboo.core.actions.Action;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.gui.DialogHelper;
import de.splotycode.bamboo.core.i18n.I18N;
import de.splotycode.bamboo.core.project.Project;
import de.splotycode.bamboo.core.project.SimpleProjectInformation;
import de.splotycode.bamboo.core.util.FileUtils;
import de.splotycode.bamboo.core.yaml.FileConfiguration;
import de.splotycode.bamboo.core.yaml.YamlFile;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class CreateProjectAction extends Action {

    @Override
    public void onAction(BambooEvent event) {
        String result = JOptionPane.showInputDialog(event.getWindow(), I18N.get("actions.projects.create.name"), I18N.get("actions.projects.create.description"), JOptionPane.PLAIN_MESSAGE);
        File file = new File(event.getWorkSpace().getBaseDirecotory(), result + "/bamboo.babo");
        if (result != null && result.length() > 0) {
            if (file.exists()) {
                DialogHelper.showMessage(event.getWindow(), "actions.projects.create.dialog", DialogHelper.Type.ERROR);
                return;
            }
            FileUtils.createFile(file);
            YamlFile yaml = YamlFile.loadFile(file);
            yaml.set("name", result);
            yaml.set("languages", new ArrayList<String>());
            yaml.save();
            event.getWorkSpace().getProjects().add(new Project(event.getWorkSpace(), SimpleProjectInformation.load(file)));
            YamlFile workspace = YamlFile.loadFile(event.getWorkSpace().getInformation().getBambooFile());
            workspace.set("projects", event.getWorkSpace().getBambooProjectFiles());
            workspace.save();
            event.getWorkSpace().reloadFileSystem();
        }
    }

    @Override
    public String internalName() {
        return "projects.create";
    }
}
