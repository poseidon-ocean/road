package com.adagio.io.nio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

	private int port = 8080;
	private InetSocketAddress address = null;
	
	private Selector selector;
	
	public NIOServer(int port) {
		try {
			
			this.port = port;
			address = new InetSocketAddress(this.port);
			
			ServerSocketChannel server = ServerSocketChannel.open();
			server.bind(address);
			
			//默认是阻塞，设置为非阻塞
			server.configureBlocking(false);
			
			selector = Selector.open();
			
			server.register(selector, SelectionKey.OP_ACCEPT);
			
			System.out.println("服务器准备就绪，监听端口--" + this.port);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listen() {
		try {
			
			while(true) {
				int wait = this.selector.select();
				if(wait == 0) {
					continue;
				}
				
				Set<SelectionKey> keys = this.selector.selectedKeys();
				Iterator<SelectionKey> i = keys.iterator();
				while(i.hasNext()) {
					SelectionKey key = i.next();
					process(key);
					i.remove();
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据不同的事件做相应的处理
	 * @param key
	 */
	private void process(SelectionKey key) {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		//接受请求
		if(key.isAcceptable()) {
			ServerSocketChannel server = (ServerSocketChannel)key.channel();
			try {
				SocketChannel client = server.accept();
				
				client.configureBlocking(false);
				client.register(selector, SelectionKey.OP_READ);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			//读信息
		} else if(key.isReadable()) {
			SocketChannel client = (SocketChannel)key.channel();
			
			try {
				int len = client.read(buffer);
				if(len > 0) {
					buffer.flip();
					String content = new String(buffer.array(), 0, len);
					System.out.println("read content--" + content);
					client.register(selector, SelectionKey.OP_WRITE);
				}
				
				buffer.clear();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			//写事件
		} else if(key.isWritable()) {
			SocketChannel client = (SocketChannel)key.channel();
			
			String name = (String)key.attachment();
			
			try {
				client.write(ByteBuffer.wrap(("hello " + name).getBytes()));
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		new NIOServer(8181).listen();
	}
}
