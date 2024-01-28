package com.distribute.persistence.mq;

import com.distribute.domain.action.BatchParam;
import com.distribute.domain.action.Outcome;
import com.distribute.persistence.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatchParamProductor {

    @Autowired
    private RedissonClient client;


    public <T> void send(BatchParam<T> param) {
        String value = JsonUtil.toTypeJson(param);
        try {
            this.send(value);
        } catch (Exception e) {
            log.error("发送消息失败：{}", value, e);
            Outcome outcome = new Outcome();
            outcome.setSuccess(false);
            outcome.setData(e.getMessage());
            client.getBlockingQueue(param.getQueueName()).add(outcome);
        }
    }

    private void send(String message) {

    }
}
