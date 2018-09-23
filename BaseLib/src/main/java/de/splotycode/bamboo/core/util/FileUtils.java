package de.splotycode.bamboo.core.util;

import de.splotycode.bamboo.core.gui.DialogHelper;
import de.splotycode.bamboo.core.i18n.I18N;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public final class FileUtils {

    public static void createFile(File file) {
        try {
            file.getParentFile().mkdirs();
            if (!file.createNewFile()) {
                //TODO I18n?
                DialogHelper.showMessageRaw(I18N.get("base.dialogs.filecreatefail.title"), I18N.get("base.dialogs.filecreatefail.message", file.getAbsolutePath(), "Already Exists"), DialogHelper.Type.WARNING);
            }
        } catch (IOException e) {
            e.printStackTrace();
            DialogHelper.showMessageRaw(I18N.get("base.dialogs.filecreatefail.title"), I18N.get("base.dialogs.filecreatefail.message", file.getAbsolutePath(), e.getMessage()), DialogHelper.Type.ERROR);
        }
    }

    private static LinkedList<File> geFilesRecursivly(LinkedList<File> files, File directory) {
        if (!directory.isDirectory()) {
            files.add(directory);
            return files;
        }

        for (File file : directory.listFiles())
            geFilesRecursivly(files, file);
        return files;
    }

    public static List<File> geFilesRecursivly(File directory) {
        LinkedList<File> files = new LinkedList<>();

        if (!directory.isDirectory()) {
            files.add(directory);
            return files;
        }

        for (File file : directory.listFiles())
            files = geFilesRecursivly(files, file);
        return files;
    }

}
