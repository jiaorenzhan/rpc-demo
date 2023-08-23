package org.jrz.rpc.server.boot.nett;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;
import org.jrz.rpc.netty.codec.FrameDecoder;
import org.jrz.rpc.netty.codec.FrameEncoder;
import org.jrz.rpc.netty.codec.RpcRequestDecoder;
import org.jrz.rpc.netty.codec.RpcResponseEncoder;
import org.jrz.rpc.netty.handler.RpcRequestHandler;
import org.jrz.rpc.server.boot.RpcServer;
import org.jrz.rpc.server.config.RpcServerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NettyServer implements RpcServer {
    @Autowired
    private RpcServerConfiguration rpcServerConfiguration;

    @Override
    public void start() {
        EventLoopGroup boos = new NioEventLoopGroup(1, new DefaultThreadFactory("boss"));
        EventLoopGroup worker = new NioEventLoopGroup(0, new DefaultThreadFactory("worker"));
        EventExecutorGroup bussiness = new UnorderedThreadPoolEventExecutor(NettyRuntime.availableProcessors() * 2 + 1, new DefaultThreadFactory("bussiness"));

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boos, worker).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.TCP_NODELAY, true).childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline channelPipeline = ch.pipeline();
                            //编码
                            channelPipeline.addLast("frameEncoder", new FrameEncoder());
                            channelPipeline.addLast("", new RpcResponseEncoder());

                            //解码
                            channelPipeline.addLast("frameDecoder", new FrameDecoder());
                            channelPipeline.addLast("", new RpcRequestDecoder());

                            channelPipeline.addLast(bussiness, "rpcRequestHandler", new RpcRequestHandler());


                        }
                    });
            ChannelFuture sync = bootstrap.bind(rpcServerConfiguration.getRpcPort()).sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boos.shutdownGracefully();
            worker.shutdownGracefully();
            bussiness.shutdownGracefully();

        }

    }
}
