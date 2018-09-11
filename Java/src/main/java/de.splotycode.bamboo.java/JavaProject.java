package de.splotycode.bamboo.java;

import de.splotycode.bamboo.core.project.Project;
import lombok.Getter;

import java.io.File;

public class JavaProject implements Project {

    @Getter private File bambooFile;
    @Getter private File workSpace;

    public JavaProject(File bambooFile) {
        this.bambooFile = bambooFile;
    }

    @Override
    public String getName() {
        return null;
    }

}
