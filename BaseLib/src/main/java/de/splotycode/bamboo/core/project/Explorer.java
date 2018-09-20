package de.splotycode.bamboo.core.project;

import javax.swing.*;
import java.io.File;
import java.util.function.Function;

public interface Explorer {

    JComponent getComponent();

    void open(File file);
    void selectFile(File file);
    void update();

    Function<File, JPopupMenu> generatePopup();

    File selectedFile();
    File baseDirectory();

    WorkSpace getWorkSpace();

}
