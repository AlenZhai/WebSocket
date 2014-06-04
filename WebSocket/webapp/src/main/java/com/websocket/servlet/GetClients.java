package com.websocket.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
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
	private Map clients=new HashMap();
   @Override
	public void doGet(HttpServletRequest req,HttpServletResponse res)
   {
	   res.setCharacterEncoding(this.encoding);
	   res.addHeader("Access-Control-Allow-Origin", "*");
	   PrintWriter out=null;
	   try {
		   out=res.getWriter();
		   Set<String> clients= ECacheManager.getNewInstance().getClients();
		   Map<String,String> mapClients=new HashMap<String,String>();
		   for(String ip: clients)
		   {		
			   if(this.clients.containsKey(ip))
			   {
				  mapClients.put(ip, this.clients.get(ip).toString());  
			   }
			 
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
	 
	 String path=  config.getInitParameter("clients");
	  
	   
	 if(path!=null)
	 {
		 path= GetClients.class.getResource(path).getPath();
		 File f=new File(path);
		 if(f.exists()&&f.isFile())
		 {
			 try {
				FileInputStream input=new FileInputStream (f);
				Properties pro=new Properties();
				try {
					pro.load(input);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for(Entry e: pro.entrySet())
				{
					clients.put(e.getKey(), e.getValue());
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }else{
			 logger.error("错误:没有找到 clients 配置文件:"+f.getPath());
		 }		 
	 }else{
		 logger.error("错误:请指定 clients 配置文件路径！");
	 }
	   this.encoding=config.getInitParameter("encoding")!=null?config.getInitParameter("encoding"):this.encoding;
   }
}
