package com.xkcoding.zookeeper.lock;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.Locker;

/**
 * @Author:cyw
 * @CreateTime: 2021/3/10 10:46
 **/
@Slf4j
public class ZKDistLock implements DistLock {

    private String path;
    private CuratorFramework client;
    private Locker locker;
    private long lockTime;

    public ZKDistLock(String path, CuratorFramework client) {
        if (Strings.isNullOrEmpty(path)) {
            throw new RuntimeException("path required non-null");
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        this.path = path;
        this.client = client;
        log.info("path to lock:{}", path);
    }

    @Override
    public void lock() throws LockException {
        try {
            lockTime = System.currentTimeMillis();
            // 可重入排他锁
            InterProcessLock lock = new InterProcessMutex(client, path);
            locker = new Locker(lock);
            log.info("lock success:{}", path);
        } catch (Exception e) {
            throw new LockException(e);
        }
    }

    @Override
    public void unlock() throws LockException {
        if (locker != null) {
            try {
                locker.close();
                log.info("unlock success:{}, cost:{}ms", path, System.currentTimeMillis() - lockTime);
            } catch (Exception e) {
                throw new LockException(e);
            }
        }
    }
}





























