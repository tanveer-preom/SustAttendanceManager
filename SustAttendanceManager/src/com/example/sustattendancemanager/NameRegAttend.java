package com.example.sustattendancemanager;

class NameRegAttend {
	private String name,reg,attend;
	NameRegAttend(String n,String r,String a)
	{
		name=n;
		reg=r;
		attend=a;
	}
	public String getName()
	{
		return name;
	}
	public String getReg()
	{
		return reg;
		
	}
	public String getAttend()
	{
		return attend;
	}
	public void setAttendance(String passedAttend)
	{
		attend = passedAttend;
	}

}
