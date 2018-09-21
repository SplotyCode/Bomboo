package de.splotycode.bamboo.gui.workspace;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import de.splotycode.bamboo.core.actions.Action;
import de.splotycode.bamboo.core.actions.ActionManager;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.actions.EventCause;
import de.splotycode.bamboo.core.data.ExplorerDataKeys;
import de.splotycode.bamboo.core.project.Explorer;
import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.util.ActionUtils;
import de.splotycode.bamboo.core.util.ui.TreeUtils;
import lombok.Getter;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class ExplorerImpl implements Explorer, MouseListener  {

    private static final String[] ACTIONS = new String[]{"explorer.createfolder"};

    private JTree jTree = null;
    private DefaultTreeModel treeModel;

    private JScrollPane scrollPane = new JScrollPane();

    private BiMap<DefaultMutableTreeNode, String> nodes = HashBiMap.create();

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
        jTree.getSelectionModel().setSelectionPath(TreeUtils.getPath(nodes.inverse().get(file.getAbsolutePath())));
    }

    private DefaultMutableTreeNode generateNode(File file) {
        String path = file.getAbsolutePath();
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(file.getName()) {
            @Override
            public boolean isLeaf() {
                File file = new File(path);
                if (!file.isDirectory()) return true;
                File[] files = file.listFiles();
                boolean result = files == null || files.length == 0;
                System.out.println(path + " " + result);
                return result;
            }
        };
        node.setAllowsChildren(true);
        nodes.put(node, file.getAbsolutePath());
        return node;
    }

    @Override
    public void update() {
        nodes.clear();
        if (jTree != null) scrollPane.getViewport().remove(jTree);

        treeModel = new DefaultTreeModel(generateNode(base));
        jTree = new JTree(treeModel);
        jTree.addMouseListener(this);
        jTree.addTreeExpansionListener(new TreeExpansionListener() {
            @Override
            public void treeExpanded(TreeExpansionEvent event) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
                File currentRoot = new File(nodes.get(node));
                node.removeAllChildren();
                File[] sup = currentRoot.listFiles();
                if (sup == null) return;
                for (File file : sup) {
                    DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(file.getName());
                    node.add(fileNode);
                }
                treeModel.reload();
                jTree.updateUI();
            }
            @Override public void treeCollapsed(TreeExpansionEvent treeExpansionEvent) {}
        });
        scrollPane.getViewport().add(jTree);
        treeModel.reload();
        jTree.updateUI();

        /* Workaround for SplitPane */
        jTree.setMinimumSize(new Dimension());
    }

    @Override
    public File selectedFile() {
        return new File(nodes.get(jTree.getSelectionPath().getLastPathComponent()));
    }

    @Override
    public File baseDirectory() {
        return base;
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
        }
    }

    @Override public void mousePressed(MouseEvent mouseEvent) {}
    @Override public void mouseReleased(MouseEvent mouseEvent) {}

    @Override public void mouseEntered(MouseEvent mouseEvent) {}@Override
    public void mouseExited(MouseEvent mouseEvent) {}
}
