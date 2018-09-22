package de.splotycode.bamboo.core.project;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import de.splotycode.bamboo.core.Bamboo;
import de.splotycode.bamboo.core.boot.BootLoader;
import de.splotycode.bamboo.core.editor.Editor;
import de.splotycode.bamboo.core.notification.NotificationManager;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class WorkSpace {

    @Getter private NotificationManager notifications;
    @Getter private SimpleProjectInformation information;

    @Getter private Set<Project> projects = new HashSet<>();

    @Getter private WorkspaceWindow window;
    @Getter private Explorer explorer;

    @Getter private JSplitPane mainSplit;

    @Getter private JTabbedPane editorTabs = new JTabbedPane();

    @Getter private BiMap<Editor, File> editors = HashBiMap.create();

    public WorkSpace(SimpleProjectInformation information) {
        this.information = information;
        notifications = new NotificationManager();
        window = BootLoader.getBootLoader().getGenerateWindow().apply(this);
        explorer = BootLoader.getBootLoader().getGenerateExplorer().apply(this);

        mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, explorer.getComponent(), editorTabs);
        window.add(mainSplit);

        explorer.open(information.getBambooFile().getParentFile());
    }

    public void openFile(File file) {
        if (editors.inverse().containsKey(file)) {
            editorTabs.getModel().setSelectedIndex(editorTabs.indexOfComponent(editors.inverse().get(file)));
        } else {
            Editor editor = new Editor(file);
            editors.put(editor, file);
            editorTabs.addTab(file.getName(), editor);
        }
    }

    public void close() {
        Bamboo.getInstance().getOpenProjects().remove(this);
        explorer.saveExpanded();
        window.closeWindow();
    }

}