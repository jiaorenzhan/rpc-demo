package org.jrz.rpc.client.spring;

import org.jrz.rpc.annotation.JrpcRemote;
import org.jrz.rpc.proxy.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class RpcAnnotationProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ProxyFactory proxyFactory;

    /**
     * bean 初始化之后 执行依赖注入
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        try {
            Field[] declaredFields = bean.getClass().getDeclaredFields();
            for (Field filed : declaredFields) {
                if (!filed.isAccessible()) {
                    filed.setAccessible(true);
                }
                //检查field 上是否有 @JrpcRemote 注解
                JrpcRemote annotation = filed.getAnnotation(JrpcRemote.class);
                if (annotation != null) {
                    Class<?> fieldType = filed.getType();
                    Object proxy = proxyFactory.newProxyInstance(fieldType);
                    if (proxy != null) {
                        System.out.println(filed.getName() + "生成代理" + proxy);
                        filed.set(bean, proxy);

                    }
                }
            }
        } catch (Exception e) {
            //todo error log
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        proxyFactory = applicationContext.getBean(ProxyFactory.class);

    }
}
