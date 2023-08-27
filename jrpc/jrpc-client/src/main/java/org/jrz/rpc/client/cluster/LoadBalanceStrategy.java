package org.jrz.rpc.client.cluster;

import org.jrz.rpc.provider.ServiceProvider;

import java.util.List;

/**
 * 负载均衡策略接口
 */
public interface LoadBalanceStrategy {

    /**
     * 根据对应的策略进行负载均衡
     * @param serviceProviders
     * @return
     */
    ServiceProvider select(List<ServiceProvider> serviceProviders);

}
