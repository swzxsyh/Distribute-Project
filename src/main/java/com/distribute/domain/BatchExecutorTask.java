package com.distribute.domain;

import com.distribute.persistence.util.LockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class BatchExecutorTask {

    @Resource
    private BatchEventListener eventListener;

//    @Scheduled(fixedRate = 1000L)
    public void batch() {
        final ActionBo bo = new ActionBo();

        String key = "lock:action:" + bo.getAction();
        LockUtil.lockMutex(key, () -> this.doAction(bo));
    }


    public void doAction(ActionBo bo) {
        final CountDownLatch countDownLatch = new CountDownLatch(bo.getSecond());

        while (countDownLatch.getCount() != 0) {
            eventListener.onApplicationEvent(new BatchEvent(this, bo.getBatch(), bo.getAction()));
            countDownLatch.countDown();
        }

        try {
            countDownLatch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("countDown error:", e);
        }
    }
}
