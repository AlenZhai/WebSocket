package com.philips.transport.tcp.emis_server.utils;


import java.util.LinkedList;
import java.util.Queue;





import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.philips.transport.tcp.emis_server.pack_type.DataPackFormat;


/**
 * 1.管理缓存
 * 
 * @author alenzhai 2013-05-16
 * 
 */
public class ECacheManager {
	private static final Logger logger = LoggerFactory.getLogger(ECacheManager.class);
	private ConcurrentMap<String,Queue<DataPackFormat>> queueMap=new ConcurrentHashMap<String,Queue<DataPackFormat>>();
	//private Queue<DataPackFormat> queue=new LinkedList<DataPackFormat>();
	private Set<String> clients=new CopyOnWriteArraySet<String>();
	private static  ECacheManager cacheMgr;
	private ECacheManager()
	{
		logger.debug("init cache .....");
	}
	/**
	 * 获取缓存管理对象
	 * @return
	 */
	public  static ECacheManager getNewInstance()
	{
		if(cacheMgr==null)
		{
			cacheMgr=new ECacheManager();
		}
		return cacheMgr;
	}
	/**
	 * 获取队列
	 * @return
	 */
	public  Queue<DataPackFormat> getQueue(String key)
	{
		if(queueMap.containsKey(key))
		{
			return queueMap.get(key);
		}
		Queue<DataPackFormat> q=new LinkedList<DataPackFormat>();
		queueMap.put(key, q);
		return q;		
	}
	public ConcurrentMap<String,Queue<DataPackFormat>> getQueueMap()
	{
		return queueMap;
	}
	/**
	 * 从队列头取出一个元素并移除该元素
	 * @return
	 */
	public synchronized  DataPackFormat outQueue(String key)
	{
		
	return queueMap.get(key).poll();
		
	}
	/**
	 * 在队列的尾加入一个元素
	 * @param json
	 */
	public synchronized void inQueue(String key,DataPackFormat json)
	{
		queueMap.get(key).offer(json);		
	}
	/**
	 * 从队列头取出一个元素但不移除该元素
	 * @return
	 */
	public  DataPackFormat peek(String key)
	{
		return queueMap.get(key).peek();
	}
	public Set<String> getClients() {
		return clients;
	}
	public void addClient(String ip)
	{
		clients.add(ip);
	}
	public void removeClient(String ip)
	{
		clients.remove(ip);
	}

}
