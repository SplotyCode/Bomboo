package de.splotycode.bamboo.core.gui.components;

import de.splotycode.bamboo.core.gui.ColorConstants;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;

public class BambooSplitPane extends JSplitPane {

    public BambooSplitPane(int i, Component component, Component component1) {
        super(i, component, component1);
        setUI(new BasicSplitPaneUI() {
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    public void setBorder(Border b) {
                    }

                    @Override
                    public void paint(Graphics g) {
                        g.setColor(ColorConstants.COLOR_OUTLINE);
                        g.fillRect(0, 0, getSize().width, getSize().height);
                        super.paint(g);
                    }
                };
            }
        });

        setForeground(ColorConstants.COLOR_OUTLINE);
        setBackground(ColorConstants.COLOR_BACKGROUND);

        BasicSplitPaneDivider divider = (BasicSplitPaneDivider) getComponent(2);
        divider.setForeground(ColorConstants.COLOR_OUTLINE);
        divider.setBackground(ColorConstants.COLOR_BACKGROUND);
        divider.setBorder(null);
        setBorder(null);
    }
}
