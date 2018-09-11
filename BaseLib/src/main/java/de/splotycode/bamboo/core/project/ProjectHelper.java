package de.splotycode.bamboo.core.project;

import de.splotycode.bamboo.core.notification.NotificationManager;
import de.splotycode.bamboo.core.notification.NotificationType;
import de.splotycode.bamboo.core.yaml.YamlConfiguration;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ProjectHelper {

    @Getter private static ProjectHelper instance = new ProjectHelper();

    private Set<ProjectLoader> loaders = new HashSet<>();

    private ProjectHelper() {
        try {
            Class<?> clazz = Class.forName("de.splotycode.bamboo.html.HtmlLoader");
            loaders.add((ProjectLoader) clazz.newInstance());
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }
    }

    public WorkSpace load(File project) {
        WorkSpace workSpace = new WorkSpace();
        workSpace.notifications = new NotificationManager();

        if (!project.exists()) workSpace.notifications.push("filedeleted", NotificationType.ERROR);

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(project);

        String type = configuration.getString("type");

        Optional<ProjectLoader> loader = loaders.stream().filter(cLoader -> cLoader.getLoaderName().equalsIgnoreCase(type)).findFirst();

        if (loader.isPresent()) {
            loader.get().load(project, configuration);
        } else {
            workSpace.notifications.push("noloader", NotificationType.ERROR);
        }
        return workSpace;
    }

    public void create(String type, String name, File file) {
        file.mkdirs();
        File bambooFile = new File(file, "bamboo.babo");
        try {
            bambooFile.createNewFile();

            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(bambooFile);
            configuration.set("type", type);
            configuration.set("name", name);
            configuration.save(bambooFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
