/*
package com.dresglas.rabbitmqdemo.demo13;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WarningConsumer {
    public static final String WARNING_QUEUE_NAME = "warning.queue";
    private static final Logger logger= LoggerFactory.getLogger(WarningConsumer.class);
    @RabbitListener(queues = WARNING_QUEUE_NAME)
    public void receiveWarningMsg(Message message) {
        String msg = new String( message.getBody() );
        logger.error( "报警发现不可路由消息：{}", msg );
    }
}
*/
