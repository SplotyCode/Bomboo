package de.splotycode.bamboo.core.actions.impl;

import de.splotycode.bamboo.core.Bamboo;
import de.splotycode.bamboo.core.actions.Action;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.actions.CommonAction;
import de.splotycode.bamboo.core.boot.BootLoader;
import de.splotycode.bamboo.core.data.WorkspaceDataKeys;
import de.splotycode.bamboo.core.gui.DialogHelper;
import de.splotycode.bamboo.core.notification.NotificationType;
import de.splotycode.bamboo.core.project.LanguageDescriptor;
import de.splotycode.bamboo.core.project.Project;
import de.splotycode.bamboo.core.project.SimpleProjectInformation;
import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.yaml.YamlConfiguration;
import de.splotycode.bamboo.core.yaml.YamlFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class LoadWorkspace extends Action {

    @Override
    public void onAction(BambooEvent event) {
        SimpleProjectInformation information = event.getDataFactory().getData(WorkspaceDataKeys.WORKSPACE_RAW);
        if (information == null) {
            File file = event.getDataFactory().getData(WorkspaceDataKeys.WORKSPACE_FILE);
            information = SimpleProjectInformation.load(file);
        }

        SimpleProjectInformation finalInformation = information;
        if (Bamboo.getInstance().getOpenProjects().stream().anyMatch(workSpace -> workSpace.getInformation().getBambooFile().getAbsolutePath().equals(finalInformation.getBambooFile().getAbsolutePath()))) {
            DialogHelper.showMessage(event.getWindow(), "workspace.load.alreadyopen", DialogHelper.Type.WARNING);
            return;
        }

        WorkSpace workSpace = new WorkSpace(information);

        Bamboo.getInstance().getOpenProjects().add(workSpace);

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(information.getBambooFile());
        for (String projectName : configuration.getStringList("projects")) {
            System.out.println("Loading Project: " + projectName);
            Project project = new Project(workSpace, SimpleProjectInformation.load(new File(projectName)));
            List<String> types = YamlFile.loadFile(project.bambooFile()).getStringList("languages");

            for (String type : types) {
                Optional<LanguageDescriptor> loader = BootLoader.getBootLoader().getGetDescriptors().get().stream().filter(cLoader -> cLoader.getLanguage().name().equalsIgnoreCase(type)).findFirst();
                if (loader.isPresent()) {
                    project.installLanguageDescriptor(loader.get());
                } else {
                    workSpace.getNotifications().push("noloader", NotificationType.ERROR);
                }
            }
            workSpace.getProjects().add(project);
        }
        workSpace.reloadFileSystem();
    }

    @Override
    public String internalName() {
        return CommonAction.LOAD_WORKSPACE.getInternalName();
    }
}
