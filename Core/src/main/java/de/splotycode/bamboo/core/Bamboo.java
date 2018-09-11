package de.splotycode.bamboo.core;

import de.splotycode.bamboo.core.tools.init.InitialisedOnce;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Bamboo extends InitialisedOnce {

    @Getter private static Bamboo instance = new Bamboo();

    protected void init() {

    }
}
