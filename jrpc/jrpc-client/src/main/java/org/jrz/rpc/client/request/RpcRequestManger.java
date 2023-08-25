package org.jrz.rpc.client.request;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.Promise;
import org.apache.commons.collections.CollectionUtils;
import org.jrz.rpc.cache.ServiceProviderCache;
import org.jrz.rpc.data.RpcRequest;
import org.jrz.rpc.data.RpcResponse;
import org.jrz.rpc.enums.StatusEnum;
import org.jrz.rpc.exception.RpcException;
import org.jrz.rpc.netty.codec.*;
import org.jrz.rpc.netty.handler.RpcResponseHandler;
import org.jrz.rpc.provider.ServiceProvider;
import org.jrz.rpc.request.RequestPromise;
import org.jrz.rpc.request.RpcRequestHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RpcRequestManger {


    @Autowired
    private ServiceProviderCache serviceProviderCache;

    public RpcResponse sendRequest(RpcRequest request) {

        List<ServiceProvider> serviceProviders = serviceProviderCache.get(request.getClassName());
        if (CollectionUtils.isEmpty(serviceProviders)) {
            System.out.println("客户端没有发现可以服务端节点");
            throw new RpcException(StatusEnum.NOT_FOUND_SERVICE_PROVINDER);
        }
        //找到 rpc调用
        //todo 负载均衡
        ServiceProvider serviceProvider = serviceProviders.get(0);
        return requestByNetty(request, serviceProvider);

    }

    private RpcResponse requestByNetty(RpcRequest request, ServiceProvider serviceProvider) {
        //netty client
        EventLoopGroup group = new NioEventLoopGroup(0, new DefaultThreadFactory("client"));
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline channelPipeline = ch.pipeline();
                //编码
                channelPipeline.addLast("frameEncoder", new FrameEncoder());
                channelPipeline.addLast("requestEncoder", new RpcRequestEncoder());

                //解码
                channelPipeline.addLast("frameDecoder", new FrameDecoder());
                channelPipeline.addLast("responseDecoder", new RpcResponseDecoder());
                channelPipeline.addLast("responseHandler", new RpcResponseHandler());
            }
        });
        try {
            ChannelFuture future = bootstrap.connect(serviceProvider.getServerIp(), serviceProvider.getRpcPort()).sync();
            if (future.isSuccess()) {
                Channel channel = future.channel();
                RequestPromise requestPromise = new RequestPromise(channel.eventLoop());
                channel.writeAndFlush(request);
                RpcRequestHolder.addRequestPromise(request.getRequestId(),requestPromise);

                RpcResponse response = (RpcResponse) requestPromise.get();
                return response;
            }
        } catch (Exception e) {
            System.out.println("connect server error:");
            System.out.println(e);
        }finally {
            RpcRequestHolder.removeRequestPromise(request.getRequestId());
        }

        return new RpcResponse();

    }
}
