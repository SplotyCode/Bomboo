package de.splotycode.bamboo.core.editor;

import de.splotycode.bamboo.core.util.Disposable;

import javax.swing.*;
import java.io.File;

public class Editor extends JScrollPane implements Disposable  {

    private File file;

    public Editor(File file) {
        this.file = file;
    }

    @Override
    public void dispose() {

    }
}
