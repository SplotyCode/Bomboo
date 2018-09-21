package de.splotycode.bamboo.core.data;

import de.splotycode.bamboo.core.project.Explorer;

import java.io.File;

public final class ExplorerDataKeys {

    public static final DataKey<Explorer> EXPLORER = new DataKey<>("explorer.explorer");
    public static final DataKey<File> SELECTED_FILE = new DataKey<>("explorer.selected_file");

}
