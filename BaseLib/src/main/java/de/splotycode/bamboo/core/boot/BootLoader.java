package de.splotycode.bamboo.core.boot;

import de.splotycode.bamboo.core.project.Explorer;
import de.splotycode.bamboo.core.project.LanguageDescriptor;
import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.project.WorkspaceWindow;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BootLoader {

    @Getter private static BootLoader bootLoader = new BootLoader();

    @Getter @Setter private Runnable showOpenedProjects;
    @Getter @Setter private Supplier<Boolean> selectWorkspaceOpen;
    @Getter @Setter private Runnable closeSelectWorkspace;

    @Getter @Setter private Supplier<List<LanguageDescriptor>> getDescriptors;

    @Getter @Setter private Runnable reloadWorkspaces;

    @Getter @Setter private Function<WorkSpace, WorkspaceWindow> generateWindow;
    @Getter @Setter private Function<WorkSpace, Explorer> generateExplorer;

}
