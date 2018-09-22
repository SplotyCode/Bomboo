package de.splotycode.bamboo;

import de.splotycode.bamboo.core.editor.Editor;
import de.splotycode.bamboo.core.project.Language;
import de.splotycode.bamboo.core.project.LanguageDescriptor;
import de.splotycode.bamboo.core.project.Project;
import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.yaml.Configuration;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class DefaultTextEditor implements LanguageDescriptor {

    @Override
    public void load(Project project, Configuration configuration, WorkSpace workSpace) {

    }

    @Override
    public String loadContent(File file) throws IOException {
        return IOUtils.toString(file.toURI(), Charset.forName("UTF-8"));
    }

    @Override
    public void prepairEditor(Editor editor, WorkSpace workSpace) {

    }

    @Override
    public String[] fileTypes() {
        return new String[] {"txt", "log", "json"};
    }

    @Override
    public Language getLanguage() {
        return Language.TEXT;
    }
}
