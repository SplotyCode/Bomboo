package de.splotycode.bamboo.core.data;

import de.splotycode.bamboo.core.project.SimpleProjectInformation;

import java.io.File;

public class WorkspaceDataKeys {

    public static final DataKey<SimpleProjectInformation> WORKSPACE_RAW = new DataKey<>("raw_workspace");
    public static final DataKey<File> WORKSPACE_FILE = new DataKey<>("workspace_file");

}
