package de.splotycode.bamboo.core.icon;

import javax.swing.*;

public enum Icons {

    OPEN("ToolBar.Open");

    private final String name;

    Icons(String name) {
        this.name = name;
    }

    public Icon getIcon () {
        return UIManager.getIcon(name);
    }

}
