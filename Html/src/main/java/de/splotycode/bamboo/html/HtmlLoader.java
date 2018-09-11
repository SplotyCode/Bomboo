package de.splotycode.bamboo.html;

import de.splotycode.bamboo.core.project.Project;
import de.splotycode.bamboo.core.project.ProjectLoader;
import de.splotycode.bamboo.core.yaml.YamlConfiguration;

import java.io.File;

public class HtmlLoader implements ProjectLoader<HtmlProject> {

    @Override
    public HtmlProject load(File bambooFile, YamlConfiguration configuration) {
        HtmlProject project = new HtmlProject();
        project.name = configuration.getString("name");
        project.bambooFile = bambooFile;
        return project;
    }

    @Override
    public String getLoaderName() {
        return "html";
    }
}
