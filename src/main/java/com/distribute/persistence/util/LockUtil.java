package com.distribute.persistence.util;

import com.distribute.persistence.lock.Locker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class LockUtil {

    private static Locker locker;

    private static TransactionHelper transactionHelper;

    @Autowired
    public void setLocker(Locker locker) {
        LockUtil.locker = locker;
    }

    @Autowired
    public void setTransactionHelper(TransactionHelper transactionHelper) {
        LockUtil.transactionHelper = transactionHelper;
    }

    /**
     * 普通加锁，无返回值
     *
     * @param name
     * @param command
     */
    public static void lock(String name, Runnable command) {
        locker.lock(name, command);
    }

    /**
     * 普通加锁，无返回值,不等待，加锁成功执行command，失败什么也不做
     *
     * @param name
     * @param command
     */
    public static void lockMutex(String name, Runnable command) {
        locker.lockMutex(name, command);
    }

    /**
     * 普通加锁，有返回值
     *
     * @param name
     * @param command
     * @param <T>
     * @return
     */
    public static <T> T lock(String name, Supplier<T> command) {
        return locker.lock(name, command);
    }

    /**
     * 普通加锁，有返回值,不等待，加锁成功执行command，失败什么也不做
     *
     * @param name
     * @param command
     * @param <T>
     * @return
     */
    public static <T> T lockMutex(String name, Supplier<T> command) {
        return locker.lockMutex(name, command);
    }

    /**
     * 加锁，并在内部开启事务，无返回值
     *
     * @param name
     * @param command
     */
    public static void lockWithTx(String name, Runnable command) {
        locker.lock(name, () -> transactionHelper.run(command));
    }

    /**
     * 加锁，并在内部开启事务，无返回值,加锁成功执行command，失败什么也不做
     *
     * @param name
     * @param command
     */
    public static void lockMutexWithTx(String name, Runnable command) {
        locker.lockMutex(name, () -> transactionHelper.run(command));
    }

    /**
     * 加锁，并在内部开启事务，有返回值
     *
     * @param name
     * @param command
     * @param <T>
     * @return
     */
    public static <T> T lockWithTx(String name, Supplier<T> command) {
        return locker.lock(name, () -> transactionHelper.run(command));
    }

    /**
     * 加锁，并在内部开启事务，有返回值.加锁成功执行command，失败什么也不做
     *
     * @param name
     * @param command
     * @param <T>
     * @return
     */
    public static <T> T lockMutexWithTx(String name, Supplier<T> command) {
        return locker.lockMutex(name, () -> transactionHelper.run(command));
    }

    public static String generateLockName(String... seps) {
        StringBuilder sb = new StringBuilder();
        for (String sep : seps) {
            sb = sb.append(sep).append("_");
        }
        return sb.substring(0, sb.length() - 1);
    }
}
