package org.jrz.rpc.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.jrz.rpc.data.RpcRequest;
import org.jrz.rpc.data.RpcResponse;
import org.jrz.rpc.spring.SpringBeanFactory;

import java.lang.reflect.Method;

public class RpcRequestHandler extends SimpleChannelInboundHandler<RpcRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {

        //有请求一定要有响应
        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getRequestId());


        try {
            String serviceName = request.getClassName();// com.itheima.order.OrderService
            String methodName = request.getMethodName();
            Class<?>[] parameterTypes = request.getParameterTypes();
            Object[] parameters = request.getParameters();
            Object bean = SpringBeanFactory.getBean(Class.forName(serviceName));
            Method method = bean.getClass().getMethod(methodName, parameterTypes);
            Object result = method.invoke(bean, parameters);
            response.setResult(result);
        } catch (Exception e) {
           System.out.println("rpc server invoke error ");
        }finally {
            // 把响应写出去
            ctx.channel().writeAndFlush(response);
        }

    }
}
