package de.splotycode.bamboo.core.actions;

import de.splotycode.bamboo.core.i18n.I18N;

import javax.swing.*;

public abstract class Action {

    public abstract void onAction(BambooEvent event);

    public abstract String internalName();

    public boolean canUseInToolBar() {
        return false;
    }

    public boolean canUseasShortCurt() {
        return false;
    }

    public String getDisplayName() {
        return I18N.get("actions." + internalName() + ".name");
    }

    public String getDescription() {
        return I18N.contains("actions." + internalName() + ".description") ? I18N.get("actions." + internalName() + ".description") : I18N.get("base.nodescription");
    }

    public Icon getIcon() {
        return null;
    }

}
