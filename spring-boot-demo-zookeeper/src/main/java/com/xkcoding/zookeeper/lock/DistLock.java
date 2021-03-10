package com.xkcoding.zookeeper.lock;

/**
 * 分布式锁接口
 * @author cyw
 */
public interface DistLock {

    void lock() throws LockException;

    void unlock() throws LockException;
}
