package com.distribute.application.impl;

import com.distribute.application.InputService;
import com.distribute.domain.BatchOddsService;
import com.distribute.domain.access.SyncBatchHelper;
import com.distribute.domain.action.BatchResultVo;
import com.distribute.domain.action.Outcome;
import com.distribute.facade.dto.BatchInputDto;
import com.distribute.persistence.executor.CustomizeThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 批量处理调用入口
 */
@Slf4j
@Service
public class InputServiceImpl implements InputService {

    @Autowired
    private BatchOddsService batchOddsService;

    @Autowired
    private SyncBatchHelper helper;

    // ThreadPool
    private static final int THREAD_NUM = Runtime.getRuntime().availableProcessors();
    ThreadPoolExecutor executor = new ThreadPoolExecutor(THREAD_NUM, THREAD_NUM * 2, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(THREAD_NUM * 5), new CustomizeThreadFactory("CUSTOMIZE-MULTI-"), new ThreadPoolExecutor.CallerRunsPolicy());


    @Override
    public BatchResultVo multi(BatchInputDto batchInputDto) throws InterruptedException {
        // validate something...

        if (CollectionUtils.isEmpty(batchInputDto.getList())) {
            return new BatchResultVo();
        }

        // 根据性能预估设置数量
        if (batchInputDto.getList().size() <= THREAD_NUM * 2) {
            //使用CompleteAbleFuture 将odds放入 batch方法，然后allOf获取结果
            List<BatchInputDto.Odds> oddsList = batchInputDto.getList();
            List<CompletableFuture<Outcome>> futures = batchInputDto.getList().stream().map(odds -> CompletableFuture.supplyAsync(() -> batchOddsService.batch(odds), executor)).collect(Collectors.toList());
            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

            List<Outcome> result = futures.stream().map(CompletableFuture::join).filter(Objects::nonNull).collect(Collectors.toList());

            long successCount = result.stream().filter(Outcome::isSuccess).count();
            List<Outcome> outcomeList = result.stream().filter(e -> !e.isSuccess()).collect(Collectors.toList());

            return BatchResultVo.builder().successCount((int) successCount).failCount(batchInputDto.getList().size() - (int) successCount).failList(outcomeList).build();
        }

        return helper.syncBatch(batchInputDto.getList(), "batchOdds", 60);
    }
}
