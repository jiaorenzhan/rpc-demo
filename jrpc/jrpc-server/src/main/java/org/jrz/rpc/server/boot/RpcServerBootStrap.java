package org.jrz.rpc.server.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Configuration
public class RpcServerBootStrap {

    @Autowired
    private RpcServerRunner rpcServerRunner;

    @PostConstruct
    public void startServer() {

        rpcServerRunner.run();


    }


}
