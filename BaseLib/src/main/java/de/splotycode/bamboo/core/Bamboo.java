package de.splotycode.bamboo.core;

import de.splotycode.bamboo.core.boot.BootLoader;
import de.splotycode.bamboo.core.editor.Editor;
import de.splotycode.bamboo.core.gui.ThemeHelper;
import de.splotycode.bamboo.core.i18n.I18N;
import de.splotycode.bamboo.core.keybind.KeyMap;
import de.splotycode.bamboo.core.keybind.KeyMapManager;
import de.splotycode.bamboo.core.project.SimpleProjectInformation;
import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.util.Destroyable;
import de.splotycode.bamboo.core.util.init.InitialisedOnce;
import de.splotycode.bamboo.core.yaml.YamlConfiguration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Bamboo extends InitialisedOnce implements Destroyable {

    @Getter private static Bamboo instance = new Bamboo();

    private List<WorkSpace> openProjects = new ArrayList<>();
    private List<SimpleProjectInformation> allWorkSpaces = new ArrayList<>();

    private File bambooFolder = new File(System.getProperty("user.home"), ".bamboo");
    private File workspacesFile = new File(bambooFolder, "workspaces.yml");

    private KeyMapManager keyMapManager = new KeyMapManager();

    protected void init() {
        bambooFolder.mkdirs();
        try {
            workspacesFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadWorkSpaces();

        keyMapManager.init();

        /* TODO: Can not show Windows in Shut down Thread */
        //Runtime.getRuntime().addShutdownHook(new Thread(this::destroy, "Bamboo shutdown Thread"));

        //lookAndFeel();
        ThemeHelper.setup();
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

    public List<File> getWorkspaceFiles() {
        List<File> projectFiles = new ArrayList<>();
        for (SimpleProjectInformation information : allWorkSpaces)
            projectFiles.add(information.getBambooFile());
        return projectFiles;
    }

    private Collection<Editor> getAllEditors() {
        List<Editor> list = new ArrayList<>();
        for (WorkSpace workSpace : openProjects)
            list.addAll(workSpace.getEditors());
        return list;
    }

    public KeyMap currentKeyMap() {
        return keyMapManager.current();
    }

    @Override
    public void destroy() {
        for (WorkSpace workSpace : new ArrayList<>(openProjects))
            workSpace.destroy();
    }

}
