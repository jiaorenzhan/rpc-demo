package org.jrz.rpc.server.registry.zk;

import org.jrz.rpc.annotation.JrpcService;
import org.jrz.rpc.server.config.RpcServerConfiguration;
import org.jrz.rpc.server.registry.RpcRegistry;
import org.jrz.rpc.spring.SpringBeanFactory;
import org.jrz.rpc.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ZKRegistry implements RpcRegistry {

    @Autowired
    private ServerZkit serverZkit;

    @Autowired
    private RpcServerConfiguration rpcServerConfiguration;


    @Autowired
    private SpringBeanFactory beanFactory;

    /**
     * 服务注册
     */
    @Override
    public void serverRegistry() {

        Map<String, Object> beanListByAnnotationClass = SpringBeanFactory.getBeanListByAnnotationClass(JrpcService.class);
        if (beanListByAnnotationClass != null && !beanListByAnnotationClass.isEmpty()) {
            serverZkit.createRootNode();
            String ip = IpUtil.getRealIp();
            for (Object bean : beanListByAnnotationClass.values()) {
                JrpcService annotation = bean.getClass().getAnnotation(JrpcService.class);
                Class<?> aClass = annotation.interfaceClass();
                String serviceName = aClass.getName();
                serverZkit.createPersistentNode(serviceName);
                String subNode = serviceName + "/" + ip + ":" + rpcServerConfiguration.getRpcPort();
                serverZkit.createNode(subNode);
                System.out.println("服务注册成功：serviceName:"+serviceName+",childNode:"+subNode);
            }
        }

    }
}
