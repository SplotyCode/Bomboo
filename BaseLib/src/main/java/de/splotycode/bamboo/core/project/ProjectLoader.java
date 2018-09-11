package de.splotycode.bamboo.core.project;

import de.splotycode.bamboo.core.yaml.YamlConfiguration;

import java.io.File;

public interface ProjectLoader<T extends Project> {

    T load(File bambooFile, YamlConfiguration configuration);

    String getLoaderName();

}
