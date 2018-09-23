package de.splotycode.bamboo.core.editor;

import de.splotycode.bamboo.core.project.LanguageDescriptor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public abstract class AbstractTextDescriptor implements LanguageDescriptor {

    @Override
    public String loadContent(File file) throws IOException {
        return FileUtils.readFileToString(file, "UTF-8");
    }

    @Override
    public void saveContent(Editor editor, String text) throws IOException {
        FileUtils.write(editor.getFile(), text, "UTF-8");
    }
}
