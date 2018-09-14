package de.splotycode.bamboo.core.gui;

import de.splotycode.bamboo.core.i18n.I18N;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.NoSuchElementException;

public class DialogHelper {

    public static void showMessage(String name, Type type) {
        showMessage(null, name, type);
    }

    public static void showMessage(Window window, String name, Type type) {
        JOptionPane.showMessageDialog(window, I18N.get(name + ".message"), I18N.get(name + ".title"), type.type);
    }

    public static Result showInput(String title, JComponent... components) {
        return showInput(null, title, components);
    }

    public static Result showInput(Window frame, String title, JComponent... components) {
        int result = JOptionPane.showConfirmDialog(frame, components, I18N.get(title), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        switch (result) {
            case JOptionPane.YES_OPTION:
                return Result.YES;
            case JOptionPane.NO_OPTION:
                return Result.NO;
            case JOptionPane.CANCEL_OPTION:
                return Result.CANCEL;
            case JOptionPane.CLOSED_OPTION:
                return Result.CLOSED;
        }
        throw new NoSuchElementException("Result: " + result);
    }

    public enum Type {

        ERROR(0),
        INFORMATION(1),
        WARNING(2),
        QUESTION(3),
        PLAIN(-1);

        Type(int type) {
            this.type = type;
        }

        @Getter private final int type;

    }

    public enum Result {

        YES,
        NO,
        CANCEL,
        CLOSED

    }

}
