package de.splotycode.bamboo.core.gui.components.label;

import de.splotycode.bamboo.core.i18n.I18N;

import javax.swing.*;

public class BambooTranslatedLabel extends BambooLabel {

    public BambooTranslatedLabel() {
    }

    public BambooTranslatedLabel(String s, Icon icon, int i) {
        super(I18N.get(s), icon, i);
    }

    public BambooTranslatedLabel(String s, int i) {
        super(I18N.get(s), i);
    }

    public BambooTranslatedLabel(String s) {
        super(I18N.get(s));
    }

    @Override
    public void setText(String s) {
        super.setText(s.isEmpty() ? "" : I18N.get(s));
    }

    public BambooLabel setRawText(String s) {
        super.setText(s);
        return this;
    }

    public void setText(String s, String... objects) {
        super.setText(I18N.get(s, objects));
    }

}
