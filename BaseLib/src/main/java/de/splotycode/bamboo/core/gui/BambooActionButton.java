package de.splotycode.bamboo.core.gui;

import de.splotycode.bamboo.core.actions.ActionManager;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.actions.CommonAction;
import de.splotycode.bamboo.core.actions.EventCause;
import de.splotycode.bamboo.core.i18n.I18N;
import de.splotycode.bamboo.core.project.WorkSpace;
import lombok.Getter;
import lombok.Setter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
@Setter
public class BambooActionButton extends BambooButton implements ActionListener {

    private String actionName;
    private EventCause cause;
    private WorkSpace workSpace = null;

    public BambooActionButton(String name, String actionName, EventCause cause, WorkSpace workSpace) {
        super(I18N.get(name));
        this.actionName = actionName;
        this.cause = cause;
        this.workSpace = workSpace;
        addActionListener(this);
    }

    public BambooActionButton(String name, CommonAction commonAction, EventCause cause, WorkSpace workSpace) {
        this(name, commonAction.getInternalName(), cause, workSpace);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        ActionManager.getInstance().callAction(new BambooEvent(event.getSource(), event.getID(), event.getActionCommand(), workSpace, cause), actionName);
    }
}
