package com.distribute.persistence.lock;

import java.util.function.Supplier;

public interface Locker {


    /**
     * 一直等待直到获取锁，无返回值
     * 默认使用的租期为30秒
     * @param name
     * @param command
     */
    void lock(String name, Runnable command);

    /**
     * 一直等待直到获取锁，有返回值-
     * 默认使用的租期为30秒
     * @param name
     * @param command
     * @return 返回值
     */
    <T> T lock(String name, Supplier<T> command);

    /**
     * 不等待，加锁成功执行command，失败什么也不做
     * @param name
     * @param command
     */
    void lockMutex(String name, Runnable command);

    /**
     * 不等待，加锁成功执行command，失败什么也不做
     * @param name
     * @param command
     * @return 返回值
     */
    <T> T lockMutex(String name, Supplier<T> command);

}
