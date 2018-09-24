package de.splotycode.bamboo.core.gui.components.menu;

import de.splotycode.bamboo.core.gui.ColorConstants;
import de.splotycode.bamboo.core.gui.FontConstants;

import javax.swing.*;
import java.awt.*;

public class BambooMenuItem extends JMenuItem {

    public BambooMenuItem(String s) {
        super(s);
        setFont(FontConstants.getSegoe(getFont().getSize()));
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.white);
        setBackground(ColorConstants.COLOR_BACKGROUND);
    }
}
