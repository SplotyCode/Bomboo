package de.splotycode.bamboo.gui.workspace;

import de.splotycode.bamboo.core.actions.AbstractAction;
import de.splotycode.bamboo.core.actions.ActionManager;
import de.splotycode.bamboo.core.actions.EventCause;
import de.splotycode.bamboo.core.gui.components.menu.BambooMenu;
import de.splotycode.bamboo.core.gui.components.menu.BambooMenuBar;
import de.splotycode.bamboo.core.gui.components.menu.BambooMenuItem;
import de.splotycode.bamboo.core.i18n.I18N;
import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.util.ActionUtils;
import de.splotycode.bamboo.core.yaml.YamlConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WorkspaceMenuBar extends BambooMenuBar {

    private WorkSpace workSpace;

    public WorkspaceMenuBar(WorkSpace workSpace) {
        this.workSpace = workSpace;
        YamlConfiguration configuration = YamlConfiguration.loadResource("menubar.yml");
        for (String menuName : configuration.getKeys(false)) {
            BambooMenu menu = new BambooMenu(I18N.get("menubar." + menuName));
            for (String itemName : configuration.getStringList(menuName)) {
                AbstractAction action = ActionManager.getInstance().getAction(itemName);
                BambooMenuItem item = new BambooMenuItem(action.getDisplayName());
                item.setToolTipText(action.getDescription());
                item.addActionListener(ActionUtils.generateListener(action.internalName(), EventCause.MENU, workSpace));
                menu.add(item);
            }
            add(menu);
        }
    }

}
