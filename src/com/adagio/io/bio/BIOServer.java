package com.adagio.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer {

	private ServerSocket server;
	
	public BIOServer(int port) {
		try {
			server = new ServerSocket(port);
			
			System.out.println("BIO服务已启动，监听端口--" + port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listener() {
		while(true) {
			try {
				//阻塞，等待客户端连接
				Socket client = server.accept();
				
				//拿到输入流
				InputStream is = client.getInputStream();
				
				byte[] buff = new byte[1024];
				int len = is.read(buff);
				
				if(len > 0) {
					String msg = new String(buff, 0, len);
					System.out.println("收到--" + msg);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static void main(String[] args) {
		new BIOServer(8181).listener();
	}
}
