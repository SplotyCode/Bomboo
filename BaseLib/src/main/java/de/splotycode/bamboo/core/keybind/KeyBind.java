package de.splotycode.bamboo.core.keybind;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.event.KeyEvent;

@Data
@AllArgsConstructor
public class KeyBind {

    public boolean match(KeyEvent event) {
        return modifiers == event.getModifiers() && event.getKeyChar() == keyCode;
    }

    private int modifiers;
    private char keyCode;
    private String action;

}
