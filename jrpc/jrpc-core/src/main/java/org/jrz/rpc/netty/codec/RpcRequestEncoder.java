package org.jrz.rpc.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.jrz.rpc.data.RpcRequest;
import org.jrz.rpc.util.ProtostuffUtil;

import java.util.List;

public class RpcRequestEncoder extends MessageToMessageEncoder<RpcRequest> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcRequest request, List<Object> out) throws Exception {
        try {
            byte[] bytes = ProtostuffUtil.serialize(request);
            ByteBuf buf = ctx.alloc().buffer(bytes.length);
            buf.writeBytes(bytes);
            out.add(buf);
        } catch (Exception e) {
//            log.error("RpcResponseEncoder encode error,msg={}",e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
