package com.philips.transport.tcp.emis_server.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;





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
	private static Map<String,Queue<DataPackFormat>> queueMap=new HashMap<String,Queue<DataPackFormat>>();
	public static Map<String, Queue<DataPackFormat>> getQueueMap() {
		return queueMap;
	}
	private static ECacheManager cacheMgr;
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
		}else if(!queueMap.containsKey(key))
		{
			Queue<DataPackFormat> q=new LinkedList<DataPackFormat>();
			queueMap.put(key, q);
			return q;
		}
		return null;
		
	}
	/**
	 * 从队列头取出一个元素并移除该元素
	 * @return
	 */
	public synchronized  DataPackFormat outQueue(String key)
	{
		if(queueMap!=null&&queueMap.containsKey(key))
		{
			return queueMap.get(key).poll();
		}
		return null;
	}
	/**
	 * 在队列的尾加入一个元素
	 * @param json
	 */
	public synchronized void inQueue(String key,DataPackFormat json)
	{
		if(queueMap!=null&&queueMap.containsKey(key))
		{
			queueMap.get(key).offer(json);
		}else if(queueMap==null)
		{
			queueMap=new HashMap<String,Queue<DataPackFormat>>();
			Queue<DataPackFormat> q=new LinkedList<DataPackFormat>();
			q.offer(json);
			queueMap.put(key, q);
		}else if(!queueMap.containsKey(key)){
			Queue<DataPackFormat> q=new LinkedList<DataPackFormat>();
			q.offer(json);
			queueMap.put(key, q);
		}
		
	}
	/**
	 * 从队列头取出一个元素但不移除该元素
	 * @return
	 */
	public  DataPackFormat peek(String key)
	{
		return queueMap.get(key).peek();
	}

}
