package de.splotycode.bamboo.core.project;

import de.splotycode.bamboo.core.data.DataFactory;
import de.splotycode.bamboo.core.data.DataKey;
import de.splotycode.bamboo.core.yaml.YamlFile;
import lombok.Getter;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Project {

    @Getter private WorkSpace workSpace;

    public Project(WorkSpace workSpace, SimpleProjectInformation information) {
        this.workSpace = workSpace;
        this.information = information;
    }

    @Getter private SimpleProjectInformation information;

    private HashMap<Language, LanguageProjectData> languageData = new HashMap<>();
    private HashMap<String, DataFactory> languageFactorys = new HashMap<>();

    @Getter private List<LanguageDescriptor> languageDescriptors = new ArrayList<>();

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

    public void installLanguageDescriptor(LanguageDescriptor descriptor) {
        languageDescriptors.add(descriptor);
        descriptor.load(this, YamlFile.loadFile(bambooFile()), workSpace);
    }

    public LanguageDescriptor getDescriptorByFile(File file) {
        String extension = FilenameUtils.getExtension(file.getName());
        return languageDescriptors.stream().filter(descriptor -> Arrays.asList(descriptor.fileTypes()).contains(extension)).findFirst().orElse(null);
    }

    public List<LanguageDescriptor> getDescriptorsByFile(File file) {
        String extension = FilenameUtils.getExtension(file.getName());
        return languageDescriptors.stream().filter(descriptor -> Arrays.asList(descriptor.fileTypes()).contains(extension)).collect(Collectors.toList());
    }

    public void saveDescriptors() {
        List<String> list = new ArrayList<>();
        for (LanguageDescriptor descriptor : languageDescriptors) {
            list.add(descriptor.getLanguage().name());
        }
        YamlFile yaml = YamlFile.loadFile(bambooFile());
        yaml.set("languages", list);
        yaml.save();
    }

    public void uninstallLanguageDescriptor(LanguageDescriptor descriptor) {
        languageDescriptors.remove(descriptor);
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
