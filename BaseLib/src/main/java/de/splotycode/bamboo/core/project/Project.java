package de.splotycode.bamboo.core.project;

import de.splotycode.bamboo.core.notification.NotificationManager;
import lombok.Getter;

import java.io.File;

public class Project {

    @Getter private SimpleProjectInformation information;
    @Getter private NotificationManager notifications;

    public Project(SimpleProjectInformation information) {
        this.information = information;
        notifications = new NotificationManager();
    }

    public File workSpace() {
        return bambooFile().getParentFile();
    }

    public File bambooFile() {
        return information.getBambooFile();
    }

    public String name() {
        return information.getName();
    }

}
