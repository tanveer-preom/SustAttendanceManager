package com.example.sustattendancemanager;

public class ExportDatas {
	private String name, reg,total,percent;
	ExportDatas(String name,String reg,String total,String percent)
	{
		this.name=name;
		this.reg=reg;
		this.total=total;
		this.percent=percent;
	}
	public String getName()
	{
		return name;
	}
	public String getReg()
	{
		return reg;
	}
	
	public String getTotal()
	{
		return total;
	}
	public String getPercent()
	{
		return percent;
	}
	
}
