package com.philips.transport.tcp.emis_server.pack_type;

public class RESP_SingleFormat extends DataPackFormat implements DataParser {
    protected	float	respRate;		/**呼吸率*/
    protected	boolean	ep_loose;		/**电极脱落*/
    protected	int	resp_gain;			/**呼吸增益*/
    protected	int	resp_tead;			/**呼吸导联*/
	public float getRespRate() {
		return respRate;
	}
	public boolean isEp_loose() {
		return ep_loose;
	}
	
	public int getResp_gain() {
		return resp_gain;
	}
	public int getResp_tead() {
		return resp_tead;
	}
	@Override
	public DataPackFormat parse(Integer[] data) {
		// TODO Auto-generated method stub
			
		this.channel=data[1];		
		this.length=data[2];			//解析xx0
		this.respRate=data[3];		//解析xx1
		int other=data[4];			//解析xx2
		if((other&this.bit7)!=0)
		{
			this.ep_loose=true;
		}
		this.resp_gain=other&this.bit4_6;
		this.resp_tead=other&this.bit0_3;
		return this;
	}
	/*public static void main(String[] args)
	{
		Integer[] data={0xaa, 0x40, 0x0c, 0x15, 0x21, 0x05, 0x03, 0x03, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xcc};
		RESP_SingleFormat resp=(RESP_SingleFormat) new RESP_SingleFormat().parse(data);
		System.out.println(resp.getChannel());
		System.out.println(resp.getLength());
		System.out.println(resp.getRespRate());
		System.out.println(resp.getResp_gain());
		System.out.println(resp.getResp_tead());
		System.out.println(resp.isEp_loose());
	}*/

}
