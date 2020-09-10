package com.aihangxunxi.aitalk.im.codec;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ChannelHandler.Sharable
public class MessageEncoder extends MessageToMessageEncoder<MessageLiteOrBuilder> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MessageLiteOrBuilder msg, List<Object> out) throws Exception {
		ByteBuf result = null;
		if (msg instanceof MessageLite) {
			result = Unpooled.wrappedBuffer(((MessageLite) msg).toByteArray());
		}
		else {
			if (msg instanceof MessageLite.Builder) {
				result = Unpooled.wrappedBuffer(((MessageLite.Builder) msg).build().toByteArray());
			}

		}

		// todo ：根据客户端功能处理,确认是否需要转换成WebSocketFrame二进制流，
		WebSocketFrame frame = new BinaryWebSocketFrame(result);
		out.add(frame);
	}

}
