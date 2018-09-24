package de.splotycode.bamboo.gui.workspace;

import de.splotycode.bamboo.core.actions.AbstractAction;
import de.splotycode.bamboo.core.actions.ActionManager;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.actions.EventCause;
import de.splotycode.bamboo.core.data.ExplorerDataKeys;
import de.splotycode.bamboo.core.gui.FontConstants;
import de.splotycode.bamboo.core.gui.components.BambooScrollPane;
import de.splotycode.bamboo.core.gui.components.menu.BambooPopupMenu;
import de.splotycode.bamboo.core.gui.components.tree.BambooFileTreeRenderer;
import de.splotycode.bamboo.core.gui.components.tree.BambooTree;
import de.splotycode.bamboo.core.gui.components.menu.BambooMenu;
import de.splotycode.bamboo.core.gui.components.menu.BambooMenuItem;
import de.splotycode.bamboo.core.gui.components.tree.FileNode;
import de.splotycode.bamboo.core.project.*;
import de.splotycode.bamboo.core.util.ActionUtils;
import de.splotycode.bamboo.core.yaml.YamlFile;
import lombok.Getter;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class ExplorerImpl implements Explorer, MouseListener  {

    private static final String[] ACTIONS = new String[] {"explorer.addfile", "explorer.createfolder", "explorer.deletefile", "file.reload", "explorer.showinfiles", "explorer.openfile", "explorer.editfile", "explorer.printfile", "explorer.showinbrowser", "explorer.mail"};

    private BambooTree jTree = null;

    private BambooScrollPane scrollPane = new BambooScrollPane();

    @Getter private WorkSpace workSpace;

    public ExplorerImpl(WorkSpace workSpace) {
        this.workSpace = workSpace;
    }

    private void createChildren(File fileRoot, DefaultMutableTreeNode node, Project project) {
        File[] files = fileRoot.listFiles();
        if (files == null) return;

        for (File file : files) {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file, file.getName(), project));
            node.add(childNode);
            if (file.isDirectory()) {
                createChildren(file, childNode, project);
            }
        }
    }

    @Override
    public JComponent getComponent() {
        return scrollPane;
    }

    @Override
    public void update() {
        String expandString = jTree == null ? YamlFile.loadFile(workSpace.getInformation().getBambooFile()).getString("explorerexpand") : ExplorerHelper.getExpansionState(jTree);
        if (jTree != null) scrollPane.getViewport().remove(jTree);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        for (Project project : workSpace.getProjects()) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(new FileNode(project.workSpace(), project.name(), project));
            root.add(node);
            createChildren(project.workSpace(), node, project);
        }
        jTree = new BambooTree(root);
        jTree.setFont(FontConstants.getSegoe(16));
        jTree.setCellRenderer(new BambooFileTreeRenderer());
        jTree.setRootVisible(false);
        jTree.addMouseListener(this);

        if (expandString != null) {
            ExplorerHelper.setExpansionState(jTree, expandString);
        }

        /* Workaround for SplitPane */
        jTree.setMinimumSize(new Dimension());
        scrollPane.getViewport().add(jTree);
    }

    @Override
    public File selectedFile() {
        return selectedFileNode().getFile();
    }

    public FileNode selectedFileNode() {
        return (FileNode) ((DefaultMutableTreeNode) jTree.getSelectionPath().getLastPathComponent()).getUserObject();
    }

    @Override
    public void saveExpanded() {
        YamlFile configuration = YamlFile.loadFile(workSpace.getInformation().getBambooFile());
        configuration.set("explorerexpand", ExplorerHelper.getExpansionState(jTree));
        configuration.save();
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        int row = jTree.getClosestRowForLocation(event.getX(), event.getY());
        jTree.setSelectionRow(row);
        FileNode node = selectedFileNode();
        File selectedFile = node.getFile();
        if (SwingUtilities.isRightMouseButton(event)) {
            BambooPopupMenu menu = new BambooPopupMenu();

            if (!selectedFile.isDirectory()) {
                BambooMenu openWith = new BambooMenu("Open With");
                for (LanguageDescriptor descriptor : node.getProject().getDescriptorsByFile(selectedFile)) {
                    BambooMenuItem item = new BambooMenuItem(descriptor.getLanguage().name());
                    item.addActionListener(actionEvent -> {
                        workSpace.openFile(selectedFile, descriptor);
                    });
                    openWith.add(item);
                }
                menu.add(openWith);
            }

            for (String actionName : ACTIONS) {
                AbstractAction action = ActionManager.getInstance().getAction(actionName);
                BambooMenuItem item = new BambooMenuItem(action.getDisplayName());
                item.setToolTipText(action.getDescription());
                item.addActionListener(itemEvent -> {
                    BambooEvent bambooEvent = ActionUtils.convertEvent(itemEvent, workSpace, EventCause.EXPLORER);
                    bambooEvent.factoryBuilder()
                            .addData(ExplorerDataKeys.EXPLORER, this)
                            .addData(ExplorerDataKeys.SELECTED_FILE, selectedFile)
                            .build();
                    action.onAction(bambooEvent);
                });
                menu.add(item);
            }
            menu.show(event.getComponent(), event.getX(), event.getY());
        } else if (event.getClickCount() >= 2) {
            if (!selectedFile.isDirectory())
                workSpace.openFile(selectedFile);
        }
    }

    @Override public void mousePressed(MouseEvent mouseEvent) {}
    @Override public void mouseReleased(MouseEvent mouseEvent) {}

    @Override public void mouseEntered(MouseEvent mouseEvent) {}
    @Override public void mouseExited(MouseEvent mouseEvent) {}

}
