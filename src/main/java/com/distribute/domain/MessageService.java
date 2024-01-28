package com.distribute.domain;


import com.distribute.persistence.constant.CacheConstant;
import com.distribute.persistence.websocket.WebSocketParam;
import com.distribute.persistence.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

@Slf4j
@Service
public class MessageService {

    @Autowired
    private WebSocketServer webSocketServer;

    @Autowired
    private RedissonClient client;

    static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, new BasicThreadFactory.Builder().namingPattern("WebSocketServer-reconnect-schedule-pool-%d").daemon(true).build());

    @PostConstruct
    public <T> void config() {
        log.info("WsSubscriber-init");
        // 订阅消息 channel type, consumer
        executor.execute(() -> this.subscribe(CacheConstant.WEBSOCKET_MESSAGE_CHANNEL, WebSocketParam.class, channelMessage -> {
            log.info("CasinoMessageService-收到消息: {}", channelMessage);
            Object pushData = channelMessage.getData();
            String message = channelMessage.getMessage();

           webSocketServer.send(message);
        }));
    }


    public <T> void publish(String channelKey, T msg) {
        RTopic topic = client.getTopic(channelKey);
        topic.publish(msg);
    }

    public <T> void subscribe(String channelKey, Class<T> clazz, Consumer<T> consumer) {
        RTopic topic = client.getTopic(channelKey);
        topic.addListener(clazz, (channel, msg) -> consumer.accept(msg));
    }

    public void send(String message) {
        // 利用redis channel publish发送消息到所有节点
        this.publish(CacheConstant.WEBSOCKET_MESSAGE_CHANNEL,message);
    }

}
