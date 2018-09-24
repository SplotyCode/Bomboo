package de.splotycode.bamboo.core.actions.impl.explorer;

import de.splotycode.bamboo.core.actions.AbstractAction;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.data.ExplorerDataKeys;
import de.splotycode.bamboo.core.project.Explorer;

import java.io.File;

public abstract class AbstractExplorerAction extends AbstractAction {

    @Override
    public final void onAction(BambooEvent event) {
        Explorer explorer = event.getDataFactory().getData(ExplorerDataKeys.EXPLORER);
        File file = event.getDataFactory().getData(ExplorerDataKeys.SELECTED_FILE);
        explorerAction(explorer, file, event);
    }

    protected abstract void explorerAction(Explorer explorer, File file, BambooEvent event);

}
