package cc.stevenyin.netty.demo.ws;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WSServer {
	
	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup main = new NioEventLoopGroup();
		EventLoopGroup sub = new NioEventLoopGroup();
		
		try {
			ServerBootstrap server = new ServerBootstrap();
			server.group(main, sub)
				.channel(NioServerSocketChannel.class)
				.childHandler(new WSServerInitiializer());
			ChannelFuture channelFuture = server.bind(9999).sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			main.shutdownGracefully();
			sub.shutdownGracefully();
		}
		
	}

}
