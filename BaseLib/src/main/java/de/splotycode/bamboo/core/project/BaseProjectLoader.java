package de.splotycode.bamboo.core.project;

import de.splotycode.bamboo.core.notification.NotificationManager;
import de.splotycode.bamboo.core.notification.NotificationType;
import de.splotycode.bamboo.core.yaml.YamlConfiguration;
import lombok.Getter;

import java.io.File;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class BaseProjectLoader {

    @Getter private static BaseProjectLoader instance = new BaseProjectLoader();

    private Set<ProjectLoader> loaders = new HashSet<>();

    private BaseProjectLoader() {
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

}
