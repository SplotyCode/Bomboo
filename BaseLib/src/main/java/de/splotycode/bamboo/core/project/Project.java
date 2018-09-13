package de.splotycode.bamboo.core.project;

import lombok.Getter;

import java.io.File;
import java.util.HashMap;

public class Project {

    @Getter private SimpleProjectInformation information;

    private HashMap<Language, LanguageProjectData> languageData = new HashMap<>();

    public <T extends LanguageProjectData> T getProjectData(Class<T> clazz) {
        for (LanguageProjectData data : languageData.values()) {
            if (data.getClass() == clazz) {
                return (T) data;
            }
        }
        return null;
    }

    public LanguageProjectData getProjectData(Language language) {
        return languageData.get(language);
    }

    public <T extends LanguageProjectData> T getProjectData(Language language, Class<T> clazz) {
        return (T) languageData.get(language);
    }

    public Project(SimpleProjectInformation information) {
        this.information = information;
    }

    public File workSpace() {
        return bambooFile().getParentFile();
    }

    public File bambooFile() {
        return information.getBambooFile();
    }

    public String name() {
        return information.getName();
    }

}
