package de.splotycode.bamboo.core.gui.components;

import de.splotycode.bamboo.core.project.WorkSpace;
import lombok.AllArgsConstructor;

import java.awt.*;

@AllArgsConstructor
public class BambooEditorPane extends BambooTabbedPane {

    private WorkSpace workSpace;

    @Override
    public Color getForegroundAt(int i) {
        if (workSpace.getEditorTabMap().get(workSpace.getEditorTabs().getComponentAt(i)).hasChanged()) {
            return Color.red;
        }
        return Color.white;
    }

}
