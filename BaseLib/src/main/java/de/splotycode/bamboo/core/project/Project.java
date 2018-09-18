package de.splotycode.bamboo.core.project;

import de.splotycode.bamboo.core.data.DataFactory;
import de.splotycode.bamboo.core.data.DataKey;
import lombok.Getter;

import java.io.File;
import java.util.HashMap;

public class Project {

    @Getter private SimpleProjectInformation information;

    private HashMap<Language, LanguageProjectData> languageData = new HashMap<>();
    private HashMap<String, DataFactory> languageFactorys = new HashMap<>();

    public <T> T getData(DataKey<T> key) {
        return getData(key, null);
    }

    public <T> T getData(DataKey<T> key, T def) {
        String[] split = key.getName().split("#");
        if (split.length != 2) throw new IllegalArgumentException("String did not contains Language");

        DataFactory factory = languageFactorys.get(split[0]);
        if (factory == null) {
            return def;
        }
        T value = factory.getData(split[1], key);
        return value == null ? def : value;
    }

    public <T> void putData(DataKey<T> key, T value) {
        String[] split = key.getName().split("#");
        if (split.length != 2) throw new IllegalArgumentException("String did not contains Language");

        DataFactory factory = languageFactorys.get(split[0]);
        if (factory == null) {
            factory = new DataFactory();
            languageFactorys.put(split[0], factory);
        }
        factory.putData(split[1], key, value);
    }

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
