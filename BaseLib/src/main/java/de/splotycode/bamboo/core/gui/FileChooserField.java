package de.splotycode.bamboo.core.gui;

import de.splotycode.bamboo.core.i18n.I18N;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.function.Consumer;

public class FileChooserField extends JPanel implements ActionListener {

    private BambooLabel label = new BambooLabel();
    private JFileChooser fileChooser = new JFileChooser();
    private JTextField field = new JTextField();
    private JButton button = new JButton();
    private String name;
    @Getter private File file;
    private Consumer<File> consumer;
    private boolean directorys;

    public FileChooserField(String name, boolean directors) {
        this(name, new File(System.getProperty("user.home")), file -> {}, directors);
    }

    public FileChooserField(String name, File file, Consumer<File> consumer, boolean directors) {
        this.directorys = directors;
        this.consumer = consumer;
        this.name = name;
        this.file = file;
        field.setEditable(false);
        label.setText(name);
        label.setRawText(label.getText() + ":");
        button.setText("...");
        button.addActionListener(this);
        add(label);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(field);
        panel.add(button);
        add(panel);
        setLayout(new GridLayout(2, 1));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        fileChooser.setDialogTitle(I18N.get("base.select") + I18N.get(name));
        fileChooser.setDragEnabled(true);
        fileChooser.setFileHidingEnabled(false);
        if (file != null)
            fileChooser.setSelectedFile(file);
        if (directorys) {
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
        }

        if (fileChooser.showOpenDialog(SwingUtilities.windowForComponent(this)) == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            if (consumer != null) {
                consumer.accept(file);
            }
        }
    }
}
