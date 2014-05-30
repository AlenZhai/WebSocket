package com.philips.transport.tcp.emis_server.pack_type;

public class ECG_SingleFormat extends DataPackFormat implements DataParser {
    
	protected float   	heartRate;		/**心率*/
	protected boolean 	HRCheckOut;		/**无心率检出*/
	protected boolean 	ep_loose;		/**电极脱落*/
	protected boolean 	RwaveChechOut;	/**R 波检出*/
	protected boolean 	vpc_occupy;		/**有VPC发生*/
	protected float 	vpc_num;		/**VPC名称编号*/
	protected int		gain;			/**增益*/
	protected int		tead;			/**导联*/
	public int getGain() {
		return gain;
	}
	public int getTead() {
		return tead;
	}
	public float getHeartRate() {
		return heartRate;
	}
	public boolean isHRCheckOut() {
		return HRCheckOut;
	}
	public boolean isEp_loose() {
		return ep_loose;
	}
	public boolean isRwaveChechOut() {
		return RwaveChechOut;
	}
	public boolean isVpc_occupy() {
		return vpc_occupy;
	}
	public float getVpc_num() {
		return vpc_num;
	}
	
	@Override
	public DataPackFormat parse(Integer[] data) {
		
		// TODO Auto-generated method stub
		//String[] rawData=data.split(split);		
		this.channel=data[1];		//解析xx0
		this.length=data[2];			//解析xx1
		this.heartRate=data[3];		//解析xx2
		int hr=data[5];				//解析xx4
		//int vpc_oc=vpc& 0x40;
		if((hr&this.bit7)!=0)
		{
			this.HRCheckOut=true;
		}
		if((hr&this.bit6)!=0)
		{
			this.ep_loose=true;
		}
		if((hr&this.bit5)!=0)
		{
			this.RwaveChechOut=true;
		}
		int vpc=data[8];				//解析xx7
		if((vpc&this.bit7)!=0)
		{
			this.vpc_occupy=true;
		}
		this.vpc_num=vpc&this.bit0_3;
		int gain_tead=data[9];		//解析xx8
		this.tead=gain_tead&this.bit0_3;
		this.gain=gain_tead&this.bit4_7;
		//System.out.println(rawData[5]);
		return this;
	}
	/*public static void main(String[] args)
	{
		Integer[] data={0xaa ,0x10 ,0x19 ,0x43 ,0x20 ,0x6b ,0x00 ,0x22 ,0x1c ,0x00 ,0x1e ,0x00 ,0x20 ,0x00 ,0x22 ,0x00 ,0x22 ,0x00 ,0x22 ,0x00 ,0x20 ,0x00 ,0x1e ,0x00 ,0x1c ,0x00 ,0x18 ,0x00 ,0xcc };
		ECG_SingleFormat ecg=	(ECG_SingleFormat) new ECG_SingleFormat().parse(data);
		System.out.println(ecg.getChannel());
		System.out.println(ecg.getLength());
		System.out.println(ecg.getHeartRate());
		System.out.println(ecg.isHRCheckOut());
	    System.out.println(ecg.isEp_loose());
		System.out.println(ecg.isRwaveChechOut());
	    System.out.println(ecg.isVpc_occupy());
		System.out.println(ecg.getVpc_num());
	    System.out.println(ecg.getTead());
		System.out.println(ecg.getGain());
	}*/

	
}
