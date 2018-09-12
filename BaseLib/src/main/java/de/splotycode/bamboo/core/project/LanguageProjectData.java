package de.splotycode.bamboo.core.project;

import de.splotycode.bamboo.core.yaml.YamlConfiguration;

public interface LanguageProjectData {

    Language getLanguage();
    void read(YamlConfiguration yaml);

}
