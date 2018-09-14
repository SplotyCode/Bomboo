package de.splotycode.bamboo.core.gui;

import de.splotycode.bamboo.core.i18n.I18N;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
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

    private Set<Checks> checks = new HashSet<>();

    public enum Checks {

        FOLDER_MUST_EMPTY

    }

    private static final File USER_HOME = new File(System.getProperty("user.home"));

    public FileChooserField(String name, boolean directors, Checks... checks) {
        this(name, USER_HOME, file -> {}, directors, checks);
    }

    public FileChooserField(String name, File file, Consumer<File> consumer, boolean directors, Checks... checks) {
        this.checks.addAll(Arrays.asList(checks));
        this.directorys = directors;
        this.consumer = consumer;
        this.name = name;
        this.file = file;
        field.setEditable(false);
        field.setText(file.getAbsolutePath());
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

        Window window = SwingUtilities.windowForComponent(button);

        if (fileChooser.showOpenDialog(SwingUtilities.windowForComponent(this)) == JFileChooser.APPROVE_OPTION) {
            setFile(fileChooser.getSelectedFile());
            if (!runChecks()) return;
            if (consumer != null) {
                consumer.accept(file);
            }
        } else {
            file = null;
            DialogHelper.showMessage(window, "base.dialogs.filechooser.filenull", DialogHelper.Type.ERROR);
        }
    }

    public boolean runChecks() {
        Window window = SwingUtilities.windowForComponent(button);
        if (file == null) {
            DialogHelper.showMessage(window, "base.dialogs.filechooser.filenull", DialogHelper.Type.ERROR);
            return false;
        }
        if (directorys && !file.isDirectory()) {
            setFile(file);
            DialogHelper.showMessage(window, "base.dialogs.filechooser.nodirectory", DialogHelper.Type.ERROR);
            return false;
        }
        if (!file.exists()) {
            setFile(file);
            DialogHelper.showMessage(window, "base.dialogs.filechooser.exsits", DialogHelper.Type.ERROR);
            return false;
        }
        if (directorys && checks.contains(Checks.FOLDER_MUST_EMPTY) && file.listFiles().length != 0) {
            setFile(file);
            DialogHelper.showMessage(window, "base.dialogs.filechooser.empty", DialogHelper.Type.ERROR);
            return false;
        }
        return true;
    }

    public void setFile(File file) {
        this.file = file;
        field.setText(file == null ? "Not specified" : file.getAbsolutePath());
        revalidate();
        repaint();
    }
}
