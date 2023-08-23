package org.jrz.rpc.client.discovery.zk;

import org.jrz.rpc.cache.ServiceProviderCache;
import org.jrz.rpc.client.discovery.RpcServiceDiscovery;
import org.jrz.rpc.provider.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ZkServiceDiscovery implements RpcServiceDiscovery {


    @Autowired
    private Zkclient zkclient;

    @Autowired
    private ServiceProviderCache providerCache;

    @Override
    public void serviceDiscovery() {
        // 拉取所有的接口列表
        List<String> serviceList = zkclient.getServiceList();
        if (serviceList != null && !serviceList.isEmpty()) {
            for (String serviceName : serviceList) {
                // 获取该接口服务下的所有提供者信息
                List<ServiceProvider> serviceInfos = zkclient.getServiceInfos(serviceName);
                //存入缓存
                providerCache.put(serviceName, serviceInfos);

                //4、监听该服务下是所有节点信息变化
                zkclient.subscribeZKEvent(serviceName);

                System.out.println("从zk加载的服务信息为,serviceName=" + serviceName + ",serviceInfos=" + serviceInfos.toString());
            }
        }
    }
}
