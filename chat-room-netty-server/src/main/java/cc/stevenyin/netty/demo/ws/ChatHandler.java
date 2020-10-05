package cc.stevenyin.netty.demo.ws;

import java.time.LocalDateTime;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
/**
 * ������Ϣ��handler
 * TextWebSocketFrame:  ��netty��, ����websocketר�Ŵ����ı��Ķ���
 * @author Steven.Yin
 *
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

//	���ڼ�¼�͹������пͻ��˵�channels
	private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		// ��ȡ�ͻ��˴����������Ϣ
		String content = msg.text();
		System.out.println("���յ�������: " + content);
		for (Channel channel : clients) {
			channel.writeAndFlush(new TextWebSocketFrame("[Server Time] " + LocalDateTime.now() + ", content: " + content));
		}
		// ��������������forѭ��һ��
//		clients.writeAndFlush(new TextWebSocketFrame("[Server Time] " + LocalDateTime.now() + ", content: " + content));
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ChatHandler.handlerAdded()");
		// ���ͻ��˴����ӻ�ȡ�ͻ��˵�channel, ���ҷŵ�channel group��
		clients.add(ctx.channel());
		super.handlerAdded(ctx);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ChatHandler.handlerRemoved()");
		System.out.println("�ͻ��˶Ͽ�, ��Id: " + ctx.channel().id().asLongText() + ", ��Id: " + ctx.channel().id().asShortText());
		// ������handlerRemoved, ChannelGroup���Զ��Ƴ��ͻ��˶�Ӧ��channel
		clients.remove(ctx.channel());
		super.handlerRemoved(ctx);
	}
	
}
