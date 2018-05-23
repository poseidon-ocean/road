package com.adagio.io.nio.buffer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferPaogram {

	public static void main(String[] args) {
		
		try {
			FileInputStream fin = new FileInputStream("d://info.txt");
			
			//创建文件操作管道
			FileChannel fc = fin.getChannel();
			
			//分配一个容量为10的缓存区，就是分配了一个10个大小的byte数组
			ByteBuffer buffer = ByteBuffer.allocate(10);
			output("初始化", buffer);
			
			//先读一下
			fc.read(buffer);
			output("调用read()", buffer);
			
			//操作之前，先锁定操作范围
			buffer.flip();
			output("调用flip()", buffer);
			
			//判断有没有可读数据
			while(buffer.remaining() > 0) {
				buffer.get();
			}
			output("调用get()", buffer);
			
			//清除缓存
	        buffer.clear();  
	        output("调用clear()", buffer);  
	  
	        //最后关闭管道
	        fin.close();  

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	//打印缓存区实时状态信息
	private static void output(String step, ByteBuffer buffer) {
		System.out.println(step + "--");
		//容量，数组大小
		System.out.println("capacity--" + buffer.capacity());
		//当前操作数据的位置，类似游标
		System.out.println("position--" + buffer.position());
		//锁定值，flip，数据操作范围索引只能咋position~limit之间
		System.out.println("limit--" + buffer.limit());
		
	}
}
