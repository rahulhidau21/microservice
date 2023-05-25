package com.rahul.user.service.impl;

import com.rahul.commons.config.RabbitMQMessageProducer;
import com.rahul.commons.dto.User;
import com.rahul.user.config.NotificationConfig;
import com.rahul.user.service.INotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService implements INotificationService {

    private RabbitMQMessageProducer rabbitMQMessageProducer;

    private NotificationConfig notificationConfig;

    @Override
    public void send() {
        rabbitMQMessageProducer.publish(new User((long) 123, "rahul"), notificationConfig.getInternalExchange(),
                notificationConfig.getInternalNotificationRoutingKey());
    }
}
