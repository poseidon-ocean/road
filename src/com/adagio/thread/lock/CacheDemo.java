package com.adagio.thread.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁在缓存中的使用
 *
 */
public class CacheDemo {

	private Map<String, Object> cache = new HashMap<>();
	
	public static void main(String[] args) {
		
	}
	
	private ReadWriteLock rwl = new ReentrantReadWriteLock();
	
	public Object getData(String key) {
		
		rwl.readLock().lock();
		
		Object value = null;
		
		try {
			value = cache.get(key);
			if(value == null) {
				rwl.readLock().unlock();
				
				rwl.writeLock().lock();
				if(value == null) {
					value = "1111";		//实际从DB中query
				}
				rwl.writeLock().unlock();
				
				rwl.readLock().lock();
			}
		} finally {
			rwl.readLock().unlock();
		}
		
		return value;
	}
	
}
