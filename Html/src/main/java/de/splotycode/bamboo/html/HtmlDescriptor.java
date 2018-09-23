package de.splotycode.bamboo.html;

import de.splotycode.bamboo.core.editor.AbstractTextDescriptor;
import de.splotycode.bamboo.core.editor.Editor;
import de.splotycode.bamboo.core.project.Language;
import de.splotycode.bamboo.core.project.LanguageDescriptor;
import de.splotycode.bamboo.core.project.Project;
import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.yaml.Configuration;

import java.io.File;
import java.io.IOException;

public class HtmlDescriptor extends AbstractTextDescriptor {

    @Override
    public void load(Project project, Configuration configuration, WorkSpace workSpace) {

    }

    @Override
    public void prepairEditor(Editor editor, WorkSpace workSpace) {

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
