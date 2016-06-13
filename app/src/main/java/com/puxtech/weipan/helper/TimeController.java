package com.puxtech.weipan.helper;


import com.puxtech.weipan.data.entitydata.EnvTradeTime;
import com.puxtech.weipan.data.entitydata.TimePointEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public  class TimeController {
	/**
	 * 底部时间
	 */
	private String[] BOTTOM_TEXT;
	private static final int DLS = 1;
	private static final int XLS = 2;
	/**
	 * 底部时间相对于06:00的分钟数
	 */
	private int[] BOTTOM_TEXT_IN_MINUTES;
	private float TOTAL_MINUTES;
	private String startTime;
	private int dayLine;
	
	public String[] getBOTTOM_TEXT() {
		return BOTTOM_TEXT;
	}
	public void setBOTTOM_TEXT(String[] bOTTOM_TEXT) {
		BOTTOM_TEXT = bOTTOM_TEXT;
	}
	public int[] getBOTTOM_TEXT_IN_MINUTES() {
		return BOTTOM_TEXT_IN_MINUTES;
	}
	public void setBOTTOM_TEXT_IN_MINUTES(int[] bOTTOM_TEXT_IN_MINUTES) {
		BOTTOM_TEXT_IN_MINUTES = bOTTOM_TEXT_IN_MINUTES;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public void setTOTAL_MINUTES(float tOTAL_MINUTES) {
		TOTAL_MINUTES = tOTAL_MINUTES;
	}
	public void setDayLine(int dayLine) {
		this.dayLine = dayLine;
	}
	public TimeController(int LS_FLAG) {
		switch (LS_FLAG) {
		case DLS:
			BOTTOM_TEXT = new String[] { "8:00", "10:00", "14:00", "18:00",
					"22:00", "2:00", "4:00", "8:00" };
			this.startTime= "0800";
			this.dayLine=800;
			break;
		case XLS:
			BOTTOM_TEXT = new String[] { "6:00", "8:00", "12:00", "16:00",
					"20:00", "00:00", "04:00", "6:00" };
			this.startTime= "0600";
			this.dayLine=600;
			break;
		default:
			break;
		}
		BOTTOM_TEXT_IN_MINUTES = new int[] { 0, 120, 360, 600, 840, 1080,
		1320, 1440 };
		TOTAL_MINUTES = 1440f;// 总分钟数
	}
	public float getTOTAL_MINUTES() {
		return TOTAL_MINUTES;
	}
	EnvTradeTime ett;
	
	public EnvTradeTime getEtt() {
		return ett;
	}
	public TimeController(EnvTradeTime ett){
		long startTime;
		if(ett==null){
			Calendar cal= Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
			cal.set(Calendar.HOUR, 6);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			startTime =cal.getTime().getTime();
		}else{
			startTime = ett.getStartTime();
			this.ett=ett;
		}
//		long ll=1417747800000l;
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
		Calendar cal= Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		cal.setTimeInMillis(startTime);
		int hour=cal.get(Calendar.HOUR);
		int set_m=cal.get(Calendar.MINUTE)>=30?30:0;
		cal.set(Calendar.MINUTE, set_m);
		ArrayList<String> al=new ArrayList<String>();
		ArrayList<Integer> alDiff=new ArrayList<Integer>();
		
		al.add(sdf.format(cal.getTime()));
		alDiff.add(0);
		
		Calendar cal1= Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		cal1.setTime(cal.getTime());
		cal1.add(Calendar.MINUTE, -cal.get(Calendar.MINUTE));
		for (int i = 0; i <6; i++) {
			if(i==0){
				cal1.add(Calendar.HOUR, 4-hour%4);
			}
			else {
				cal1.add(Calendar.HOUR, 4);
			}
			al.add(sdf.format(cal1.getTime()));
			alDiff.add(getDiff(cal, cal1));
		}
		
		if(cal1.get(Calendar.HOUR)!=cal.get(Calendar.HOUR)||cal1.get(Calendar.MINUTE)!=cal.get(Calendar.MINUTE)){
			cal1.add(Calendar.HOUR, cal.get(Calendar.HOUR)-cal1.get(Calendar.HOUR));
			cal1.add(Calendar.MINUTE, cal.get(Calendar.MINUTE));
			al.add(sdf.format(cal1.getTime()));
			alDiff.add(getDiff(cal, cal1));
		}else{
			System.out.println("done");
		}
		BOTTOM_TEXT=new String[al.size()];
		for (int i = 0; i < BOTTOM_TEXT.length; i++) {
			BOTTOM_TEXT[i]=al.get(i);
		}
		BOTTOM_TEXT_IN_MINUTES=new int[alDiff.size()];
		for (int i = 0; i < BOTTOM_TEXT_IN_MINUTES.length; i++) {
			BOTTOM_TEXT_IN_MINUTES[i]=alDiff.get(i);
		}
	
		TOTAL_MINUTES = 1440f;// 总分钟数
		initField();
	}
	private void initField(){
		StringBuilder sb=new StringBuilder(BOTTOM_TEXT[0]);
		sb.deleteCharAt(sb.indexOf(":"));
		this.startTime=  sb.toString();
		this.dayLine= Integer.valueOf(sb.toString());
	}
	private int getDiff(Calendar cal, Calendar cal1) {
		long diff=cal1.getTimeInMillis()-cal.getTimeInMillis();
		int a=(int) (diff/60000);
		return a;
	}
	/**
	 * 根据时令获得当天 的开市时间
	 * 
	 * @return
	 */
	public String getStartTime(TimePointEntity entity) {
		
		return  entity.getTradeDate()+this.startTime;
	}
	public long getStartTimeLong(){
		return this.ett.getStartTime();
	}
	/**
	 * 返回一天的开始时间8：00 6：00
	 * 
	 * @return
	 */
	public int getDayLine() {
		return this.dayLine;
	}
}