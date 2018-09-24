package de.splotycode.bamboo.core.gui.components.field;

import de.splotycode.bamboo.core.gui.components.label.BambooTranslatedLabel;

import javax.swing.*;
import java.awt.*;

public class BambooTextBox extends JPanel {

    private BambooTranslatedLabel label = new BambooTranslatedLabel();
    private BambooField field = new BambooField();

    public BambooTextBox(String name) {
        label.setText(name);

        add(label);
        add(field);
        setLayout(new GridLayout(2, 1));
    }

    public BambooTextBox(String name, String value) {
        label.setText(name);
        field.setText(value);
    }

    public String getText() {
        return field.getText();
    }

    public void setText(String text) {
        field.setText(text);
    }

}
