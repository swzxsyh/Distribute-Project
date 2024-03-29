package com.distribute.persistence.websocket;

import lombok.Data;

@Data
public class WebSocketParam<T> {

    private String domain;

    private Long userId;

    private String message;

    private T data;
}