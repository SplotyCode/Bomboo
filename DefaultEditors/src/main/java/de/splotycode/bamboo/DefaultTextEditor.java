package de.splotycode.bamboo;

import de.splotycode.bamboo.core.editor.AbstractTextDescriptor;
import de.splotycode.bamboo.core.editor.Editor;
import de.splotycode.bamboo.core.editor.error.Inspection;
import de.splotycode.bamboo.core.project.Language;
import de.splotycode.bamboo.core.project.Project;
import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.yaml.Configuration;

public class DefaultTextEditor extends AbstractTextDescriptor {

    @Override
    public void load(Project project, Configuration configuration, WorkSpace workSpace) {

    }

    @Override
    public void prepairEditor(Editor editor, WorkSpace workSpace) {

    }

    @Override
    public void onTextChange(Editor editor) {

    }

    @Override
    public String[] fileTypes() {
        return new String[] {"txt", "log", "json", "html", "htm"};
    }

    @Override
    public Language getLanguage() {
        return Language.TEXT;
    }

    @Override
    public Inspection[] getInspections() {
        return new Inspection[0];
    }
}
