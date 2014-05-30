package com.philips.transport.tcp.emis_server.parse;

import com.philips.transport.tcp.emis_server.pack_type.DataPackFormat;
import com.philips.transport.tcp.emis_server.pack_type.DataParser;
import com.philips.transport.tcp.emis_server.pack_type.ECG_SingleFormat;
import com.philips.transport.tcp.emis_server.pack_type.NIBP_SingleFormat;
import com.philips.transport.tcp.emis_server.pack_type.RESP_SingleFormat;
import com.philips.transport.tcp.emis_server.pack_type.SPO2_SingleFormat;

/**
 * 
 * @author Alen
 * 这里解析所得数据
 */
public class DataParse {
	public static DataPackFormat parseEGC(Integer[] data)
	{
		//TODO
		return new ECG_SingleFormat().parse( data);		
	}
	public static DataPackFormat parseNIBP(Integer[] data)
	{
		return new NIBP_SingleFormat().parse( data);
		//TODO
	}
	public static DataPackFormat parseSPO2(Integer[] data)
	{
		//TODO
		return new SPO2_SingleFormat().parse( data);		
	}
	public static DataPackFormat parseRESP(Integer[] data)
	{
		return new RESP_SingleFormat().parse( data);
		
	}
	
}
