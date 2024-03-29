package com.distribute.application;

import com.distribute.domain.action.BatchResultVo;
import com.distribute.facade.dto.BatchInputDto;

/**
 * 入口
 */
public interface InputService {

    /**
     * 多任务执行
     *
     * @param batchInputDto 入参
     * @return 是否成功
     */
    BatchResultVo multi(BatchInputDto batchInputDto) throws InterruptedException;
}
