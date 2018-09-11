package de.splotycode.bamboo.html;

import de.splotycode.bamboo.core.notification.NotificationManager;
import de.splotycode.bamboo.core.project.Project;
import lombok.Getter;

import java.io.File;

public class HtmlProject implements Project {

    String name;
    File bambooFile;

    @Override
    public File bambooFile() {
        return bambooFile;
    }

    @Override
    public String name() {
        return name;
    }

}
