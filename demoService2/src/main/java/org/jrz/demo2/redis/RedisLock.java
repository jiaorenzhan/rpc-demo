package org.jrz.demo2.redis;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisLock {
    @Autowired
    private RedissonClient redissonClient;


    public boolean lock(String lockey) {
        RLock lock = redissonClient.getLock(lockey);
        lock.lock();

        return true;
    }

    public boolean unlock(String lockey) {
        RLock lock = redissonClient.getLock(lockey);
        lock.unlock();

        return true;
    }
}
