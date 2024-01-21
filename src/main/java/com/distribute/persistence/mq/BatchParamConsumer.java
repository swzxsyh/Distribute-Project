package com.distribute.persistence.mq;

import com.distribute.domain.action.AbstractAction;
import com.distribute.domain.action.BatchParam;
import com.distribute.persistence.executor.CustomizeThreadFactory;
import com.distribute.persistence.util.JsonUtil;
import com.distribute.persistence.util.SpringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class BatchParamConsumer {

    private final static int THREAD_NUM = Runtime.getRuntime().availableProcessors();
    private static final ThreadPoolExecutor component = new ThreadPoolExecutor(THREAD_NUM, THREAD_NUM * 2, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(THREAD_NUM * 5), new CustomizeThreadFactory("CUSTOMIZE-CONSUMER-"), new ThreadPoolExecutor.CallerRunsPolicy());

    public <T> void consume(String message) {
        try {
            component.execute(() -> handle(message));
        } finally {
            // ack
            log.info("消费消息：{}", message);
        }
    }


    private <T> void handle(String value) {
        log.info("消费消息：{}", value);
        BatchParam<T> batchParam = JsonUtil.toClass(value, new TypeReference<BatchParam<T>>() {
        });

        AbstractAction<T> bean = SpringUtils.getBean(batchParam.getBeanName());
        if (Objects.nonNull(bean)) {
            bean.doAction(batchParam);
        } else {
            log.error("未找到beanName为{}的bean", batchParam.getBeanName());
        }
    }
}
