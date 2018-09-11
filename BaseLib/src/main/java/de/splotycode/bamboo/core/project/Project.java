package de.splotycode.bamboo.core.project;

import java.io.File;

public interface Project {

    default File getWorkSpace() {
        return getBambooFile().getParentFile();
    }

    File getBambooFile();

    String getName();



}
