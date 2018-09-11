package de.splotycode.bamboo.core.project;

import java.io.File;

public interface Project {

    default File workSpace() {
        return bambooFile().getParentFile();
    }

    File bambooFile();

    String name();

}
