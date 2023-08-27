package org.jrz.rpc.client.cluster;


/**
 * 负载均衡策略提供者
 */
public interface StartegyProvider {

    LoadBalanceStrategy getStrategy();
}
