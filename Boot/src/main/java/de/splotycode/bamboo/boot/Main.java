package de.splotycode.bamboo.boot;

import de.splotycode.bamboo.core.Bamboo;
import de.splotycode.bamboo.core.boot.BootLoader;
import de.splotycode.bamboo.core.i18n.I18N;
import de.splotycode.bamboo.core.i18n.Locale;
import de.splotycode.bamboo.core.project.LanguageDescriptor;
import de.splotycode.bamboo.css.CSSDescriptor;
import de.splotycode.bamboo.gui.WorkspaceSelect;
import de.splotycode.bamboo.html.HtmlDescriptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //TODO get locale from config
        I18N.getInstance().setLocale(Locale.ENGLISH);

        BootLoader.getBootLoader().setShowOpenedProjects(WorkspaceSelect::new);
        BootLoader.getBootLoader().setGetDescriptors(() -> Arrays.asList(new HtmlDescriptor(), new CSSDescriptor()));
        BootLoader.getBootLoader().setReloadWorkspaces(WorkspaceSelect::reloadWorkspaces);

        Bamboo.getInstance().initalize();
    }

}
