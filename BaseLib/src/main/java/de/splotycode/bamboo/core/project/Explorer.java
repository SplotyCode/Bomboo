package de.splotycode.bamboo.core.project;

import javax.swing.*;
import java.io.File;

public interface Explorer {

    JComponent getComponent();

    void open(File file);
    void update();

    File selectedFile();
    File baseDirectory();

    WorkSpace getWorkSpace();

    void saveExpanded();

}
