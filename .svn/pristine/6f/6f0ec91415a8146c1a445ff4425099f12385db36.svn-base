package com.puxtech.weipan.util;


import com.puxtech.weipan.data.entitydata.TimePointEntity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * <b>Description:</b>
 * <p>
 * 时间工具类
 * </p>
 * 
 * @Company 北京乐赢索思软件科技有限责任公司
 * @author fanshuo
 * @date 2013-12-30 上午11:53:10
 * @version V1.0
 * @FileName TimeUtil.java
 * @Package com.happyinsource.htjy.android.util
 */
public class TimeUtil {

	/**
	 * yyyyMMddHHmm转成时间戳
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public static long getTimeByTimePoint(TimePointEntity entity) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
		int time = entity.getTime();
		String timeStr = time+"";
		//补全四位数字
		//00:01~00:09
		if(time < 10){
			timeStr = "000"+timeStr;
		}
		//00:10～00:59
		else if(time < 100){
			timeStr = "00"+timeStr;
		}
		//01:00~09:59
		else if(time < 1000){
			timeStr = "0"+timeStr;
		}
		Date date = dateFormat.parse(entity.getTradeDate()+ "" + timeStr);
		return date.getTime();
	}
	
	/**
	 * yyyyMMddHHmm转成时间戳
	 * @param s
	 * @return
	 */
	public static long getTimeByString(String s) throws Exception {
		if(s.length() <=8){
			s += "0000";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		Date date = dateFormat.parse(s);
		return date.getTime();
	}
	
	/**
	 * 获取今天的交易开始时间(06：00)，时间戳类型，毫秒级
	 * @return
	 */
	public static long getCurrentTradeStartTime(){
		long currentTime = System.currentTimeMillis();
		currentTime = currentTime/1000 * 1000;//忽略毫秒
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		int hours = cal.get(Calendar.HOUR_OF_DAY);//现在的小时数
		int deltaMinutes = cal.get(Calendar.MINUTE);//现在的分钟数
		int deltaSeconds = cal.get(Calendar.SECOND);//现在的秒钟数
		int deltaHours = 0;//现在距离本交易日的06：00有多少小时
		if(hours>= 0 && hours < 6){
			deltaHours = hours + 18;//如果在00：00～06：00之间，就加上昨天的18个小时（06：00～24：00）
		}
		else{
			deltaHours = hours-6;//如果在06：00～24：00之间，直接减去6就是了
		}
		deltaSeconds = deltaSeconds + deltaMinutes*60 + deltaHours*3600;
		long startTime = currentTime - deltaSeconds*1000;
		return startTime;
	}
	
	/**
	 * 把奇怪的时间格式转换为易读的String。201402261314 -> 2014-02-26 13:14
	 * @param time long型的字符串。。。。如：201402261314，类型为long型，服务器传回来就这样的
	 * @return 返回格式为 2014-02-26 13:14
	 */
	public static String convertStangeTimeToString(long time){
		String timeStr = time+"";
		if(timeStr.length() ==8){
			timeStr += "0000";
		}
		String retString = "";
		retString = timeStr.substring(0, 4) + "-" + timeStr.substring(4, 6) + "-" + timeStr.substring(6, 8) + " " + timeStr.substring(8, 10) + ":" + timeStr.substring(10, 12);
		return retString;
	}
	
	/**
	 * 格式化分时数据中的时间。例如：823 格式化为 08:23
	 * @param time
	 * @return
	 */
	public static String convertTimeLinePointTime(long time){
		String timeStr = time+"";
		//补全四位数字
		//00:01~00:09
		if(time < 10){
			timeStr = "000"+timeStr;
		}
		//00:10～00:59
		else if(time < 100){
			timeStr = "00"+timeStr;
		}
		//01:00~09:59
		else if(time < 1000){
			timeStr = "0"+timeStr;
		}
		String retStr = timeStr.substring(0, timeStr.length()-2) + ":" + timeStr.substring(timeStr.length()-2);
		return retStr;
	}

}
