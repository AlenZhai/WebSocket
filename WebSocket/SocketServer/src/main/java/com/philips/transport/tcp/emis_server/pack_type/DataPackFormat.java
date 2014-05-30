package com.philips.transport.tcp.emis_server.pack_type;

public class DataPackFormat {
	protected   int       	bit7=0x40;		/**取b7*/
	protected   int			bit6=0x20;		/**取b6*/
	protected	int 		bit5=0x10;		/**取b5*/
	protected   int         bit4=0x08;		/**取b4*/
	protected   int			bit0_3=0x07;	/**取b0-b3*/
	protected   int			bit4_7=0x78;	/**取b4-b7*/
	protected   int			bit4_6=0x38;	/**取b4-b6*/
	
	protected 	String		clinetIp;		/**仪器IP*/
	protected 	int 		channel;		/** 数据包类型*/
	protected 	int 		length;   		/**数据字节个数*/
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getClinetIp() {
		return clinetIp;
	}
	public void setClinetIp(String clinetIp) {
		this.clinetIp = clinetIp;
	}

}
