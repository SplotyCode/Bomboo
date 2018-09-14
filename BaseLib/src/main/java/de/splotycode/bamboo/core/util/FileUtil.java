package de.splotycode.bamboo.core.util;

import de.splotycode.bamboo.core.gui.DialogHelper;
import de.splotycode.bamboo.core.i18n.I18N;

import java.io.File;
import java.io.IOException;

public final class FileUtil {

    public static void createFile(File file) {
        try {
            if (!file.createNewFile()) {
                //TODO I18n?
                DialogHelper.showMessageRaw(I18N.get("base.dialogs.filecreatefail.title"), I18N.get("base.dialogs.filecreatefail.message", file.getAbsolutePath(), "Already Exists"), DialogHelper.Type.WARNING);
            }
        } catch (IOException e) {
            e.printStackTrace();
            DialogHelper.showMessageRaw(I18N.get("base.dialogs.filecreatefail.title"), I18N.get("base.dialogs.filecreatefail.message", file.getAbsolutePath(), e.getMessage()), DialogHelper.Type.ERROR);
        }
    }

}
