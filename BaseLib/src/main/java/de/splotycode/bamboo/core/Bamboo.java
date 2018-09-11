package de.splotycode.bamboo.core;

import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.tools.init.InitialisedOnce;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Bamboo extends InitialisedOnce {

    @Getter private static Bamboo instance = new Bamboo();

    private List<WorkSpace> workSpaces = new ArrayList<>();

    protected void init() {

    }
}
