package com.philips.transport.tcp.emis_server.pack_type;

public class SPO2_SingleFormat extends DataPackFormat implements DataParser {
    protected	float	pulseRate;		/**脉率*/
    protected	float	spo2;			/**血氧饱和度*/
    protected	boolean	checking;		/**正在检出*/
    protected	boolean	ep_loose;		/**电极脱落*/
    protected	boolean	Rw_CheckOut;	/**R波检出*/
    protected	boolean	prprior;		/**脉率优先*/
    protected	boolean	hroprior;		/**心率优先*/
    protected	int		tatcolum;		/**肪柱值*/
	public int getTatcolum() {
		return tatcolum;
	}
	public float getPulseRate() {
		return pulseRate;
	}
	public float getSpo2() {
		return spo2;
	}
	public boolean isChecking() {
		return checking;
	}
	public boolean isEp_loose() {
		return ep_loose;
	}
	public boolean isRw_CheckOut() {
		return Rw_CheckOut;
	}
	public boolean isPrprior() {
		return prprior;
	}
	public boolean isHroprior() {
		return hroprior;
	}
	
	@Override
	public DataPackFormat parse(Integer[] data) {
		// TODO Auto-generated method stub
		
		this.channel=data[1];		
		this.length=data[2];			//解析xx0
		this.pulseRate=data[3];		//解析xx1
		this.spo2=data[4];			//解析xx2
		int other=data[5];			//解析xx3
		if((other&this.bit7)!=0)
		{
			this.checking=true;
		}
		if((other&this.bit6)!=0)
		{
			this.ep_loose=true;
		}
		if((other&this.bit5)!=0)
		{
			this.Rw_CheckOut=true;
		}
		if((other&this.bit4)!=0)
		{
			this.prprior=true;
		}else
		{
			this.hroprior=true;
		}
		this.tatcolum=other&this.bit0_3;
		return this;
	}
	/*public static void main(String[] args)
	{
		Integer[] data={0xaa, 0x30, 0x05, 0x41, 0x63, 0x01, 0x00 ,0x0b ,0xcc};
		SPO2_SingleFormat spo2=(SPO2_SingleFormat) new SPO2_SingleFormat().parse(data);
		System.out.println(spo2.getChannel());
		System.out.println(spo2.getLength());
		System.out.println(spo2.getPulseRate());
		System.out.println(spo2.getSpo2());
		
		System.out.println(spo2.isChecking());
		System.out.println(spo2.isEp_loose());
		System.out.println(spo2.isRw_CheckOut());
		System.out.println(spo2.isPrprior());
		System.out.println(spo2.isHroprior());
		System.out.println(spo2.getTatcolum());
	}*/
}
