package com.distribute.domain;


import com.distribute.persistence.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class MessageService {

    @Autowired
    private WebSocketServer webSocketServer;


//    @Autowired
//    private RedisTemp redisTemplate;


    public void send(String message) {
//        redisTemplate.addListener((channel, message1) -> {
//            log.info("channel: {}, message: {}", channel, message1);
//        }


        // 利用redis channel publish发送消息到所有节点
        webSocketServer.send(message);
    }

    @PostConstruct
    public void consume() {
        // 利用redis channel 消费消息 subscribe
    }
}
