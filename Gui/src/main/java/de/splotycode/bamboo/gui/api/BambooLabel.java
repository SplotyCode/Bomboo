package de.splotycode.bamboo.gui.api;

import de.splotycode.bamboo.core.i18n.I18N;

import javax.swing.*;

public class BambooLabel extends JLabel {

    public BambooLabel() {}

    public BambooLabel(String s, Icon icon, int i) {
        super(I18N.get(s), icon, i);
    }

    public BambooLabel(String s, int i) {
        super(I18N.get(s), i);
    }

    public BambooLabel(String s) {
        super(I18N.get(s));
    }

    @Override
    public void setText(String s) {
        super.setText(I18N.get(s));
    }

    public void setRawText(String s) {
        super.setText(s);
    }

    public void setText(String s, String... objects) {
        super.setText(I18N.get(s, objects));
    }

}
