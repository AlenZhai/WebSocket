package com.websocket.handler;
import com.philips.transport.tcp.emis_server.pack_type.DataPackFormat;
import com.philips.transport.tcp.emis_server.utils.ECacheManager;
import com.websocket.servlet.SocketServlet;
import com.websocket.util.ConnectionsManager;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class DataHandle extends Thread {
	private final Logger logger = LoggerFactory.getLogger(DataHandle.class);
	private boolean stop = false;
	private Set<SocketServlet> connections;
	private ConcurrentMap<String,Queue<DataPackFormat>> queueMap;
	public DataHandle() {
		this.logger.debug("init dataHandle ...");	
		connections=ConnectionsManager.getNewInstance().getConnections();
		queueMap=ECacheManager.getNewInstance().getQueueMap();
	}

	public void run() {
		
		while (!this.stop) {	
			for(String key:queueMap.keySet())
			{
				Queue<DataPackFormat> queue=queueMap.get(key);
				DataPackFormat data = queue.poll();
				/*try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				if (data != null) {					
					for (SocketServlet client :connections ) {
						
						synchronized (client) {
							client.broadcast(data);
						}
					}

				}
			}
		}
		this.logger.debug("stop dataHandle data");
	}

	public boolean isStop() {
		return this.stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}
}
