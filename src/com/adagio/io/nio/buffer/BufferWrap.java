package com.adagio.io.nio.buffer;

import java.nio.ByteBuffer;

/**
 * 接将一个现有的数组，包装为缓冲区对象
 *
 */
public class BufferWrap {

	public void myWrap() {
		// 分配指定大小的缓冲区
		ByteBuffer buffer1 = ByteBuffer.allocate(10);
		// 包装一个现有的数组
		byte array[] = new byte[10];
		ByteBuffer buffer2 = ByteBuffer.wrap( array );
		
		System.out.println(buffer1 + "==" + buffer2);
	}
}
