package de.splotycode.bamboo.core.gui.components;

import de.splotycode.bamboo.core.gui.ColorConstants;
import de.splotycode.bamboo.core.gui.ui.BambooScrollBarUI;

import javax.swing.*;
import java.awt.*;

public class BambooScrollPane extends JScrollPane {

    public BambooScrollPane(Component component) {
        super(component);
        init();
    }

    public BambooScrollPane() {
        init();
    }

    private void init() {
        initBar(getHorizontalScrollBar());
        initBar(getVerticalScrollBar());
        setBorder(null);
        setBackground(ColorConstants.COLOR_BACKGROUND);
    }

    private void initBar(JScrollBar bar) {
        bar.setUI(BambooScrollBarUI.createUI(bar));
        bar.setBackground(ColorConstants.COLOR_BACKGROUND);
        bar.setForeground(ColorConstants.COLOR_OUTLINE);
        bar.setFocusable(false);
        bar.setFocusable(false);
        bar.setRequestFocusEnabled(false);
        bar.setBorder(null);
    }
}
