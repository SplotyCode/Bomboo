package de.splotycode.bamboo.boot;

import de.splotycode.bamboo.core.Bamboo;
import de.splotycode.bamboo.core.boot.BootLoader;
import de.splotycode.bamboo.core.i18n.I18N;
import de.splotycode.bamboo.core.i18n.Locale;
import de.splotycode.bamboo.gui.WorkspaceSelect;

public class Main {

    public static void main(String[] args) {
        //TODO get locale from config
        I18N.getInstance().setLocale(Locale.ENGLISH);

        BootLoader.getBootLoader().setShowOpenedProjects(WorkspaceSelect::new);
        Bamboo.getInstance().initalize();
    }

}
