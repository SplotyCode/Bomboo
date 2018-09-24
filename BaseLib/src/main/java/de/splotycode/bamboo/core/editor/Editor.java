package de.splotycode.bamboo.core.editor;

import de.splotycode.bamboo.core.gui.components.BambooScrollPane;
import de.splotycode.bamboo.core.gui.components.field.BambooTextArea;
import de.splotycode.bamboo.core.project.LanguageDescriptor;
import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.util.Destroyable;
import lombok.Getter;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Editor extends BambooTextArea implements Destroyable, DocumentListener  {

    @Getter private File file;
    private BambooScrollPane scrollPane = new BambooScrollPane(this);
    @Getter private LanguageDescriptor descriptor;
    private WorkSpace workSpace;

    private boolean lastChanged = false;

    private String diskState;

    private LineNumberComponent lineNumbers = new LineNumberComponent(this);

    public Editor(File file, LanguageDescriptor descriptor, WorkSpace workSpace) {
        super("Loading...");
        setEditable(false);
        this.file = file;
        this.workSpace = workSpace;
        this.descriptor = descriptor;
        scrollPane.setRowHeaderView(lineNumbers);
        lineNumbers.setAlignment(LineNumberComponent.CENTER_ALIGNMENT);
        getDocument().addDocumentListener(this);
        try {
            diskState = descriptor.loadContent(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setText(diskState);
        lineNumbers.adjustWidth();
        descriptor.prepairEditor(this, workSpace);
        setEditable(true);
    }

    public Component getComponent() {
        return scrollPane;
    }

    public void save() {
        if (hasChanged()) {
            setEditable(false);
            try {
                descriptor.saveContent(this, getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
            diskState = getText();
            setEditable(true);
            workSpace.triggerFileEditedChanged();
        }
    }

    public boolean hasChanged() {
        return !getText().equals(diskState);
    }

    @Override
    public void destroy() {
        save();
    }

    private void updateDocument() {
        lineNumbers.adjustWidth();
        if (hasChanged() != lastChanged) {
            workSpace.triggerFileEditedChanged();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent event) {
        updateDocument();
    }

    @Override
    public void removeUpdate(DocumentEvent event) {
        updateDocument();
    }

    @Override
    public void changedUpdate(DocumentEvent event) {
        updateDocument();
    }

}
