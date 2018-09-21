package de.splotycode.bamboo.gui.workspace;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import de.splotycode.bamboo.core.project.Explorer;
import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.util.ui.TreeUtils;
import lombok.Getter;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;
import java.util.function.Function;

public class ExplorerImpl implements Explorer {

    private JTree jTree = null;
    private DefaultTreeModel treeModel;

    private JScrollPane scrollPane = new JScrollPane();

    private BiMap<DefaultMutableTreeNode, File> nodes = HashBiMap.create();

    @Getter private WorkSpace workSpace;
    private File base;

    public ExplorerImpl(WorkSpace workSpace) {
        this.workSpace = workSpace;
    }

    @Override
    public JComponent getComponent() {
        return scrollPane;
    }

    @Override
    public void open(File file) {
        base = file;

        update();
    }

    @Override
    public void selectFile(File file) {
        jTree.getSelectionModel().setSelectionPath(TreeUtils.getPath(nodes.inverse().get(file)));
    }

    private DefaultMutableTreeNode generateNode(File file) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(file.getName()) {
            @Override
            public boolean isLeaf() {
                if (!file.isDirectory()) return true;
                File[] files = file.listFiles();
                return files == null || files.length == 0;
            }
        };
        node.setAllowsChildren(true);
        nodes.put(node, file);
        return node;
    }

    @Override
    public void update() {
        nodes.clear();
        if (jTree != null) scrollPane.getViewport().remove(jTree);

        treeModel = new DefaultTreeModel(generateNode(base));
        jTree = new JTree(treeModel);
        jTree.addTreeExpansionListener(new TreeExpansionListener() {
            @Override
            public void treeExpanded(TreeExpansionEvent event) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
                File currentRoot = nodes.get(node);
                node.removeAllChildren();
                File[] sup = currentRoot.listFiles();
                if (sup == null) return;
                for (File file : sup) {
                    DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(file.getName());
                    node.add(fileNode);
                }
                treeModel.reload();
                System.out.println(currentRoot.getAbsolutePath());
            }
            @Override public void treeCollapsed(TreeExpansionEvent treeExpansionEvent) {}
        });
        scrollPane.getViewport().add(jTree);

        /* Workaround for SplitPane */
        jTree.setMinimumSize(new Dimension());
    }

    @Override
    public Function<File, JPopupMenu> generatePopup() {
        return null;
    }

    @Override
    public File selectedFile() {
        return nodes.get(jTree.getSelectionPath().getLastPathComponent());
    }

    @Override
    public File baseDirectory() {
        return base;
    }

}
