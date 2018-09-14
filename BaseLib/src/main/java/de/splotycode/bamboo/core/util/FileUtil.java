package de.splotycode.bamboo.core.util;

import java.io.File;
import java.io.IOException;

public final class FileUtil {

    public static void createFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
