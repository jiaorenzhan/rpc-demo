package org.jrz.rpc.server.boot;

import org.jrz.rpc.server.registry.RpcRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class RpcServerRunner {

    @Autowired
    private RpcRegistry registry;

    @Autowired
    private RpcServer rpcServer;

    public void run(){
        /**
         * 1、服务信息的注册
         *   1.扫描业务代码接口注册到注册中心
         * 2、基于netty编写服务端
         *   1。编写一个服务端很快，注意是要分析需要提供那些handler
         *   入站：一次 二次解码器 ->请求处理器（根据参数调用接口方法）
         *   出站：二次 一次编码器
         */

        registry.serverRegistry();
        rpcServer.start();


    }
}
