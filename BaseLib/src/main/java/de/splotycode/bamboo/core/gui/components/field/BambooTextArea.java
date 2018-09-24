package de.splotycode.bamboo.core.gui.components.field;

import de.splotycode.bamboo.core.gui.ColorConstants;
import de.splotycode.bamboo.core.gui.FontConstants;

import javax.swing.*;
import java.awt.*;

public class BambooTextArea extends JTextArea {

    public BambooTextArea() {
        setText("");
        init();
    }

    public BambooTextArea(String s) {
        super(s);
        init();
    }

    private void init() {
        setCaretColor(Color.white);
        setBackground(ColorConstants.COLOR_BACKGROUND);
        setForeground(Color.white);
        setFont(FontConstants.getSegoe(getFont().getSize()));
    }
}
