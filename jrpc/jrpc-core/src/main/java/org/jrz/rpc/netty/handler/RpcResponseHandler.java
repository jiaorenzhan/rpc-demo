package org.jrz.rpc.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.jrz.rpc.data.RpcResponse;
import org.jrz.rpc.request.RequestPromise;
import org.jrz.rpc.request.RpcRequestHolder;

public class RpcResponseHandler extends SimpleChannelInboundHandler<RpcResponse> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {

        RequestPromise requestPromise = RpcRequestHolder.getRequestPromise(rpcResponse.getRequestId());
        if (requestPromise != null) {
            requestPromise.setSuccess(rpcResponse);
        }
    }
}
