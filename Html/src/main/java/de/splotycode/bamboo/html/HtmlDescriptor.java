package de.splotycode.bamboo.html;

import de.splotycode.bamboo.core.project.Language;
import de.splotycode.bamboo.core.project.LanguageDescriptor;
import de.splotycode.bamboo.core.project.Project;
import de.splotycode.bamboo.core.yaml.Configuration;

public class HtmlDescriptor implements LanguageDescriptor {

    @Override
    public void load(Project project, Configuration configuration) {

    }

    @Override
    public String[] fileTypes() {
        return new String[] {"html", "htm"};
    }

    @Override
    public Language getLanguage() {
        return Language.HTML;
    }
}
