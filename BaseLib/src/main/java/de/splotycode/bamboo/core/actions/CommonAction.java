package de.splotycode.bamboo.core.actions;

import lombok.Getter;

public enum CommonAction {

    ;

    @Getter private final String internalName;

    CommonAction(String internalName) {
        this.internalName = internalName;
    }
}
