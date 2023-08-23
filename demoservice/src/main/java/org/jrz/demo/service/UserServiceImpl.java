package org.jrz.demo.service;

import org.jrz.demo.service.UserService;
import org.jrz.rpc.annotation.JrpcService;

@JrpcService(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService{
    @Override
    public void sayHello() {

    }
}
