package de.splotycode.bamboo.core.util;

import de.splotycode.bamboo.core.actions.ActionManager;
import de.splotycode.bamboo.core.actions.BambooEvent;
import de.splotycode.bamboo.core.actions.CommonAction;
import de.splotycode.bamboo.core.actions.EventCause;
import de.splotycode.bamboo.core.project.WorkSpace;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class ActionUtils {

    public static BambooEvent convertEvent(AWTEvent event, String command, WorkSpace workSpace, EventCause cause) {
        return new BambooEvent(event.getSource(), event.getID(), command, workSpace, cause);
    }

    public static BambooEvent convertEvent(ActionEvent event, WorkSpace workSpace, EventCause cause) {
        return convertEvent(event, event.getActionCommand(), workSpace, cause);
    }

    public static ActionListener generateListener(String actionName, EventCause cause, WorkSpace workSpace) {
        return actionEvent -> callFromAwtEvent(actionName, actionEvent, workSpace, cause);
    }

    public static ActionListener generateListener(CommonAction action, EventCause cause, WorkSpace workSpace) {
        return generateListener(action.getInternalName(), cause, workSpace);
    }

    public static void callFromAwtEvent(String actionName, ActionEvent event, WorkSpace workSpace, EventCause cause) {
        BambooEvent bambooEvent = convertEvent(event, workSpace, cause);
        ActionManager.getInstance().callAction(bambooEvent, actionName);
    }

    public static void callFromAwtEvent(CommonAction action, ActionEvent event, WorkSpace workSpace, EventCause cause) {
        callFromAwtEvent(action.getInternalName(), event, workSpace, cause);
    }

}
