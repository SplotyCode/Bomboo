package de.splotycode.bamboo.core.notification;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Notification {

    private NotificationType type;
    private String title, message;

}
