package de.splotycode.bamboo.gui.api;

import de.splotycode.bamboo.core.i18n.I18N;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.function.Consumer;

public class FileCooserField extends JPanel implements ActionListener {

    private BambooLabel label = new BambooLabel();
    private JFileChooser fileChooser = new JFileChooser();
    private JTextField field = new JTextField();
    private JButton button = new JButton();
    private String name;
    private File file;
    private Consumer<File> consumer;
    private boolean directorys;

    public FileCooserField(String name, File file, Consumer<File> consumer, boolean directors) {
        this.directorys = directors;
        this.consumer = consumer;
        this.name = name;
        this.file = file;
        field.setEditable(false);
        label.setText(name);
        button.setText("...");
        button.addActionListener(this);
        add(label);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(field);
        panel.add(button);
        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        fileChooser.setDialogTitle(I18N.get("base.select") + name);
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
