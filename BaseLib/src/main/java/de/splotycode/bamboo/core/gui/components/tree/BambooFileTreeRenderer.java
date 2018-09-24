package de.splotycode.bamboo.core.gui.components.tree;

import de.splotycode.bamboo.core.gui.BambooFileView;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class BambooFileTreeRenderer extends BambooTreeRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object object, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) super.getTreeCellRendererComponent(tree, object, selected, expanded, leaf, row, hasFocus);
        Object o = ((DefaultMutableTreeNode) object).getUserObject();
        if (o instanceof FileNode) {
            FileNode node = (FileNode) o;
            Icon icon = BambooFileView.getInstance().getIcon(node.getFile());
            if (icon instanceof ImageIcon) {
                ((ImageIcon) icon).setImage(((ImageIcon) icon).getImage().getScaledInstance(getFont().getSize(), getFont().getSize(), Image.SCALE_DEFAULT));
            }
            renderer.setIcon(icon);
        }
        renderer.repaint();
        return renderer;
    }
}
