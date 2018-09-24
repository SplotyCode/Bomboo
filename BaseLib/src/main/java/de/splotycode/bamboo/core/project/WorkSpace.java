package de.splotycode.bamboo.core.project;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import de.splotycode.bamboo.core.Bamboo;
import de.splotycode.bamboo.core.boot.BootLoader;
import de.splotycode.bamboo.core.editor.Editor;
import de.splotycode.bamboo.core.gui.ColorConstants;
import de.splotycode.bamboo.core.gui.DialogHelper;
import de.splotycode.bamboo.core.gui.components.BambooSplitPane;
import de.splotycode.bamboo.core.gui.components.BambooTabbedPane;
import de.splotycode.bamboo.core.notification.NotificationManager;
import de.splotycode.bamboo.core.util.FileUtils;
import lombok.Getter;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.io.File;
import java.util.*;

public class WorkSpace {

    @Getter private NotificationManager notifications;
    @Getter private SimpleProjectInformation information;

    @Getter private Set<Project> projects = new HashSet<>();

    @Getter private WorkspaceWindow window;
    @Getter private Explorer explorer;

    @Getter private BambooSplitPane mainSplit;

    @Getter private BambooTabbedPane editorTabs = new BambooTabbedPane();

    @Getter private BiMap<Editor, File> editors = HashBiMap.create();

    public WorkSpace(SimpleProjectInformation information) {
        this.information = information;
        notifications = new NotificationManager();
        window = BootLoader.getBootLoader().getGenerateWindow().apply(this);
        explorer = BootLoader.getBootLoader().getGenerateExplorer().apply(this);

        mainSplit = new BambooSplitPane(JSplitPane.HORIZONTAL_SPLIT, explorer.getComponent(), editorTabs);
        mainSplit.setDividerLocation(0.15);
        window.add(mainSplit);
    }

    public File getBaseDirecotory() {
        return information.getBambooFile().getParentFile();
    }

    public void openFile(File file) {
        LanguageDescriptor descriptor = getProjectBySomeFile(file).getDescriptorByFile(file);
        if (descriptor == null) {
            DialogHelper.showMessage(window.getWindow(), "workspace.nodescriptor", DialogHelper.Type.WARNING);
            return;
        }
        openFile(file, descriptor);
    }

    public void openFile(File file, LanguageDescriptor descriptor) {
        if (editors.inverse().containsKey(file)) {
            editorTabs.getModel().setSelectedIndex(editorTabs.indexOfComponent(editors.inverse().get(file).getComponent()));
        } else {
            Editor editor = new Editor(file, descriptor, this);
            editors.put(editor, file);
            editorTabs.addTab(file.getName(), editor.getComponent());
        }
    }

    public void reloadFileSystem() {
        explorer.update();
        for (Project project : projects) {
            Set<LanguageDescriptor> neededDescriptors = new HashSet<>();
            for (File file : FileUtils.geFilesRecursivly(project.workSpace())) {
                LanguageDescriptor descriptor = BootLoader.getBootLoader().getGetDescriptors().get()
                        .stream().filter(des -> {
                            return Arrays.asList(des.fileTypes()).contains(FilenameUtils.getExtension(file.getName()));
                        }).findFirst().orElse(null);
                if (descriptor != null)
                    neededDescriptors.add(descriptor);
            }
            for (LanguageDescriptor descriptor : new ArrayList<>(project.getLanguageDescriptors())) {
                if (!neededDescriptors.contains(descriptor)) {
                    project.uninstallLanguageDescriptor(descriptor);
                }
            }
            for (LanguageDescriptor descriptor : new ArrayList<>(neededDescriptors)) {
                if (!project.getLanguageDescriptors().contains(descriptor)) {
                    project.installLanguageDescriptor(descriptor);
                }
            }
        }
    }

    public List<String> getBambooProjectFiles() {
        List<String> list = new ArrayList<>();
        for (Project project : projects) {
            list.add(project.bambooFile().getAbsolutePath());
        }
        return list;
    }

    public Project getProjectBySomeFile(File checkFile) {
        for (Project project : projects) {
            for (File file : FileUtils.geFilesRecursivly(project.workSpace())) {
                if (file.getAbsolutePath().equals(checkFile.getAbsolutePath())) {
                    return project;
                }
            }
        }
        return null;
    }

    public void close() {
        Bamboo.getInstance().getOpenProjects().remove(this);
        explorer.saveExpanded();
        window.closeWindow();
    }

}