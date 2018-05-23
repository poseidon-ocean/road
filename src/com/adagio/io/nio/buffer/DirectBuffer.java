package com.adagio.io.nio.buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class DirectBuffer {

	public static void main(String[] args) throws Exception {
		
		String infile = "d:\\info.txt";
		FileInputStream fin = new FileInputStream( infile );
		
		FileChannel fcin = fin.getChannel();
		String outfile = String.format("d:\\testcopy.txt");
		FileOutputStream fout = new FileOutputStream( outfile );
		FileChannel fcout = fout.getChannel();
		// 使用 allocateDirect，而不是 allocate
		// 直接缓存 - 缓存的hb是空的
		ByteBuffer buffer = ByteBuffer.allocateDirect( 1024 );
		
		while (true) {
			buffer.clear();
			int r = fcin.read( buffer );
			if (r==-1) {
				break;
			}
			buffer.flip();
			fcout.write( buffer );
		}
		
		fcin.close();
		fin.close();
		fcout.close();
		fout.close();
	}
}
