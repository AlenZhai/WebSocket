package com.websocket.util;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import com.websocket.servlet.SocketServlet;

public class ConnectionsManager {
	private static ConnectionsManager instance;
	private   AtomicInteger connectionIds = new AtomicInteger(0);
	private   Set<SocketServlet> connections = new CopyOnWriteArraySet();
    private ConnectionsManager()
    {
    	
    }
    public static ConnectionsManager getNewInstance(){
    	if(instance==null)
    	{
    		instance=new ConnectionsManager();
    		return instance;
    	}
    	return instance;
    }
    public int add(SocketServlet connection){
    	connections.add(connection);
    	return connectionIds.getAndIncrement();
    }
    public void remove(SocketServlet connection){
    	connections.remove(connection);
    }
	public Set<SocketServlet> getConnections() {
		return connections;
	}
    
}
