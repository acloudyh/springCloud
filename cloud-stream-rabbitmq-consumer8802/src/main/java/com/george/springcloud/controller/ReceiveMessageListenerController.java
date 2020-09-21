package com.george.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author Yang Hao
 * @description 消费者
 * @date 2020-09-21 15:32
 */
@Component
@Slf4j
@EnableBinding(Sink.class)
public class ReceiveMessageListenerController {

    @Value("${server.port}")
    private String serverPort;

    /**
     * StreamListener 注解, 监听队列, 用于消费者队列的消息接收
     *
     * @param message
     */
    @StreamListener(Sink.INPUT)
    public void input(Message<String> message) {
        log.info("消费者1号 ------> 接收到的消息：[{}]; port:[{}]", message.getPayload(), serverPort);
    }

}
