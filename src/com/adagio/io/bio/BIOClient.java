package com.adagio.io.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class BIOClient {

	public static void main(String[] args) {
		int count = 100;
		
		final CountDownLatch latch = new CountDownLatch(count);
		
		for(int i=0;i<count;i++) {
			new Thread() {
				@Override
				public void run() {
					
					try {
						latch.await();
						
						//打开Socket通道
						Socket client = new Socket("localhost", 8181);
						
						//获取输出通道
						OutputStream os = client.getOutputStream();
						String name = UUID.randomUUID().toString() + "===";
						
						//发送给服务端
						os.write(name.getBytes());
						os.close();
						client.close();
						
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					
				}; 
			}.start();
			
			latch.countDown();
		}
		System.out.println(">>>>>>>>>>>>");
	}
}
