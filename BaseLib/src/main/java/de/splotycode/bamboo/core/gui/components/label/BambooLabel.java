package de.splotycode.bamboo.core.gui.components.label;

import de.splotycode.bamboo.core.gui.FontConstants;
import de.splotycode.bamboo.core.i18n.I18N;

import javax.swing.*;
import java.awt.*;

public class BambooLabel extends JLabel {

    public BambooLabel() {
        init();
    }

    public BambooLabel(String s, Icon icon, int i) {
        super(s, icon, i);
        init();
    }

    public BambooLabel(String s, int i) {
        super(s, i);
        init();
    }

    public BambooLabel(String s) {
        super(s);
        init();
    }

    public void init() {
        setForeground(Color.white);
        setFont(FontConstants.getSegoe(getFont().getSize()));
    }

}
