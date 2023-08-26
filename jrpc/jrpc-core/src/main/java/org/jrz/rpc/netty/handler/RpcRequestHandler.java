package org.jrz.rpc.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.jrz.rpc.data.RpcRequest;
import org.jrz.rpc.data.RpcResponse;
import org.jrz.rpc.spring.SpringBeanFactory;

import java.lang.reflect.Method;
import java.util.Map;

@ChannelHandler.Sharable
public class RpcRequestHandler extends SimpleChannelInboundHandler<RpcRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {

        //有请求一定要有响应
        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getRequestId());


        try {
            String serviceName = request.getClassName();
            String methodName = request.getMethodName();
            Class<?>[] parameterTypes = request.getParameterTypes();
            Object[] parameters = request.getParameters();
            Object bean = SpringBeanFactory.getBean(Class.forName(serviceName));
            if (bean != null) {
                Method method = bean.getClass().getMethod(methodName, parameterTypes);
                Object result = method.invoke(bean, parameters);
                response.setResult(result);
            } else {
                System.out.println("can not find bean :" + serviceName);
            }
        } catch (Exception e) {
            response.setCause(e);
            System.out.println("rpc server invoke error ");
        } finally {
            // 把响应写出去
            ctx.channel().writeAndFlush(response);
        }

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("服务端出现异常,异常信息为:" + cause.getCause());
        super.exceptionCaught(ctx, cause);
    }
}
