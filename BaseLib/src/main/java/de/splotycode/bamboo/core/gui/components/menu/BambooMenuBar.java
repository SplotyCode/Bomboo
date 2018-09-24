package de.splotycode.bamboo.core.gui.components.menu;

import de.splotycode.bamboo.core.gui.ColorConstants;
import de.splotycode.bamboo.core.gui.FontConstants;

import javax.swing.*;
import java.awt.*;

public class BambooMenuBar extends JMenuBar {

    public BambooMenuBar() {
        setBorderPainted(false);
        setForeground(Color.white);
        setBackground(ColorConstants.COLOR_BACKGROUND);
        setFont(FontConstants.getSegoe(getFont().getSize()));
        setBorder(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(ColorConstants.COLOR_BACKGROUND);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
