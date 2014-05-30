package com.websocket.init;

import java.io.IOException;
import java.util.Queue;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.philips.transport.tcp.emis_server.engine.ServerEngine;
import com.philips.transport.tcp.emis_server.pack_type.DataPackFormat;
import com.philips.transport.tcp.emis_server.utils.ECacheManager;

public class SystemInit implements ServletContextListener {
	private static final Logger logger = LoggerFactory
			.getLogger(SystemInit.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		try {
			ServerEngine.stop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		ServerEngine.init();
		new SocketServer().start();
		Queue<DataPackFormat> queue = ECacheManager.getNewInstance().getQueue("127.0.0.1");

		queue.offer(new DataPackFormat());
		queue.offer(new DataPackFormat());
		queue.offer(new DataPackFormat());
		queue.offer(new DataPackFormat());
	}

	private static class SocketServer extends Thread {
		public void run() {
			try {
				ServerEngine.startup();
			} catch (IOException e) {
				SystemInit.logger.error("error:" + e.getMessage(), e);
			}
		}
	}
}
