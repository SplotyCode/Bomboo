package de.splotycode.bamboo.core.boot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BootLoader {

    @Getter private static BootLoader bootLoader = new BootLoader();

    @Getter @Setter private Runnable showOpenedProjects;

}
