package cc.stevenyin.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HelloServer {

	public static void main(String[] args) {
//		1. 构建一对主从线程组
		// 定义主线程组, 用于接收客户端的连接
		EventLoopGroup bossGrp = new NioEventLoopGroup();
		// 定义从线程组, bossGrp会将连接分发给worker线程组来执行
		EventLoopGroup workerGrp = new NioEventLoopGroup();
//		2. 定义服务器启动类
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap
			// 把主从线程组放入serverBootstrap中
//		4. 设置处理从线程池的助手类初始化器
			.group(bossGrp, workerGrp)
//		3. 为服务器设置Channel
			// 定义channel类型是Nio的Channel类型(NioServerSocketChannel)
			.channel(NioServerSocketChannel.class)
			// 针对从线程组进行一些操作
			.childHandler(new HelloServerInitializer());
		ChannelFuture channelFuture;
		try {
			// 监听启动服务器, 并且设置为9998端口, 启动方式为同步, bind操作没有完成前, 线程会阻塞
//		5. 监听启动和关闭服务器
			channelFuture = serverBootstrap.bind(9998).sync();
			// 监听关闭服务器, 方式也为同步
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGrp.shutdownGracefully();
			workerGrp.shutdownGracefully();
		}
	}
}
