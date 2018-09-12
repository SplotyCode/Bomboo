package de.splotycode.bamboo.core.actions;

import de.splotycode.bamboo.core.i18n.I18N;

public abstract class Action {

    public abstract String getName();

    public abstract void onAction(BambooEvent event);

    public String getDescription() {
        return I18N.get("base.nodescription");
    }

    public Icon getIcon() {
        return null;
    }

}
