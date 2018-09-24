package de.splotycode.bamboo.core.gui.components.tree;

import de.splotycode.bamboo.core.gui.ColorConstants;

import javax.swing.*;
import javax.swing.tree.TreeNode;

public class BambooTree extends JTree {

    public BambooTree() {
        init();
    }

    public BambooTree(TreeNode treeNode) {
        super(treeNode);
        init();
    }

    private void init() {
        setBackground(ColorConstants.COLOR_BACKGROUND);
    }
}
