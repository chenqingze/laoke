package com.aihangxunxi.aitalk.im.config;

import com.aihangxunxi.aitalk.im.codec.MessageDecoder;
import com.aihangxunxi.aitalk.im.codec.MessageEncoder;
import com.aihangxunxi.aitalk.im.handler.AuthServerHandler;
import com.aihangxunxi.aitalk.im.handler.ExceptionHandler;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.constant.Constants;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class WebSocketChannelInitializer extends ChannelInitializer<NioSocketChannel> {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketChannelInitializer.class);

    private final SslContext sslCtx;

    // 用于异步处理非连接类的耗时业务逻辑
    private final EventExecutorGroup processorGroup;

    private static final int READ_IDEL_TIME_OUT = 120; // 读超时

    private static final int WRITE_IDEL_TIME_OUT = 0;// 写超时

    private static final int ALL_IDEL_TIME_OUT = 0; // 所有超时

    private static final String WEBSOCKET_PATH = "/ws";


    @Resource
    private MessageDecoder messageDecoder;

    @Resource
    private MessageEncoder messageEncoder;

    @Resource
    private ExceptionHandler exceptionHandler;

    @Resource
    private AuthServerHandler authServerHandler;

    public WebSocketChannelInitializer(@Nullable SslContext sslCtx, EventExecutorGroup processorGroup) {
        this.sslCtx = sslCtx;
        this.processorGroup = processorGroup;
    }

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (sslCtx != null) {
            pipeline.addLast(sslCtx.newHandler(ch.alloc()));
        }

        /* 通信超时处理 */
        pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(READ_IDEL_TIME_OUT));

        /* HTTP协议相关处理 */
        // http编解码器
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(Constants.MAX_AGGREGATED_CONTENT_LENGTH));// httpMessage进行聚合,
        // 聚合成FullHttpRequest或FullHttpResponse
        pipeline.addLast(new ChunkedWriteHandler());// 对写大数据流的支持

        /* Websocket协议相关处理 */
        pipeline.addLast(new WebSocketServerCompressionHandler());// 数据压缩
        pipeline.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true, Constants.MAX_FRAME_LENGTH));// handshaking(close,ping,pong)=握手处理，
        // 协议包解码
        pipeline.addLast(messageDecoder);
        // 协议包编码
//        pipeline.addLast(messageEncoder);

        /* google Protobuf 编解码 */
        // google Protobuf解码器
        // ProtobufVarint32FrameDecoder增加此解码器会报错
        // pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufDecoder(Message.getDefaultInstance()));
        // google Protobuf 编码器
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());

        /* 业务处理器 */
        // 认证处理
        pipeline.addLast("authServerHandler", authServerHandler);

        // // 心跳处理
        // pipeline.addLast("hearBeatServerHandler", hearBeatServerHandler);
        // pipeline.addLast(processorGroup, "msgDataServerHandler", msgDataServerHandler);
        // // pipeline.addLast(processorGroup, "createTeamReqServerHandler",
        // // createTeamReqServerHandler);
        // pipeline.addLast(processorGroup, "groupMsgServerHandler",
        // groupMsgServerHandler);
        // pipeline.addLast(processorGroup, "initGroupServerHandler",
        // initGroupServerHandler);
        // pipeline.addLast(processorGroup, "changeGroupNameHandler",
        // changeGroupNameHandler);
        // pipeline.addLast(processorGroup, "changeUserGroupNameHandler",
        // changeUserGroupNameHandler);
        // pipeline.addLast(processorGroup, "groupNoticeServerHandler",
        // groupNoticeServerHandler);
        // pipeline.addLast(processorGroup, "groupAllQuietHandler", groupAllQuietHandler);
        // pipeline.addLast(processorGroup, "createGroupHandler", createGroupHandler);
        // pipeline.addLast(processorGroup, "initialUserInGroupHandler",
        // initialUserInGroupHandler);
        // pipeline.addLast(processorGroup, "dismissGroupHandler", dismissGroupHandler);
        // pipeline.addLast(processorGroup, "detachGroupMemberHandler",
        // detachGroupMemberHandler);
        // pipeline.addLast(processorGroup, "quitGroupHandler", quitGroupHandler);
        // pipeline.addLast(processorGroup, "withdrawGroupMsgHandler",
        // withdrawGroupMsgHandler);
        // pipeline.addLast(processorGroup, "joinGroupHandler", joinGroupHandler);
        // pipeline.addLast(processorGroup, "accessToJoinGroupHandler",
        // accessToJoinGroupHandler);
        // pipeline.addLast(processorGroup, "storeConversationHandler",
        // storeConversationHandler);
        // pipeline.addLast(processorGroup, "invitationHandler", invitationHandler);
        // pipeline.addLast(processorGroup, "friendsHandler", friendsHandler);
        // pipeline.addLast(processorGroup, "privateChatHandler", privateChatHandler);
        // pipeline.addLast(processorGroup, "systemInformsHandler", systemInformsHandler);
        // pipeline.addLast(processorGroup, "storeWithdrawMsgHandler",
        // storeWithdrawMsgHandler);
        // pipeline.addLast(processorGroup, "disconnectServiceHandler",
        // disconnectServiceHandler);
        //
        pipeline.addLast(processorGroup, "exceptionHandler", exceptionHandler);
    }

}
