package de.splotycode.bamboo.css;

import de.splotycode.bamboo.core.project.Language;
import de.splotycode.bamboo.core.project.LanguageDescriptor;
import de.splotycode.bamboo.core.project.Project;
import de.splotycode.bamboo.core.yaml.Configuration;

public class CSSDescriptor implements LanguageDescriptor {

    @Override
    public void load(Project project, Configuration configuration) {

    }

    @Override
    public String[] fileTypes() {
        return new String[] {"css"};
    }

    @Override
    public Language getLanguage() {
        return Language.CSS;
    }
}
