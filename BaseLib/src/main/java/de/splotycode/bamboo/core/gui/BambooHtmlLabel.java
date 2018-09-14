package de.splotycode.bamboo.core.gui;

import javax.swing.*;

public class BambooHtmlLabel extends JLabel {

    public BambooHtmlLabel(String s, Icon icon, int i) {
        super("<html>" + s + "</html>", icon, i);
    }

    public BambooHtmlLabel(String s, int i) {
        super("<html>" + s + "</html>", i);
    }

    public BambooHtmlLabel(String s) {
        super("<html>" + s + "</html>");
    }

    @Override
    public void setText(String s) {
        super.setText("<html>" + s + "</html>");
    }
}
