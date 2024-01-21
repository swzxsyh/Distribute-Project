package com.distribute.persistence.lock.impl;

import com.distribute.persistence.lock.Locker;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@Component
public class RedisLockerImpl implements Locker {

	private final int timeout = 30;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 一直等待直到获取锁，无返回值
     * 默认使用的租期为30秒
     * @param name
     * @param command
     */
    @Override
    public void lock(String name, Runnable command) {
        RLock lock = redissonClient.getLock(name);
	    try {
		    if (lock.tryLock(timeout, TimeUnit.SECONDS)) {
			    log.debug("get lock, name={}", name);
			    try {
				    command.run();
			    } finally {
				    log.debug("release lock, name={}", name);
				    lock.unlock();
			    }
		    } else {
			    throw new RuntimeException("网络异常，请稍后再试");
		    }
	    } catch (InterruptedException e) {
		    throw new RuntimeException("服务器异常，请稍后再试");
	    }
    }

    /**
     * 一直等待直到获取锁，有返回值
     * 默认使用的租期为30秒
     * @param name
     * @param command
     */
    @Override
    public <T> T lock(String name, Supplier<T> command) {
        RLock lock = redissonClient.getLock(name);
	    try {
		    if (lock.tryLock(timeout, TimeUnit.SECONDS)) {
			    log.debug("get lock, name={}", name);
			    try {
				    return command.get();
			    } finally {
				    log.debug("release lock, name={}", name);
				    lock.unlock();
			    }
		    } else {
			    throw new RuntimeException("网络异常，请稍后再试");
		    }
	    } catch (InterruptedException e) {
		    throw new RuntimeException("服务器异常，请稍后再试");
	    }
    }

	@Override
	public void lockMutex(String name, Runnable command) {
		RLock lock = redissonClient.getLock(name);
		if (lock.tryLock()) {
			log.debug("get lock, name={}", name);
			try {
				command.run();
			} finally {
				log.debug("release lock, name={}", name);
				lock.unlock();
			}
		} else {
			throw new RuntimeException("网络异常，请稍后再试");
		}
	}

	@Override
	public <T> T lockMutex(String name, Supplier<T> command) {
		RLock lock = redissonClient.getLock(name);
		if (lock.tryLock()) {
			log.debug("get lock, name={}", name);
			try {
				return command.get();
			} finally {
				log.debug("release lock, name={}", name);
				lock.unlock();
			}
		} else {
			return null;
		}
	}
}
