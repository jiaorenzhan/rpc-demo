package org.jrz.rpc.client.cluster.lb;

import org.jrz.rpc.annotation.JrpcLoadBalance;
import org.jrz.rpc.client.cluster.LoadBalanceStrategy;
import org.jrz.rpc.provider.ServiceProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 负载均衡策略：轮询
 */
@JrpcLoadBalance(strategy = "polling")
public class PollingLoadBalanceStrategy implements LoadBalanceStrategy {

    private int index = 0;

    private ReentrantLock reentrantLock = new ReentrantLock();


    @Override
    public ServiceProvider select(List<ServiceProvider> serviceProviders) {

        try {
            if (reentrantLock.tryLock(10, TimeUnit.MICROSECONDS)) {
                if (index > serviceProviders.size()) {
                    index = 0;
                }
                ServiceProvider serviceProvider = serviceProviders.get(index);
                index++;
                return serviceProvider;
            }

        } catch (Exception e) {
            System.out.println("轮询策略获取锁失败：" + e);

        } finally {
            reentrantLock.unlock();
        }

        return null;
    }
}
