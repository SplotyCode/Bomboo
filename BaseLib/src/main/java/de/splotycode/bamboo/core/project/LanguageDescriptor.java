package de.splotycode.bamboo.core.project;

import de.splotycode.bamboo.core.yaml.Configuration;

public interface LanguageDescriptor {

    void load(Project project, Configuration configuration);

    String[] fileTypes();

    Language getLanguage();

}
