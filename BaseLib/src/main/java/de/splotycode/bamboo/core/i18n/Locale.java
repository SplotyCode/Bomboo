package de.splotycode.bamboo.core.i18n;

import java.io.InputStream;

public enum Locale {

    DEUTSCH,
    ENGLISH;

    public InputStream getLanguageInputStream() {
        return getClass().getResourceAsStream("/translations/" + name().toLowerCase() + ".txt");
    }

}
