package org.jrz.rpc.client.boot;

import org.jrz.rpc.client.discovery.RpcServiceDiscovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RpcClientRunner {

    @Autowired
    private RpcServiceDiscovery rpcServiceDiscovery;

    /**
     * 1、服务发现 从zk拉取注册信息 并缓存
     * 2、扫描@jrpcremote 为接口生成代理并注入
     * 3、当代理方法执行时，走到代理拦截面 往后就是基于netty编写的客户端程序（连接服务端 发送请求）
     */
    public void run() {
        rpcServiceDiscovery.serviceDiscovery();

    }
}
