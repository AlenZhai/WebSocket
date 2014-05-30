package com.philips.transport.tcp.emis_server.engine;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.philips.transport.tcp.emis_server.pack_type.DataPackFormat;
import com.philips.transport.tcp.emis_server.parse.DataParse;
import com.philips.transport.tcp.emis_server.utils.ECacheManager;

public class ServerThread extends Thread {
	final public  static Logger   logger=LoggerFactory.getLogger(ServerThread.class);
	final private static int	  HEAD=0xaa;		   /**数据包头 */
	final private static int	  END=0xcc;			   /**数据包尾 */
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	private Socket socket=null;						/**Client连接对象 */
	private int clientNo;							/**Client连接号*/
	private String clientIp="";						/**Client IP*/
	private Queue<DataPackFormat> clientQueue;		/**接收到Client的数据*/
	
	
	/**
	 * 
	 * @param socket
	 * @param clientNo
	 */
	public ServerThread(Socket socket,int clientNo)
	{
		
		this.socket=socket;
		this.clientNo=clientNo;
		this.clientIp=this.socket.getInetAddress().getHostAddress();
		this.clientQueue=ECacheManager.getNewInstance().getQueue(this.clientIp);
		logger.debug("client:"+clientNo+" client IP is:"+this.clientIp);
		this.start();
	}
	/**
	 * 关闭当前的Client连接
	 * @throws IOException
	 */
	public void close() throws IOException{
		if(this.socket!=null)
		{
			this.socket.close();
		}
	}
	/**
	 * 从Client读取数据
	 */
	@Override
	public void run()
	{
		logger.debug("run ...");
		try {
			//byte[] buff=new byte[BUFFER_SIZE];
			InputStream inStream = socket.getInputStream();
			List<Integer> temp=new LinkedList<Integer>();
			int data=0;
			while((data=inStream.read())!=-1)
			{
			  temp.add(data);
			  if(data==END)
			  {
				  Integer[] a=new Integer[temp.size()];				  
				  temp.toArray(a);
				  if(a[0]==HEAD)
				  {
					  parse(a);
				  }
				  //logger.debug("data:"+temp);
				  temp.clear();
			  }
			  //String hexStr =ServerThread.byteArrayToHex(buff);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(this.socket!=null)
			{
				try {
					this.socket.close();
					logger.debug("close and relace client:"+this.clientNo);
					ServerEngine.relace(clientNo+"");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public Socket getSocket() {
		return socket;
	}
	public int getClientNo() {
		return clientNo;
	}
	
	private void parse(Integer[] dataArray)
	{		 
			 int channel=dataArray[1].intValue();
			 switch(channel)
			 {
			 case 0x10:
				 this.clientQueue.offer(DataParse.parseEGC(dataArray));
				 break;
			 case 0x20:
				 this.clientQueue.offer(DataParse.parseNIBP(dataArray));
				 break;
			 case 0x30:
				 this.clientQueue.offer(DataParse.parseSPO2(dataArray));
				 break;
			 case 0x40:
				 this.clientQueue.offer(DataParse.parseRESP(dataArray));
				 break;
			 }
		 }
}
