package de.splotycode.bamboo.core.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class BambooButton extends JButton {

    public BambooButton() {}

    public BambooButton(Consumer<ActionEvent> consumer) {
        addActionListener(consumer::accept);
    }

    public BambooButton(String s, Consumer<ActionEvent> consumer) {
        super(s);
        addActionListener(consumer::accept);
    }

    public BambooButton(Icon icon) {
        super(icon);
    }

    public BambooButton(String s) {
        super(s);
    }

    public BambooButton(Action action) {
        super(action);
    }

    public BambooButton(String s, Icon icon) {
        super(s, icon);
    }
}
