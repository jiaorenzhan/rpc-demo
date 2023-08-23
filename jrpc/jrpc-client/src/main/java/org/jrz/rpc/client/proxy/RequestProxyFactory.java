package org.jrz.rpc.client.proxy;


import net.sf.cglib.proxy.Enhancer;

import org.jrz.rpc.proxy.ProxyFactory;
import org.springframework.stereotype.Component;


@Component
public class RequestProxyFactory implements ProxyFactory {

    /**
     * 创建新的代理实例-CGLib动态代理
     * @param cls
     * @param <T>
     * @return
     */
    public  <T> T newProxyInstance(Class<T> cls) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cls);
        enhancer.setCallback(new CglibProxyCallBackHandler());
        return (T) enhancer.create();
    }
}
