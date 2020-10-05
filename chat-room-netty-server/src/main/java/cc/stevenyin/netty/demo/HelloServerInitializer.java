package cc.stevenyin.netty.demo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 初始化器, channel注册后, 会执行相应的初始化方法
 * @author Steven.Yin
 *
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		// 通过Channel获取对应的pipeline
		ChannelPipeline channelPipeline = channel.pipeline();
		// 通过pipeline管道添加Handler, 可以理解为拦截器
		// 当请求到服务端, 我们需要做解码; 响应到客户端需要做编码
		channelPipeline.addLast("HttpServerCodec", new HttpServerCodec());
		channelPipeline.addLast("HelloNettyHandler", new HelloNettyHandler());
	}

}
