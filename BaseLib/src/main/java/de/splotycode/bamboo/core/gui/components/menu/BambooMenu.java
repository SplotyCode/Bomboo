package de.splotycode.bamboo.core.gui.components.menu;

import de.splotycode.bamboo.core.gui.ColorConstants;
import de.splotycode.bamboo.core.gui.FontConstants;

import javax.swing.*;
import java.awt.*;

public class BambooMenu extends JMenu {

    public BambooMenu(String s) {
        super(s);
        setFont(FontConstants.getSegoe(getFont().getSize()));
        getPopupMenu().setBorderPainted(false);
        getPopupMenu().setForeground(Color.white);
        getPopupMenu().setBorder(null);
        getPopupMenu().setBackground(ColorConstants.COLOR_BACKGROUND);
        setFocusPainted(false);
        setBorder(null);
        setBorderPainted(false);
        setForeground(Color.white);
        setBackground(ColorConstants.COLOR_BACKGROUND);
    }
}
