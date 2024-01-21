package com.distribute.persistence.websocket;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint("/websocket/{name}")
public class WebSocketServer {

    private final ConcurrentHashMap<Session, String> SESSION_MAP = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name) {
        log.info("websocket open");

        SESSION_MAP.put(session, name);
    }

    @OnClose
    public void onClose() {
        log.info("websocket close");
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("websocket message: {}", message);
    }

    public void send(String message) {
        for (Map.Entry<Session, String> entry : SESSION_MAP.entrySet()) {
            Session session = entry.getKey();
            if (session.isOpen()) {
                session.getAsyncRemote().sendText(message);
            }
        }
    }

}
