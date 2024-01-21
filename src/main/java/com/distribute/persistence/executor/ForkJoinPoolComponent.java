package com.distribute.persistence.executor;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

@Component
public class ForkJoinPoolComponent {

    private ForkJoinPool pool;


    @PostConstruct
    private void init() {
        final int parallelism = 3;
        pool = new ForkJoinPool(parallelism, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, Boolean.TRUE);
    }

    public <T> Future<T> submit(Callable<T> task) {
        return pool.submit(task);
    }

    public <T> Future<T> submit(Runnable task, T result) {
        return pool.submit(task, result);
    }

    public void execute(Runnable runnable) {
        pool.execute(runnable);
    }
}
