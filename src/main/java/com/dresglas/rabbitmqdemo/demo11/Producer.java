/*
package com.dresglas.rabbitmqdemo.demo11;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/confirm")
@Slf4j
public class Producer {
    public static final String CONFIRM_EXCHANGE_NAME = "confirm.exchange";
    private static final Logger logger= LoggerFactory.getLogger( Producer.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MyCallBack11 myCallBack11;

    //依赖注入 rabbitTemplate 之后再设置它的回调对象
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(myCallBack11);
    }

    @GetMapping("sendMessage/{message}")
    public void sendMessage(@PathVariable String message) {
        //指定消息 id 为 1
        CorrelationData correlationData1 = new CorrelationData( "1" );
        String routingKey = "key1";

        rabbitTemplate.convertAndSend( CONFIRM_EXCHANGE_NAME, routingKey, message + routingKey, correlationData1 );
        CorrelationData correlationData2 = new CorrelationData( "2" );
        routingKey = "key2";

        rabbitTemplate.convertAndSend( CONFIRM_EXCHANGE_NAME, routingKey, message + routingKey, correlationData2 );
        logger.info( "发送消息内容:{}", message );
    }
}*/
