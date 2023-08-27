package org.jrz.rpc.client.cluster;

import org.jrz.rpc.annotation.JrpcLoadBalance;
import org.jrz.rpc.client.conf.RpcClientConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 负载均衡策略实现类
 */

@Component
public class DefaultStrategyProvider implements StartegyProvider, ApplicationContextAware {

    @Autowired
    private RpcClientConfiguration rpcClientConfiguration;

    private LoadBalanceStrategy loadBalanceStrategy = null;

    @Override
    public LoadBalanceStrategy getStrategy() {
        if (loadBalanceStrategy != null) {
            return loadBalanceStrategy;
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beansOfType = applicationContext.getBeansWithAnnotation(JrpcLoadBalance.class);
        if (beansOfType != null) {
            for (Object bean : beansOfType.values()) {
                JrpcLoadBalance declaredAnnotation = bean.getClass().getDeclaredAnnotation(JrpcLoadBalance.class);
                if (declaredAnnotation != null) {
                    String strategy = declaredAnnotation.strategy();
                    if (strategy.equals(rpcClientConfiguration.getRpcClientClusterStrategy())) {
                        loadBalanceStrategy = (LoadBalanceStrategy) bean;
                        return;
                    }
                }
            }
        }
    }
}
