package de.splotycode.bamboo.core.boot;

import de.splotycode.bamboo.core.project.LanguageDescriptor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BootLoader {

    @Getter private static BootLoader bootLoader = new BootLoader();

    @Getter @Setter private Runnable showOpenedProjects;

    @Getter @Setter private Supplier<List<LanguageDescriptor>> getDescriptors;

    @Getter @Setter private Runnable reloadWorkspaces;

}
