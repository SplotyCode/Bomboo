package de.splotycode.bamboo.core.editor;

import de.splotycode.bamboo.core.util.Disposable;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Editor extends JTextArea implements Disposable, DocumentListener  {

    private File file;
    private JScrollPane scrollPane = new JScrollPane(this);

    private LineNumberComponent lineNumbers = new LineNumberComponent(this);

    public Editor(File file) {
        this.file = file;
        scrollPane.setRowHeaderView(lineNumbers);
        lineNumbers.setAlignment(LineNumberComponent.CENTER_ALIGNMENT);
        getDocument().addDocumentListener(this);
        try {
            setText(FileUtils.readFileToString(file, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
