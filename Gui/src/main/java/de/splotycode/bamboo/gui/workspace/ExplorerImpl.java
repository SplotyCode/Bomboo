package de.splotycode.bamboo.gui.workspace;

import de.splotycode.bamboo.core.project.Explorer;
import de.splotycode.bamboo.core.project.WorkSpace;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.function.Function;

public class ExplorerImpl implements Explorer {

    private JTree jTree = null;
    private JScrollPane scrollPane = new JScrollPane();

    @Getter private WorkSpace workSpace;
    private File base;

    public ExplorerImpl(WorkSpace workSpace) {
        this.workSpace = workSpace;
    }

    @Override
    public JComponent getComponent() {
        return null;
    }

    @Override
    public void open(File file) {
        base = file;

        if (jTree != null) scrollPane.getViewport().remove(jTree);
        jTree = new JTree();
        scrollPane.getViewport().add(jTree);

        /* Workaround for SplitPane */
        jTree.setMinimumSize(new Dimension());
        update();
    }

    @Override
    public void selectFile(File file) {

    }

    @Override
    public void update() {

    }

    @Override
    public Function<File, JPopupMenu> generatePopup() {
        return null;
    }

    @Override
    public File selectedFile() {
        return null;
    }

    @Override
    public File baseDirectory() {
        return base;
    }

}
