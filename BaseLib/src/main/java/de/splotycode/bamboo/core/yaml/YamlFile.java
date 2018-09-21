package de.splotycode.bamboo.core.yaml;

import java.io.File;
import java.io.IOException;

public class YamlFile extends YamlConfiguration {

    private File file;

    public YamlFile(File file) {
        this.file = file;
        try {
            load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static YamlFile loadFile(File file) {
        return new YamlFile(file);
    }

    public void save() {
        try {
            save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
