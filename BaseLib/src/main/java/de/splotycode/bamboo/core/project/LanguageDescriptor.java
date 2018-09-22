package de.splotycode.bamboo.core.project;

import de.splotycode.bamboo.core.editor.Editor;
import de.splotycode.bamboo.core.yaml.Configuration;

import java.io.File;
import java.io.IOException;

public interface LanguageDescriptor {

    void load(Project project, Configuration configuration, WorkSpace workSpace);

    String loadContent(File file) throws IOException;

    void prepairEditor(Editor editor, WorkSpace workSpace);

    String[] fileTypes();

    Language getLanguage();

}
