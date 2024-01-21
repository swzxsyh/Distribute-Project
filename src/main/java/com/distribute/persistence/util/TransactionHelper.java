package com.distribute.persistence.util;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

/**
 * 最小化事务
 */
@Component
public class TransactionHelper {

    @Transactional(rollbackFor = Exception.class)
    public void run(Runnable runnable) {
        runnable.run();
    }

    @Transactional(rollbackFor = Exception.class)
    public <T> T run(Supplier<T> supplier) {
        return supplier.get();
    }
}
