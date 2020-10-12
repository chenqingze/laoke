package com.aihangxunxi.aitalk.im.bootstrap;

import com.aihangxunxi.aitalk.im.channel.ChannelConstant;
import com.aihangxunxi.aitalk.im.codec.MessageDecoder;
import com.aihangxunxi.aitalk.im.codec.MessageEncoder;
import com.aihangxunxi.aitalk.im.handler.*;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.stream.ChunkedWriteHandler;
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

    @Resource
    private QueryUserGroupsHandler queryUserGroupsHandler;

    @Resource
    private InvitationRequestHandler invitationRequestHandler;

    @Resource
    private InvitationAcceptHandler invitationAcceptHandler;

    @Resource

    private AskFroJoinGroupHandler askFroJoinGroupHandler;

    @Resource
    private InvitationUserJoinGroupHandler invitationUserJoinGroupHandler;

    @Resource
    private InvitationDeclinedHandler invitationDeclinedHandler;

    @Resource
    private GroupMessageHandler groupMessageHandler;

    @Resource
    private CreateMucHandler createMucHandler;

    @Resource
    private RemoveGroupMemberHandler removeGroupMemberHandler;

	@Resource
	private P2P2ChatHandler p2P2ChatHandler;

    @Resource
    private EditGroupNameHandler editGroupNameHandler;

    @Resource
    private EditGroupNoticeHandler editGroupNoticeHandler;

    @Resource
    private ChangeMucMuteHandler changeMucMuteHandler;

    @Resource
    private ChangeMucJoinConfirmHandler changeMucJoinConfirmHandler;

    @Resource
    private ExitGroupHandler exitGroupHandler;

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
        // pipeline.addLast("readTimeoutHandler", new
        // ReadTimeoutHandler(READ_IDEL_TIME_OUT));

        /* HTTP协议相关处理 */
        // http编解码器
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(ChannelConstant.MAX_AGGREGATED_CONTENT_LENGTH));// httpMessage进行聚合,
        // 聚合成FullHttpRequest或FullHttpResponse
        pipeline.addLast(new ChunkedWriteHandler());// 对写大数据流的支持

        /* Websocket协议相关处理 */
        pipeline.addLast(new WebSocketServerCompressionHandler());// 数据压缩
        pipeline.addLast(
                new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true, ChannelConstant.MAX_FRAME_LENGTH));
        // handshaking(close,ping,pong)=握手处理，
        // 协议包解码
        pipeline.addLast(messageDecoder);
        // 协议包编码
        pipeline.addLast(messageEncoder);

        /* google Protobuf 编解码 */
        // google Protobuf解码器
        // ProtobufVarint32FrameDecoder增加此解码器会报错
        // pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufDecoder(Message.getDefaultInstance()));
        // google Protobuf 编码器
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        // ProtobufEncoder增加此解码器会报错
        // pipeline.addLast(new ProtobufEncoder());

        /* 业务处理器 */
        // 认证处理
        pipeline.addLast("authServerHandler", authServerHandler);
        pipeline.addLast("queryUserGroupsHandler", queryUserGroupsHandler);
        pipeline.addLast("invitationRequestHandler", invitationRequestHandler);
        pipeline.addLast("invitationAcceptHandler", invitationAcceptHandler);
        pipeline.addLast("invitationDeclinedHandler", invitationDeclinedHandler);
        pipeline.addLast("groupMessageHandler", groupMessageHandler);
        pipeline.addLast("invitationUserJoinGroupHandler", invitationUserJoinGroupHandler);
        pipeline.addLast("askFroJoinGroupHandler", askFroJoinGroupHandler);
        pipeline.addLast("createMucHandler", createMucHandler);
        pipeline.addLast("removeGroupMemberHandler", removeGroupMemberHandler);
        pipeline.addLast("editGroupNameHandler", editGroupNameHandler);
        pipeline.addLast("editGroupNoticeHandler", editGroupNoticeHandler);
        pipeline.addLast("changeMucMuteHandler", changeMucMuteHandler);
        pipeline.addLast("changeMucJoinConfirmHandler", changeMucJoinConfirmHandler);
        pipeline.addLast("exitGroupHandler", exitGroupHandler);

        pipeline.addLast("p2pChatHandler", p2P2ChatHandler);

        // todo：其他业务处理器放到这里
        // pipeline.addLast(processorGroup, "queryUserGroupsHandler",
        // queryUserGroupsHandler);
        pipeline.addLast(processorGroup, "exceptionHandler", exceptionHandler);
    }


}
