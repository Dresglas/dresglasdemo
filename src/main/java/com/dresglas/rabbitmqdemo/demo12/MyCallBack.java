package com.dresglas.rabbitmqdemo.demo12;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private static final Logger logger= LoggerFactory.getLogger( MyCallBack.class);
    /**
     * 交换机不管是否收到消息的一个回调方法
     * CorrelationData
     * 消息相关数据
     * ack
     * 交换机是否收到消息
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack) {
            logger.info( "交换机已经收到 id 为:{}的消息", id );
        } else {
            logger.info( "交换机还未收到 id 为:{}消息,由于原因:{}", id, cause );
        }
    }

    //当消息无法路由的时候的回调方法
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String
            exchange, String routingKey) {
        logger.error( " 消 息 {}, 被交换机 {} 退回，退回原因 :{}, 路 由 key:{}", new
                String( message.getBody() ), exchange, replyText, routingKey );
    }
}