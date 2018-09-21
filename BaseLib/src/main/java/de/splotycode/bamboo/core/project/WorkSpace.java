package de.splotycode.bamboo.core.project;

import de.splotycode.bamboo.core.Bamboo;
import de.splotycode.bamboo.core.boot.BootLoader;
import de.splotycode.bamboo.core.notification.NotificationManager;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class WorkSpace {

    @Getter private NotificationManager notifications;
    @Getter private SimpleProjectInformation information;

    @Getter private Set<Project> projects = new HashSet<>();

    @Getter private WorkspaceWindow window;
    @Getter private Explorer explorer;

    @Getter private JSplitPane mainSplit;

    public WorkSpace(SimpleProjectInformation information) {
        this.information = information;
        notifications = new NotificationManager();
        window = BootLoader.getBootLoader().getGenerateWindow().apply(this);
        explorer = BootLoader.getBootLoader().getGenerateExplorer().apply(this);

        mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, explorer.getComponent(), new TextArea("Exaple"));
        window.add(mainSplit);

        explorer.open(information.getBambooFile().getParentFile());
    }

    public void close() {
        Bamboo.getInstance().getOpenProjects().remove(this);
        explorer.saveExpanded();
        window.closeWindow();
    }

}