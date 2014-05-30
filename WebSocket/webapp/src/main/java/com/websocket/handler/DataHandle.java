package com.websocket.handler;
import com.philips.transport.tcp.emis_server.pack_type.DataPackFormat;
import com.websocket.servlet.SocketServlet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class DataHandle extends Thread {
	private final Logger logger = LoggerFactory.getLogger(DataHandle.class);
	private boolean stop = false;
	private Set<SocketServlet> connections;
	Map<String, Queue<DataPackFormat>> queueMap;

	public DataHandle(Set<SocketServlet> connections,
			Map<String, Queue<DataPackFormat>> queueMap) {
		this.logger.debug("init dataHandle ...");
		this.connections = connections;

		this.queueMap = queueMap;
	}

	public void run() {
		DataPackFormat data;
		while (!this.stop) {
			for (String ip : this.queueMap.keySet()) {
				data = (DataPackFormat) ((Queue) this.queueMap.get(ip)).poll();
				if (data != null) {
					data.setClinetIp(ip);
					for (SocketServlet client : this.connections) {
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
