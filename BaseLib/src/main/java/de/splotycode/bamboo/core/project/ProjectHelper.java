package de.splotycode.bamboo.core.project;

import de.splotycode.bamboo.core.Bamboo;
import de.splotycode.bamboo.core.boot.BootLoader;
import de.splotycode.bamboo.core.notification.NotificationManager;
import de.splotycode.bamboo.core.notification.NotificationType;
import de.splotycode.bamboo.core.yaml.YamlConfiguration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectHelper {

    @Getter private static ProjectHelper instance = new ProjectHelper();

    /*public Project load(SimpleProjectInformation information) {
        Project project = new Project(information);

        if (!information.getBambooFile().exists()) project.getNotifications().push("filedeleted", NotificationType.ERROR);

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(information.getBambooFile());

        List<String> types = configuration.getStringList("languages");

        for (String type : types) {
            Optional<LanguageDescriptor> loader = BootLoader.getBootLoader().getGetDescriptors().get().stream().filter(cLoader -> cLoader.getLanguage().name().equalsIgnoreCase(type)).findFirst();
            if (loader.isPresent()) {
                loader.get().load(project, configuration);
            } else {
                project.getNotifications().push("noloader", NotificationType.ERROR);
            }
        }
        return project;
    }

    public void create(String name, File file, List<Language> languages) {
        YamlConfiguration workspaces = YamlConfiguration.loadConfiguration(Bamboo.getInstance().getWorkspacesFile());
        List<File> allProjectFiles = new ArrayList<>();
        allProjectFiles.add(file);
        workspaces.set("workspaces", allProjectFiles);
        try {
            workspaces.save(Bamboo.getInstance().getWorkspacesFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        file.mkdirs();
        File bambooFile = new File(file, "bamboo.babo");
        try {
            bambooFile.createNewFile();

            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(bambooFile);
            configuration.set("name", name);
            configuration.set("languages", languages);
            configuration.save(bambooFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }*/

}
