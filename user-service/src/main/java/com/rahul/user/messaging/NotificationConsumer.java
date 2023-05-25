package com.rahul.user.messaging;


import com.rahul.commons.dto.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class NotificationConsumer {

    @RabbitListener(queues = "${rabbitmq.queues.notification}")
    public void consumer(User notificationRequest) {
        log.info("Consumed {} from queue", notificationRequest);
    }

}

