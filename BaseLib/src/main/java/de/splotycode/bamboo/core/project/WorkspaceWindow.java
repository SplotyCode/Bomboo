package de.splotycode.bamboo.core.project;

import de.splotycode.bamboo.core.gui.components.BambooWindow;

import javax.swing.*;

public interface WorkspaceWindow {

    void add(JComponent component);
    void closeWindow();

    BambooWindow getWindow();

}
