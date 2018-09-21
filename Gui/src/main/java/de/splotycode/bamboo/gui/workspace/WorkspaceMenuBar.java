package de.splotycode.bamboo.gui.workspace;

import de.splotycode.bamboo.core.actions.Action;
import de.splotycode.bamboo.core.actions.ActionManager;
import de.splotycode.bamboo.core.actions.EventCause;
import de.splotycode.bamboo.core.i18n.I18N;
import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.util.ActionUtils;
import de.splotycode.bamboo.core.yaml.YamlConfiguration;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WorkspaceMenuBar extends JMenuBar {

    private WorkSpace workSpace;

    public WorkspaceMenuBar(WorkSpace workSpace) {
        this.workSpace = workSpace;
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
            JMenu menu = new JMenu(I18N.get("menubar." + menuName));
            for (String itemName : configuration.getStringList(menuName)) {
                Action action = ActionManager.getInstance().getAction(itemName);
                JMenuItem item = new JMenuItem(action.getDisplayName());
                item.setToolTipText(action.getDescription());
                item.addActionListener(ActionUtils.generateListener(action.internalName(), EventCause.MENU, workSpace));
                menu.add(item);
            }
            add(menu);
        }
    }

}
