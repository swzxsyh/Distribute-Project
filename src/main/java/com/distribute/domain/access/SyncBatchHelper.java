package com.distribute.domain.access;

import com.distribute.domain.action.BatchResultVo;
import com.distribute.persistence.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * 批量处理调用入口
 */
@Slf4j
@Component
public class SyncBatchHelper {

    @Autowired
    private QueueBatchService mqBatchService;

    public <T> Result<BatchResultVo> syncBatch(List<T> batchList, String beanName, int timeout) throws InterruptedException {
        if (CollectionUtils.isEmpty(batchList)) {
            BatchResultVo vo = BatchResultVo.builder()
                    .failCount(0)
                    .successCount(0)
                    .unableOperate(0)
                    .failList(Collections.emptyList())
                    .unableOperateList(Collections.emptyList())
                    .build();
            return Result.success(vo);
        }
        BatchResultVo resultVo = mqBatchService.asyncBatch(batchList, beanName, timeout);
        log.info("方法名syncBatch:结果集resultVo：{}", resultVo);
        return Result.success(resultVo);
    }
}
