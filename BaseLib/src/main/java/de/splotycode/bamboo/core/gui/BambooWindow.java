package de.splotycode.bamboo.core.gui;

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

    @Override
    public void setSize(int width, int height) {
        Dimension dimension = new Dimension(width, height);
        super.setSize(width, height);
        setPreferredSize(dimension);
    }

    public void center() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }

}
