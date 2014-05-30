package com.philips.transport.tcp.emis_server.pack_type;

public class NIBP_SingleFormat extends DataPackFormat implements DataParser {
    protected 	float	pulseRate;		/**脉率*/
    protected 	float	sgstolicPrse;	/**收缩压*/
    protected 	float	diastolicPrse;  /**舒张压*/
    protected 	float	weanPrse;		/**平均压*/
    protected 	float	todyTemp;		/**体温*/
    protected	float	todyTemp2;		/**体温2*/
    protected 	float	respRate;		/**呼吸率*/
    protected	boolean adult;			/**成人 true表示成人 false 表示婴儿*/
    protected	int		version;	
   
    @Deprecated
    public float getTodyTemp2() {
		return todyTemp2;
	}
    @Deprecated
	public int getVersion() {
		return version;
	}
	/**xx9*/
    
	@Override
	public DataPackFormat parse(Integer[] data) {
		// TODO Auto-generated method stub
			
		this.channel=data[1];		
		this.length=data[2];			//解析xx0
		this.pulseRate=data[3];		//解析xx1
		this.sgstolicPrse=data[4];	//解析xx2
		this.diastolicPrse=data[5];	//解析xx3
		this.weanPrse=data[6];		//解析xx4
		int tempH=data[7];			//解析xx5
		int tempL=data[8];			//解析xx6
		this.todyTemp=Integer.valueOf(					
				Integer.toBinaryString(tempH)+Integer.toBinaryString(tempL),
				2);
		this.respRate=data[9];		//解析xx7
		this.version=data[11];		//解析xx9
		int other=data[12];			//解析xx10
		if((other&this.bit7)!=0)
		{
			this.adult=true;
		}
		int tempH2=data[13];			//解析xx11
		int tempL2=data[14];			//解析xx12
		this.todyTemp2=Integer.valueOf(						
				Integer.toBinaryString(tempH2)+Integer.toBinaryString(tempL2),
				2);
		return this;
	}
	public float getPulseRate() {
		return pulseRate;
	}
	public float getSgstolicPrse() {
		return sgstolicPrse;
	}
	public float getDiastolicPrse() {
		return diastolicPrse;
	}
	public float getWeanPrse() {
		return weanPrse;
	}
	public float getTodyTemp() {
		return todyTemp;
	}
	public float getRespRate() {
		return respRate;
	}
	public boolean isAdult() {
		return adult;
	}
	/*public static void main(String[] args)
	{
		Integer[] data={0xaa ,0x20 ,0x0c ,0x00 ,0x00 ,0x00 ,0x43 ,0x00 ,0x00 ,0x15 ,0x00 ,0x00 ,0x80 ,0x00 ,0x00 ,0xcc};
		NIBP_SingleFormat nibp= (NIBP_SingleFormat) new NIBP_SingleFormat().parse(data);
		System.out.println(nibp.getChannel());
		System.out.println(nibp.getLength());
		System.out.println(nibp.getPulseRate());
		System.out.println(nibp.getSgstolicPrse());
		System.out.println(nibp.getDiastolicPrse());
		System.out.println(nibp.getWeanPrse());
		
		System.out.println(nibp.getTodyTemp());
		System.out.println(nibp.getRespRate());
		System.out.println(nibp.isAdult());
		System.out.println(nibp.getTodyTemp2());
		System.out.println(nibp.getVersion());
	}*/
}
