package com.distribute.persistence.mq;

import com.distribute.domain.action.BatchParam;
import com.distribute.persistence.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatchParamProductor {

    public <T> void send(BatchParam<T> param) {
        String value = JsonUtil.toTypeJson(param);
    }
}
