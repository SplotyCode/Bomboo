package de.splotycode.bamboo.core.project;

import de.splotycode.bamboo.core.notification.NotificationManager;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class WorkSpace {

    @Getter private NotificationManager notifications;
    @Getter private SimpleProjectInformation information;

    private Set<Project> projects = new HashSet<>();

    public WorkSpace(SimpleProjectInformation information) {
        this.information = information;
        notifications = new NotificationManager();
    }

}