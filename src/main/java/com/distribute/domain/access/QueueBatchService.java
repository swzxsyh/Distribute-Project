package com.distribute.domain.access;

import com.distribute.domain.action.BatchParam;
import com.distribute.domain.action.BatchResultVo;
import com.distribute.domain.action.Outcome;
import com.distribute.persistence.mq.BatchParamProductor;
import com.distribute.persistence.util.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class QueueBatchService {

    @Autowired
    private Redisson redisson;

    @Autowired
    private BatchParamProductor productor;

    private RBlockingQueue<BatchResultVo> getSyncBlockQueue(String queueName) {
        return redisson.getBlockingQueue(queueName);
    }

    public <T> BatchResultVo asyncBatch(List<T> paramList, String beanName, int timeout) throws InterruptedException {
        String queueName = "batch:async:result:" + IdGenerator.genId("QR");
        paramList.forEach(e -> {
            BatchParam<T> batchParam = BatchParam.<T>builder()
                    .param(e)
                    .queueName(queueName)
                    .beanName(beanName)
                    .build();
            productor.send(batchParam);
        });
        RBlockingQueue<BatchResultVo> resultQueue = getSyncBlockQueue(queueName);
        for (int i = 0; i < timeout; ++i) {
            if (resultQueue.size() == paramList.size()) {
                break;
            }
            TimeUnit.SECONDS.sleep(1);
            resultQueue = getSyncBlockQueue(queueName);
        }
        BatchResultVo resultVo = new BatchResultVo();
        resultVo.setFailCount(0);
        resultVo.setSuccessCount(0);
        resultVo.setUnableOperate(0);
        resultVo.setFailList(new LinkedList<>());
        List<BatchResultVo> voList = resultQueue.readAll();
        log.info("方法名asyncBatch，voList：{}", voList.size());
        int timeoutSize = paramList.size() - voList.size();
        for (BatchResultVo poll : voList) {
            if (poll.getSuccessCount() > 0) {
                resultVo.setSuccessCount(poll.getSuccessCount() + resultVo.getSuccessCount());
            }
            if (poll.getFailCount() > 0) {
                resultVo.setFailCount(poll.getFailCount() + resultVo.getFailCount());
            }
            if (poll.getUnableOperate() > 0) {
                resultVo.setUnableOperate(poll.getUnableOperate() + resultVo.getUnableOperate());
            }
            if (!CollectionUtils.isEmpty(poll.getFailList())) {
                resultVo.getFailList().addAll(poll.getFailList());
            }
            if (!CollectionUtils.isEmpty(poll.getUnableOperateList())) {
                resultVo.getUnableOperateList().addAll(poll.getUnableOperateList());
            }
        }
        if (timeoutSize > 0) {
            resultVo.setFailCount(resultVo.getFailCount() + timeoutSize);
            for (int i = 0; i < timeoutSize; ++i) {
                Outcome outcome = Outcome.error("", "超时");
                resultVo.getFailList().add(outcome);
            }
        }
        resultQueue.delete();
        return resultVo;
    }
}
