package com.philips.transport.tcp.emis_server.engine;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ServerEngine {	
	final static private Logger logger=LoggerFactory.getLogger(ServerEngine.class);
	private static boolean stop=false;		/**	停止 */
	private static int serverPort = 8010;	/**	端口号 */
	private static ServerSocket server;     /**	服务实例*/
	private static Map<String,ServerThread> pool=new HashMap<String,ServerThread>(); /** 线程池 */
	
	public static boolean isStop() {
		return stop;
	}

	public static void setStop(boolean stop) {
		ServerEngine.stop = stop;
	}

	public static int getServerPort() {
		return serverPort;
	}

	public static void setServerPort(int serverPort) {
		ServerEngine.serverPort = serverPort;
	}

	public static ServerSocket getServer() {
		return server;
	}

	public static void setServer(ServerSocket server) {
		ServerEngine.server = server;
	}

	public static Map<String, ServerThread> getPool() {
		return pool;
	}

	public static void setPool(Map<String, ServerThread> pool) {
		ServerEngine.pool = pool;
	}

	public static Logger getLogger() {
		return logger;
	}
	
	
	
	public ServerEngine()
	{
		
	}
	/**
	 * 初始化服务器
	 */
	public static void init()
	{
		try {
			logger.info("server init ...");
			server= new ServerSocket(serverPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 开启Socket服务侦听Client
	 * @throws IOException
	 */
	public static void startup() throws IOException
	{
		logger.info("server startup ...");
		int clientNo=0;
		while(true)
		{
			if(!ServerEngine.stop)
			{
			 clientNo++;	
			 logger.debug("pool size:"+pool.size());
			 pool.put(clientNo+"", new ServerThread(server.accept(),clientNo));
			 //new ServerThread(server.accept(),clientNo);
			}else 
			{
				logger.debug("stop is true");
				break;
			}
		}		
		server.close();
	}
	/**
	 * 返回Client连接个数
	 * @return
	 */
	public static int poolSize()
	{
		return pool.size();
	}
	/**
	 * 释放指定Client连接
	 * @param key
	 * @throws IOException 
	 */
	public static void relace(String key) throws IOException
	{		
		pool.get(key).close();
		pool.remove(key);
		logger.debug("relace Client:"+key+"  pool size:"+pool.size());
	}
	/**
	 * 停止服务器
	 * @throws IOException
	 */
	public static void stop() throws IOException{
		logger.info("stop server  ...");
		stop=true;
		for(String index : pool.keySet())
		{
			pool.get(index).close();
		}
		pool.clear();
	}
}
