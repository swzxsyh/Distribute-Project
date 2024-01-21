package com.distribute.application;

import com.distribute.facade.dto.BatchInputDto;

/**
 * 入口
 */
public interface DistributeService {

    /**
     * 多任务执行
     *
     * @param batchInputDto 入参
     * @return 是否成功
     */
    Boolean multi(BatchInputDto batchInputDto) throws InterruptedException;
}
