package com.websocket.servlet;

import com.philips.transport.tcp.emis_server.json.Obj2Json;
import com.philips.transport.tcp.emis_server.pack_type.DataPackFormat;
import com.philips.transport.tcp.emis_server.utils.ECacheManager;
import com.websocket.util.ConnectionsManager;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint("/websocket/data")
public class SocketServlet {
	private static final Logger logger = LoggerFactory
			.getLogger(SocketServlet.class);
	private Session session;
	private int clientNo;
	private Set<String> room = new CopyOnWriteArraySet<String>();	
	public SocketServlet() {	 
	}

	@OnOpen
	public void start(Session session) {
		this.session = session;
		this.clientNo = ConnectionsManager.getNewInstance().add(this);

		logger.debug("client :" + this.clientNo + "start get data");
	}

	@OnClose
	public void end() {
		//logger.debug("client size:" + connections.size());
		ConnectionsManager.getNewInstance().remove(this);
		logger.debug("client :" + this.clientNo + "stop get data");
	}

	@OnError
	public void onError(Throwable t) throws Throwable {
		logger.error("Error: " + t.toString(), t);
	}

	@OnMessage
	public void incoming(String message) {
		
		Set<String> keys = ECacheManager.getNewInstance().getClients();
		logger.debug("Conns:"+ConnectionsManager.getNewInstance().getConnections());
		logger.debug("clients:"+keys);
		//logger.debug("queue:"+ECacheManager.getQueueMap());
		if (keys.contains(message)) {
			try {
				this.session.getBasicRemote().sendText(message + ":is added");
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.room.add(message);
		}
	}

	public void broadcast(DataPackFormat message) {
		if (this.room.contains(message.getClinetIp()))
			try {
				this.session.getBasicRemote().sendText(
						Obj2Json.obj2Json(message));
			} catch (JsonGenerationException e) {
				logger.debug("Error: json convert error ", e);
			} catch (JsonMappingException e) {
				logger.debug("Error: json convert error", e);
			} catch (IOException e) {
				ConnectionsManager.getNewInstance().remove(this);
				logger.debug("Error: ", e);
			}
	}	
}
