package de.splotycode.bamboo.core.keybind;

import de.splotycode.bamboo.core.Bamboo;
import de.splotycode.bamboo.core.yaml.YamlConfiguration;
import lombok.Getter;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class KeyMapManager {

    private KeyMap current;
    @Getter private Set<KeyMap> all = new HashSet<>();

    public KeyMap current() {
        return current;
    }

    public void init() {
        for (File file : new File(Bamboo.getInstance().getBambooFolder(), "keymaps").listFiles()) {
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            KeyMap map = new KeyMap(configuration);
            all.add(map);
            if (configuration.getBoolean("selected")) {
                if (current != null) {
                    throw new IllegalStateException("Can not select two keymaps");
                }
                current = map;
            }
        }
    }
}
