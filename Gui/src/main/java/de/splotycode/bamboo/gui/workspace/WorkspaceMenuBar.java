package de.splotycode.bamboo.gui.workspace;

import de.splotycode.bamboo.core.yaml.YamlConfiguration;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WorkspaceMenuBar extends JMenuBar {

    public WorkspaceMenuBar() {
        YamlConfiguration configuration = new YamlConfiguration();
        try {
            InputStream is = WorkspaceMenuBar.class.getResourceAsStream("/menubar.yml");
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            configuration.load(br);
            br.close();
            is.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        for (String menuName : configuration.getKeys(false)) {
            JMenu menu = new JMenu();
            menu.setName(menuName);
            for (String itemName : configuration.getStringList(menuName)) {
                JMenuItem item = new JMenuItem();
                item.setName(itemName);
                menu.add(item);
            }
            add(menu);
        }
    }

}
