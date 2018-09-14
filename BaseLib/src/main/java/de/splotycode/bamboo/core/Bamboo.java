package de.splotycode.bamboo.core;

import de.splotycode.bamboo.core.boot.BootLoader;
import de.splotycode.bamboo.core.project.Project;
import de.splotycode.bamboo.core.project.SimpleProjectInformation;
import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.tools.init.InitialisedOnce;
import de.splotycode.bamboo.core.yaml.YamlConfiguration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Bamboo extends InitialisedOnce {

    @Getter private static Bamboo instance = new Bamboo();

    @Getter private List<WorkSpace> openProjects = new ArrayList<>();
    @Getter private List<SimpleProjectInformation> allWorkSpaces = new ArrayList<>();

    @Getter private File bambooFolder = new File(System.getProperty("user.home"), ".bamboo");

    @Getter private File workspacesFile = new File(bambooFolder, "workspaces.yml");

    protected void init() {
        bambooFolder.mkdirs();
        try {
            workspacesFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadWorkSpaces();

        lookAndFeel();
        BootLoader.getBootLoader().getShowOpenedProjects().run();
    }

    public void lookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void loadWorkSpaces() {
        List<String> files = YamlConfiguration.loadConfiguration(workspacesFile).getStringList("workspaces");
        for (String filePath : files) {
            File file = new File(filePath);
            allWorkSpaces.add(SimpleProjectInformation.load(file));
        }
    }

    public List<File> getProjectFiles() {
        List<File> projectFiles = new ArrayList<>();
        for (SimpleProjectInformation information : allWorkSpaces)
            projectFiles.add(information.getBambooFile());
        return projectFiles;
    }

}
