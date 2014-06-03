package com.websocket.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.philips.transport.tcp.emis_server.json.Obj2Json;
import com.philips.transport.tcp.emis_server.utils.ECacheManager;

public class GetClients extends HttpServlet {
   private static final Logger logger=LoggerFactory.getLogger(GetClients.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -3685796780471430443L;
	private String encoding="UTF-8";
   @Override
	public void doGet(HttpServletRequest req,HttpServletResponse res)
   {
	   res.setCharacterEncoding(this.encoding);
	   PrintWriter out=null;
	   try {
		   out=res.getWriter();
		   Set<String> clients= ECacheManager.getNewInstance().getClients();
		   Map<String,String> mapClients=new HashMap<String,String>();
		   for(String ip: clients)
		   {
			  String car="救护车："+ ip.substring(ip.lastIndexOf('.')+1);
			  mapClients.put(ip, car);
		   }
		   out.println(Obj2Json.obj2Json(mapClients));
	   } catch (IOException e) {
		// TODO Auto-generated catch block
		logger.error("Error:",e);
	}
	   
   }
   @Override
   public void doPost(HttpServletRequest req,HttpServletResponse res)
   {
	   this.doGet(req, res);
   }
   @Override
   public void init(ServletConfig config)
   {
	   this.encoding=config.getInitParameter("encoding")!=null?config.getInitParameter("encoding"):this.encoding;
   }
}
