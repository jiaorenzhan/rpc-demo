package org.jrz.rpc.client.proxy;

import net.sf.cglib.core.ReflectUtils;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.jrz.rpc.client.request.RpcRequestManger;
import org.jrz.rpc.data.RpcRequest;
import org.jrz.rpc.data.RpcResponse;
import org.jrz.rpc.exception.RpcException;
import org.jrz.rpc.spring.SpringBeanFactory;
import org.jrz.rpc.util.RequestIdUtil;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;


public class CglibProxyCallBackHandler implements MethodInterceptor {


    public Object intercept(Object o, Method method, Object[] parameters, MethodProxy methodProxy) throws Throwable {
        //发起rpc调用

        //放过 object tostring方法
        if (ReflectionUtils.isObjectMethod(method)) {
            return method.invoke(method.getDeclaringClass().newInstance(), parameters);
        }
        //构建请求
        RpcRequest request = new RpcRequest();
        String requestId = RequestIdUtil.requestId();
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        request.setRequestId(requestId);
        request.setClassName(className);
        request.setMethodName(methodName);
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(parameters);

        //发送请求 获取响应
        RpcRequestManger rpcRequestManger = SpringBeanFactory.getBean(RpcRequestManger.class);
        if (rpcRequestManger == null) {
            throw new RpcException("spring ioc exception");
        }
        RpcResponse rpcResponse = rpcRequestManger.sendRequest(request);

        return rpcResponse.getResult();
    }
}
