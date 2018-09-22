package de.splotycode.bamboo.core.project;

import de.splotycode.bamboo.core.editor.Editor;
import de.splotycode.bamboo.core.yaml.Configuration;

import java.io.File;

public interface LanguageDescriptor {

    void load(Project project, Configuration configuration, WorkSpace workSpace);

    String loadContent(File file);

    void prepairEditor(Editor editor, WorkSpace workSpace);

    String[] fileTypes();

    Language getLanguage();

}
