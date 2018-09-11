package de.splotycode.bamboo.gui;

import javax.swing.*;
import java.awt.*;

public class BambooWindow extends JFrame {

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
