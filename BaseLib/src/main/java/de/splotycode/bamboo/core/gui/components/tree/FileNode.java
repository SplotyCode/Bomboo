package de.splotycode.bamboo.core.gui.components.tree;

import de.splotycode.bamboo.core.project.Project;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

@AllArgsConstructor
@Data
public class FileNode {

    private File file;
    private String displayName;
    private Project project;

    @Override
    public String toString() {
        return displayName;
    }

}
