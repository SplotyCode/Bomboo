package de.splotycode.bamboo.core.gui.components.label;

import javax.swing.*;
import java.awt.*;

public class BambooHtmlLabel extends BambooLabel {

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
