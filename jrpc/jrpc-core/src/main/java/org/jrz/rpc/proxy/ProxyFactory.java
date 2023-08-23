package org.jrz.rpc.proxy;


public interface ProxyFactory {

    public  <T> T newProxyInstance(Class<T> cls) ;
}
