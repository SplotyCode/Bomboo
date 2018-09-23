package de.splotycode.bamboo.core.project;

import javax.swing.*;
import java.io.File;

public interface Explorer {

    JComponent getComponent();

    void update();

    File selectedFile();

    WorkSpace getWorkSpace();

    void saveExpanded();

}
