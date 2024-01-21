package com.distribute.persistence.executor;

import com.distribute.persistence.util.ThreadMdcUtil;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * Thread Pool
 *
 * @author swzxsyh
 */
@Component
public class ThreadPoolComponent {

    private ThreadPoolExecutor threadPoolExecutor;

    // runtime
    private final int THREAD_NUM = Runtime.getRuntime().availableProcessors();

    @PostConstruct
    public void init() {
        threadPoolExecutor = new ThreadPoolExecutor(THREAD_NUM, THREAD_NUM * 2 + 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(THREAD_NUM * 200), new CustomizeThreadFactory("CUSTOMIZE-POOL-"), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public <T> Future<T> submit(Callable<T> task) {
        Callable<T> wrap = ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap());
        return threadPoolExecutor.submit(wrap);
    }

    public <T> Future<T> submit(Runnable task, T result) {
        Runnable wrap = ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap());
        return threadPoolExecutor.submit(wrap, result);
    }

    public void execute(Runnable command) {
        Runnable wrap = ThreadMdcUtil.wrap(command, MDC.getCopyOfContextMap());
        threadPoolExecutor.execute(wrap);
    }
}
