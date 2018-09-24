package de.splotycode.bamboo.core.gui.ui;

import de.splotycode.bamboo.core.gui.ColorConstants;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.lang.reflect.Field;

public class BambooTabPaneUI extends BasicTabbedPaneUI {

    public static ComponentUI createUI(JComponent var0) {
        JTabbedPane pane = (JTabbedPane) var0;
        pane.setFocusable(false);
        pane.setRequestFocusEnabled(false);
        pane.setForeground(Color.white);
        pane.setBackground(ColorConstants.COLOR_BACKGROUND);
        return new BambooTabPaneUI();
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();
        try {
            Field field = getClass().getSuperclass().getDeclaredField("selectedColor");
            field.setAccessible(true);
            field.set(this, ColorConstants.COLOR_BACKGROUND);
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintTabBackground(Graphics graphics, int i, int i1, int i2, int i3, int i4, int i5, boolean selected) {
        super.paintTabBackground(graphics, i, i1, i2, i3, i4, i5, selected);
        if (selected) {
            graphics.setColor(ColorConstants.COLOR_INTERACTIVE);
            graphics.fillRect(i2 + 1, i3 + i5 - 3, i4 - 3, 2);
        }
    }

    @Override
    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {

    }

    @Override
    protected void paintTabBorder(Graphics graphics, int i, int i1, int i2, int i3, int i4, int i5, boolean b) {

    }
}
