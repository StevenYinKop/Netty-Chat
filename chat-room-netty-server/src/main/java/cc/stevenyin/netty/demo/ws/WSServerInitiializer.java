package cc.stevenyin.netty.demo.ws;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * ����ͻ��˴���������Ϣ, ��������ٷ��ظ��ͻ���
 * @author Steven.Yin
 *
 */
public class WSServerInitiializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		ChannelPipeline pipeline = channel.pipeline();
		// websocket ����httpЭ��, ��Ҫ���http�������
		pipeline.addLast(new HttpServerCodec());
		// ��ӶԴ���������֧��
		pipeline.addLast(new ChunkedWriteHandler());
		// ��HttpMessage��������, ��ϳ�FullHttpRequest����FullHttpResponse
		// ��Netty�������� HttpObjectAggregator��ʹ���ʺܸ�
		pipeline.addLast(new HttpObjectAggregator(1024 * 64));
		
		// ===============================����������֧��httpЭ���Handler=================================
		
		/**
		 * websocket �����������Э��, ����ָ�����ͻ������ӷ��ʵ�·��������"/ws"
		 * ���Handler���ڴ���һЩ���ظ��ӵĲ���
		 * �������ֶ���: handshaking(close, ping, pong) ping + pong = ����
		 * ����websocket, ������frames���д����, ��ͬ�������Ͷ�Ӧ��framesҲ��ͬ
		 */
		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
		// �����Զ���handler
		pipeline.addLast(new ChatHandler());
	}
}
