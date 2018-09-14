package de.splotycode.bamboo.core.project;

import de.splotycode.bamboo.core.yaml.YamlConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

@Data
@AllArgsConstructor
public class SimpleProjectInformation {

    private File bambooFile;
    private String name;

    public static SimpleProjectInformation load(File bambooFile) {
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(bambooFile);
        return new SimpleProjectInformation(bambooFile, configuration.getString("name"));
    }

}
