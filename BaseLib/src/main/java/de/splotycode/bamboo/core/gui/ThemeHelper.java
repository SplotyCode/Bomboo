package de.splotycode.bamboo.core.gui;

import de.splotycode.bamboo.core.gui.ui.BambooButtonUI;
import de.splotycode.bamboo.core.gui.ui.BambooScrollBarUI;
import de.splotycode.bamboo.core.gui.ui.BambooTabPaneUI;

import javax.swing.*;
import java.awt.*;

public class ThemeHelper {

    public static void setup() {
        UIManager.put("ButtonUI", BambooButtonUI.class.getName());
        UIManager.put("TabbedPaneUI", BambooTabPaneUI.class.getName());

        UIManager.put("OptionPane.background", ColorConstants.COLOR_BACKGROUND);
        UIManager.put("OptionPane.messageForeground", Color.white);
        UIManager.put("Panel.background", ColorConstants.COLOR_BACKGROUND);

        UIManager.put("Menu.selectionBackground", ColorConstants.SELECTED);
        UIManager.put("Menu.background", ColorConstants.COLOR_BACKGROUND);
        UIManager.put("Menu.foreground", Color.white);
        UIManager.put("Menu.selectionForeground", Color.white);
        UIManager.put("MenuItem.selectionBackground", ColorConstants.SELECTED);
        UIManager.put("MenuItem.background", ColorConstants.COLOR_BACKGROUND);
        UIManager.put("MenuItem.foreground", Color.white);
        UIManager.put("MenuItem.selectionForeground", Color.white);

        UIManager.put("SplitPane.border", null);
        UIManager.put("SplitPane.foreground", ColorConstants.COLOR_OUTLINE);
        UIManager.put("SplitPane.background", ColorConstants.COLOR_OUTLINE);
        UIManager.put("SplitPane.highlight", ColorConstants.COLOR_OUTLINE);
        UIManager.put("SplitPaneDivider.draggingColor", ColorConstants.SELECTED);
        UIManager.put("SplitPaneDivider.border", null);
        UIManager.put("SplitPane.dividerFocusColor", ColorConstants.COLOR_OUTLINE);
    }

}
