package de.splotycode.bamboo.gui.workspace;

import de.splotycode.bamboo.core.actions.Action;
import de.splotycode.bamboo.core.actions.ActionManager;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.actions.EventCause;
import de.splotycode.bamboo.core.data.ExplorerDataKeys;
import de.splotycode.bamboo.core.project.Explorer;
import de.splotycode.bamboo.core.project.ExplorerHelper;
import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.util.ActionUtils;
import de.splotycode.bamboo.core.yaml.YamlConfiguration;
import de.splotycode.bamboo.core.yaml.YamlFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class ExplorerImpl implements Explorer, MouseListener  {

    private static final String[] ACTIONS = new String[]{"explorer.addfile", "explorer.createfolder", "explorer.deletefile"};

    private JTree jTree = null;

    private JScrollPane scrollPane = new JScrollPane();

    @Getter private WorkSpace workSpace;

    private File base;

    public ExplorerImpl(WorkSpace workSpace) {
        this.workSpace = workSpace;
    }

    private void createChildren(File fileRoot, DefaultMutableTreeNode node) {
        File[] files = fileRoot.listFiles();
        if (files == null) return;

        for (File file : files) {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
            node.add(childNode);
            if (file.isDirectory()) {
                createChildren(file, childNode);
            }
        }
    }

    @Override
    public JComponent getComponent() {
        return scrollPane;
    }

    @Override
    public void open(File file) {
        base =  file;
        update();
    }

    @Override
    public void update() {
        String expandString = jTree == null ? null : ExplorerHelper.getExpansionState(jTree);
        if (jTree != null) scrollPane.getViewport().remove(jTree);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new FileNode(base));
        jTree = new JTree(root);
        jTree.setShowsRootHandles(true);
        jTree.addMouseListener(this);

        createChildren(base, root);

        if (expandString != null) {
            ExplorerHelper.setExpansionState(jTree, expandString);
        }

        /* Workaround for SplitPane */
        jTree.setMinimumSize(new Dimension());
        scrollPane.getViewport().add(jTree);
    }

    @Override
    public File selectedFile() {
        FileNode node = (FileNode) ((DefaultMutableTreeNode) jTree.getSelectionPath().getLastPathComponent()).getUserObject();
        return node.file;
    }

    @Override
    public File baseDirectory() {
        return base;
    }

    @Override
    public void saveExpanded() {
        YamlFile configuration = YamlFile.loadFile(workSpace.getInformation().getBambooFile());
        configuration.set("explorerexpand", ExplorerHelper.getExpansionState(jTree));
        configuration.save();
    }

    @AllArgsConstructor
    @Data
    public class FileNode {

        private File file;

        @Override
        public String toString() {
            String name = file.getName();
            if (name.equals("")) {
                return file.getAbsolutePath();
            } else {
                return name;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (SwingUtilities.isRightMouseButton(event)) {
            int row = jTree.getClosestRowForLocation(event.getX(), event.getY());
            jTree.setSelectionRow(row);
            JPopupMenu menu = new JPopupMenu();
            for (String actionName : ACTIONS) {
                Action action = ActionManager.getInstance().getAction(actionName);
                JMenuItem item = new JMenuItem(action.getDisplayName());
                item.setToolTipText(action.getDescription());
                item.addActionListener(itemEvent -> {
                    BambooEvent bambooEvent = ActionUtils.convertEvent(itemEvent, workSpace, EventCause.EXPLORER);
                    bambooEvent.factoryBuilder()
                            .addData(ExplorerDataKeys.EXPLORER, this)
                            .addData(ExplorerDataKeys.SELECTED_FILE, selectedFile())
                            .build();
                    action.onAction(bambooEvent);
                });
                menu.add(item);
            }
            menu.show(event.getComponent(), event.getX(), event.getY());
        } else if (event.getClickCount() >= 2){
            int row = jTree.getClosestRowForLocation(event.getX(), event.getY());
            jTree.setSelectionRow(row);
            workSpace.openFile(selectedFile());
        }
    }

    @Override public void mousePressed(MouseEvent mouseEvent) {}
    @Override public void mouseReleased(MouseEvent mouseEvent) {}

    @Override public void mouseEntered(MouseEvent mouseEvent) {}
    @Override public void mouseExited(MouseEvent mouseEvent) {}

}
