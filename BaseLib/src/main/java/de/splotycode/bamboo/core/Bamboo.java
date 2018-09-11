package de.splotycode.bamboo.core;

import de.splotycode.bamboo.core.boot.BootLoader;
import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.tools.init.InitialisedOnce;
import de.splotycode.bamboo.core.yaml.YamlConfiguration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Bamboo extends InitialisedOnce {

    @Getter private static Bamboo instance = new Bamboo();

    private List<WorkSpace> openWorkSpaces = new ArrayList<>();
    private List<String> allWorkSpaces = new ArrayList<>();

    private File bambooFolder = new File(System.getProperty("user.home"), ".bamboo");

    private File workspacesFile = new File(bambooFolder, "workspaces.yml");

    protected void init() {
        bambooFolder.mkdirs();
        try {
            workspacesFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadWorkSpaces();

        BootLoader.getBootLoader().getShowOpenedProjects().run();
    }

    public void loadWorkSpaces() {
        allWorkSpaces = YamlConfiguration.loadConfiguration(workspacesFile).getStringList("workspaces");
    }
}
