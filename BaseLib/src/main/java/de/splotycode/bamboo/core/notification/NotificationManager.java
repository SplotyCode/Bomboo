package de.splotycode.bamboo.core.notification;

import de.splotycode.bamboo.core.i18n.I18N;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class NotificationManager {

    private Set<Consumer<Notification>> listeners = new HashSet<>();

    public void addListener(Consumer<Notification> listener) {
        listeners.add(listener);
    }

    public void removeListener(Consumer<Notification> listener) {
        listeners.remove(listener);
    }

    public void push(Notification notification) {
        listeners.forEach(listener -> listener.accept(notification));
    }

    public void push(String title, String message, NotificationType type) {
       push(new Notification(type, title, message));
    }

    public void push(String landKey, NotificationType type) {
        push(I18N.get("notification." + landKey + ".title"), I18N.get("notification." + landKey + ".message"), type);
    }

}
