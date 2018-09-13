package de.splotycode.bamboo.core.actions;

import com.google.common.reflect.ClassPath;
import lombok.Getter;

import java.io.IOException;
import java.util.HashMap;

public class ActionManager {

    @Getter private static ActionManager instance = new ActionManager();

    @Getter private HashMap<String, Action> actions = new HashMap<>();

    public ActionManager() {
        try {
            for (ClassPath.ClassInfo clazzInfo : ClassPath.from(getClass().getClassLoader()).getTopLevelClassesRecursive("de.splotycode.bamboo.core.actions.impl")) {
                Class<?> clazz = clazzInfo.load();
                if (Action.class.isAssignableFrom(clazz)) {
                    Action action = (Action) clazz.newInstance();
                    actions.putIfAbsent(action.internalName(), action);
                }
            }
        } catch (IOException | ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    public void callAction(BambooEvent event, String internalName) {
        getAction(internalName).onAction(event);
    }

    public void callAction(BambooEvent event, CommonAction commonAction) {
        callAction(event, commonAction.getInternalName());
    }

    public Action getAction(String internalName) {
        return actions.get(internalName);
    }

    public Action getAction(CommonAction commonAction) {
        return getAction(commonAction.getInternalName());
    }

    public <T extends Action> T getAction(String internalName, Class<T> clazz) {
        return (T) actions.get(internalName);
    }

    public <T extends Action> T getAction(CommonAction commonAction, Class<T> clazz) {
        return getAction(commonAction.getInternalName(), clazz);
    }

}
