package com.puxtech.weipan.util;

import android.content.Context;


import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.data.entitydata.PriceCommodityEntity;
import com.puxtech.weipan.data.entitydata.TimePointEntity;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * <b>Description:</b>
 * <p>
 * 价格转化和计算工具类，如小数位数等等
 * </p>
 * 
 * @Company 北京乐赢索思软件科技有限责任公司
 * @author fanshuo
 * @date 2014-1-9 下午3:58:00
 * @version V1.0
 * @FileName StringUtil.java
 * @Package com.happyinsource.htjy.android.util
 */
public class PriceUtil {

	/**
	 * 使用java正则表达式去掉多余的.与0
	 */
	public static String formatIndex(double price) {
		return keepPrecision(price, 2);
	}
	
	/**
	 * 格式化百分比，保留两位小数，前面带加减号
	 * @param percentage
	 * @return
	 */
	public static String formatPercentage(double percentage){
		String s = keepPrecision(percentage, 2);
		if(percentage == 0){
			s = s + "%";
		}
		else if(percentage > 0){
			s = "+" + s + "%";
		}
		else{
			s = s + "%";
		}
		return s;
	}

	/**
	 * 根据最小变动价位格式化价格
	 * 
	 * @return
	 */
	public static String formatPrice (Context context, double price,
			int commodityNumber) {
		MyApplication myapp = (MyApplication) context.getApplicationContext();
		PriceCommodityEntity entity = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getCommodityByNumber(
				commodityNumber);
	    return keepPrecision(price, entity.getSpread()) + "";
	}

//    public static String formatPrice (Context context, double price,
//                                      int commodityNumber, int jysCode, int envCode) {
//        MyApplication myapp = (MyApplication) context.getApplicationContext();
//        PriceCommodityEntity entity = myapp.getCommodityInfoEntityByCommodityNumber(jysCode,envCode,commodityNumber);
//
//
//		return keepPrecision(price, entity.getSpread()) + "";
//    }

	/**
	 * 对float类型的数值保留指定位数的小数。<br>
	 * 该方法舍入模式：向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。<br>
	 * <b>注意：</b>如果精度要求比较精确请使用 keepPrecision(String number, int precision)方法
	 * 
	 * @param number
	 *            要保留小数的数字
	 * @param precision
	 *            小数位数
	 * @return double 如果数值较大，则使用科学计数法表示
	 */
	public static String keepPrecision(double number, int precision) {
		if(number== Double.POSITIVE_INFINITY)return "INFINITY";
		if(number== Double.NEGATIVE_INFINITY)return "-INFINITY";
		//NaN 不能直接用==比较，全返回false
		if(Double.compare(number, Double.NaN)==0)return "NaN";
		BigDecimal bg = new BigDecimal(number);
		String ret = bg.setScale(precision, BigDecimal.ROUND_HALF_UP).toPlainString();
		return ret;
	}

	/**
	 * zip包相对路径中获取包名的前三位
	 *
	 * @param endUrl
	 *            相对路径如：/sds/sdk323.zip
	 * @return
	 */
	public static String getSecretKey(String endUrl) {
		String key = endUrl.substring(endUrl.lastIndexOf("/") + 1,
				endUrl.lastIndexOf("."));
		key = key.substring(0, 3);
		return key;
	}

	/**
	 * 解压缩
	 * @param value
	 * @return
	 * @throws DataFormatException
	 */
	public static byte[] decompress(byte[] value) throws DataFormatException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream(value.length);

		Inflater decompressor = new Inflater();

		try {
			decompressor.setInput(value);

			final byte[] buf = new byte[1024];
			while (!decompressor.finished()) {
				int count = decompressor.inflate(buf);
				bos.write(buf, 0, count);
			}
		} finally {
			decompressor.end();
		}

		return bos.toByteArray();
	}
	/**
	 * 去除相同的分时点合在一起然后返回
	 *
	 * @param recordList
	 * @param recordList2
	 * @return
	 */
	public static ArrayList<TimePointEntity> deRepeatTList(
			List<TimePointEntity> recordList, List<TimePointEntity> recordList2) {
		ArrayList<TimePointEntity> temp = new ArrayList<TimePointEntity>();
		TimePointEntity kpe;
		for (int i = 0, len = recordList.size(); i < len; i++) {
			kpe = recordList.get(i);
			if (!recordList2.contains(kpe)) {
				temp.add(kpe);
			}
		}
		temp.addAll(0, recordList2);
		Collections.sort(temp, new Comparator<TimePointEntity>() {
			@Override
			public int compare(TimePointEntity lhs, TimePointEntity rhs) {
				if (lhs.getTradeDate() > rhs.getTradeDate())
					return 1;
				if (lhs.getTradeDate() == rhs.getTradeDate()
						&& lhs.getTime() > rhs.getTime()) {
					return 1;
				}
				return -1;
			}
		});
		return temp;
	}
}
