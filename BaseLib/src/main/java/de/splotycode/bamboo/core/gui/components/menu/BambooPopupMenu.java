package de.splotycode.bamboo.core.gui.components.menu;

import de.splotycode.bamboo.core.gui.ColorConstants;
import de.splotycode.bamboo.core.gui.FontConstants;

import javax.swing.*;
import java.awt.*;

public class BambooPopupMenu extends JPopupMenu {

    public BambooPopupMenu() {
        setBorderPainted(false);
        setForeground(Color.white);
        setBackground(ColorConstants.COLOR_BACKGROUND);
        setFont(FontConstants.getSegoe(getFont().getSize()));
        setBorder(null);
    }

}
