package cc.stevenyin.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.stevenyin.netty.demo.ws.WSServerInitiializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WebSocketServer {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
	
	private EventLoopGroup main;
	private EventLoopGroup sub;
	private ServerBootstrap serverBootstrap;
	private ChannelFuture future;
	
	public void start() {
		int port = 8088;
		this.future = serverBootstrap.bind(port);
		logger.info("websocket started at : {} successfully!", port);
	}
	
	private WebSocketServer() {
		main = new NioEventLoopGroup();
		sub = new NioEventLoopGroup();
		serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(main, sub)
			.channel(NioServerSocketChannel.class)
			.childHandler(new WSServerInitiializer());
	}
	
	private static class SingletonServer {
		static WebSocketServer INSTANCE = new WebSocketServer();
	}
	public static WebSocketServer getInstance() {
		return SingletonServer.INSTANCE;
	}
	
	
}
