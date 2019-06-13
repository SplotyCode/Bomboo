package de.splotycode.bamboo.core.keybind;

import de.splotycode.bamboo.core.actions.ActionManager;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.actions.EventCause;
import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.util.ActionUtils;
import de.splotycode.bamboo.core.yaml.ConfigurationSection;
import de.splotycode.bamboo.core.yaml.YamlConfiguration;
import lombok.Data;
import lombok.Getter;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

@Getter
@Data
public class KeyMap {

    private String name;
    private Set<KeyBind> keyBinds = new HashSet<>();

    public KeyMap(YamlConfiguration configuration) {
        this.name = configuration.getString("name");
        ConfigurationSection binds = configuration.getConfigurationSection("binds");
        for (String bindSection : binds.getKeys(false)) {
            ConfigurationSection bind = binds.getConfigurationSection(bindSection);
            keyBinds.add(new KeyBind(bind.getInt("mod"),
                    (char) bind.getInt("char"),
                    bind.getString("action")));
        }
    }

    public void onPress(WorkSpace workSpace, KeyEvent e) {
        KeyBind bind = keyBinds.stream().filter(keyBind -> keyBind.match(e)).findAny().orElse(null);
        if (bind != null) {
            BambooEvent event = ActionUtils.convertEvent(e, bind.toString(), workSpace, EventCause.SHORTCURT);
            ActionManager.getInstance().callAction(event, bind.getAction());
        }
    }

}
