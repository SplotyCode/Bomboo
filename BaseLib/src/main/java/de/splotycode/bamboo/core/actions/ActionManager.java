package de.splotycode.bamboo.core.actions;

import com.google.common.reflect.ClassPath;
import lombok.Getter;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class ActionManager {

    @Getter private static ActionManager instance = new ActionManager();

    @Getter private HashMap<String, AbstractAction> actions = new HashMap<>();

    public ActionManager() {
        try {
            for (ClassPath.ClassInfo clazzInfo : ClassPath.from(getClass().getClassLoader()).getTopLevelClassesRecursive("de.splotycode.bamboo.core.actions.impl")) {
                Class<?> clazz = clazzInfo.load();
                if (AbstractAction.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
                    AbstractAction action = (AbstractAction) clazz.newInstance();
                    actions.putIfAbsent(action.internalName(), action);
                }
            }
        } catch (IOException | ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    public void callAction(BambooEvent event, String internalName) {
        System.out.println("Calling Action " + internalName);
        getAction(internalName).onAction(event);
    }

    public void callAction(BambooEvent event, CommonAction commonAction) {
        callAction(event, commonAction.getInternalName());
    }

    public AbstractAction getAction(String internalName) {
        return actions.get(internalName);
    }

    public AbstractAction getAction(CommonAction commonAction) {
        return getAction(commonAction.getInternalName());
    }

    public <T extends AbstractAction> T getAction(String internalName, Class<T> clazz) {
        return (T) actions.get(internalName);
    }

    public <T extends AbstractAction> T getAction(CommonAction commonAction, Class<T> clazz) {
        return getAction(commonAction.getInternalName(), clazz);
    }

}
