package de.splotycode.bamboo.core.actions;

import lombok.Getter;

public enum CommonAction {

    CREATE_WORKSPACE("file.workspace.create"),
    LOAD_WORKSPACE("file.workspace.load");

    @Getter private final String internalName;

    CommonAction(String internalName) {
        this.internalName = internalName;
    }
}
