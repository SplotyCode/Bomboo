package de.splotycode.bamboo.core.i18n;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.HashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class I18N {

    @Getter private static I18N instance = new I18N();

    private static I18N english = new I18N().setLocale(Locale.ENGLISH);

    private HashMap<String, String> map = new HashMap<>();

    public static boolean contains(String key) {
        return instance.map.containsKey(key) || english.map.containsKey(key);
    }

    public static String get(String key, String... objects) {
        String value = instance.map.get(key);
        if (value == null) {
            value = english.map.get(key);
            System.err.println("Failed to find " + key + " in current Locale");
        }
        if (value == null) {
            System.err.println("Failed to find " + key + " in english Locale");
            value = "I18N Error";
            try {
                new Throwable().printStackTrace();
            } catch (Throwable ex) {}
        }
        return MessageFormat.format(value, (Object[]) objects);
    }

    public I18N setLocale(Locale locale) {
        map.clear();

        try {
            InputStream stream = locale.getLanguageInputStream();
            String string = IOUtils.toString(stream, Charset.forName("Utf-8"));
            for (String line : string.split("\n")) {
                if (line.startsWith("#") || line.isEmpty()) continue;
                String[] split = line.split(": ");
                map.put(split[0], split[1]);
            }
            stream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return this;
    }

}
