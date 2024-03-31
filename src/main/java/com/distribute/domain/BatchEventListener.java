package com.distribute.domain;

import com.distribute.persistence.executor.ThreadPoolComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatchEventListener implements ApplicationListener<BatchEvent> {

    private static final Integer ACTION_ONE = 1;

    @Autowired
    private ThreadPoolComponent component;


    @Override
    public void onApplicationEvent(BatchEvent event) {
        if (event.getBatch().equals(ACTION_ONE)) {
            component.execute(() -> {
                log.info("do something");
            });
        }
    }
}
