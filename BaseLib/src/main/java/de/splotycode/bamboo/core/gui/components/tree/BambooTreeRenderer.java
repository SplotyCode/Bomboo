package de.splotycode.bamboo.core.gui.components.tree;

import de.splotycode.bamboo.core.gui.ColorConstants;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class BambooTreeRenderer extends DefaultTreeCellRenderer {

    @Override
    public Color getBackgroundSelectionColor() {
        return ColorConstants.SELECTED;
    }

    @Override
    public Color getBackgroundNonSelectionColor() {
        return null;
    }

    @Override
    public Color getBorderSelectionColor() {
        return null;
    }

    @Override
    public Color getTextNonSelectionColor() {
        return Color.white;
    }

    @Override
    public Color getTextSelectionColor() {
        return Color.white;
    }

    @Override
    public Color getBackground() {
        return null;
    }

}
