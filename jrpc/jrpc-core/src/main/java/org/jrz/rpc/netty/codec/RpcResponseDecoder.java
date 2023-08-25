package org.jrz.rpc.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.jrz.rpc.data.RpcResponse;
import org.jrz.rpc.util.ProtostuffUtil;

import java.util.List;

public class RpcResponseDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        try {
            byte[] bytes = new byte[msg.readableBytes()];
            msg.readBytes(bytes);
            RpcResponse response = ProtostuffUtil.deserialize(bytes, RpcResponse.class);
            out.add(response);
        } catch (Exception e) {
            //
            throw new RuntimeException(e);
        }


    }
}
