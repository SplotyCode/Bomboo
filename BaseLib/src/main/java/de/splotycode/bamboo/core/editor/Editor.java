package de.splotycode.bamboo.core.editor;

import de.splotycode.bamboo.core.gui.components.BambooScrollPane;
import de.splotycode.bamboo.core.gui.components.field.BambooTextArea;
import de.splotycode.bamboo.core.project.LanguageDescriptor;
import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.util.Disposable;
import lombok.Getter;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Editor extends BambooTextArea implements Disposable, DocumentListener  {

    @Getter private File file;
    private BambooScrollPane scrollPane = new BambooScrollPane(this);
    @Getter private LanguageDescriptor descriptor;
    private WorkSpace workSpace;

    private LineNumberComponent lineNumbers = new LineNumberComponent(this);

    public Editor(File file, LanguageDescriptor descriptor, WorkSpace workSpace) {
        super("Loading...");
        this.file = file;
        this.workSpace = workSpace;
        this.descriptor = descriptor;
        scrollPane.setRowHeaderView(lineNumbers);
        lineNumbers.setAlignment(LineNumberComponent.CENTER_ALIGNMENT);
        getDocument().addDocumentListener(this);
        try {
            setText(descriptor.loadContent(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        descriptor.prepairEditor(this, workSpace);
    }

    public Component getComponent() {
        return scrollPane;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void insertUpdate(DocumentEvent event) {
        lineNumbers.adjustWidth();
    }

    @Override
    public void removeUpdate(DocumentEvent event) {
        lineNumbers.adjustWidth();
    }

    @Override
    public void changedUpdate(DocumentEvent event) {
        lineNumbers.adjustWidth();
    }
}
