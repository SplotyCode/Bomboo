package de.splotycode.bamboo.gui.api;

import de.splotycode.bamboo.core.i18n.I18N;

import javax.swing.*;
import java.awt.*;

public class BambooWindow extends JFrame {

    @Override
    public void setTitle(String s) {
        super.setTitle(I18N.get(s));
    }

    public void makeBorderLess() {
        setUndecorated(true);
        setLayout(null);
    }

    public void center() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }

}
