package org.jrz.rpc.client.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RpcBootStrap {

    @Autowired
    private RpcClientRunner rpcClientRunner;

    @PostConstruct
    public void start() {
        new Thread(()->{
            rpcClientRunner.run();
        }).start();
    }


}
