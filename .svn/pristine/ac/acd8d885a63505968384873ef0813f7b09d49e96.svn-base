package com.puxtech.weipan.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences辅助类
 * @author fanshuo
 */
public class SharedPreferenceManager {

	public static final String FILE_NAME_YIN_DAO_YE = "YIN_DAO_YE_SharedPreferences";//文件名，只用于保存图表数据的最新刷新时间

	public static final String FILE_NAME_COMMODITY = "Htjy_Commodity_SharedPreferences";//文件名，只用于保存图表数据的最新刷新时间
	public static final String FILE_NAME_PRICE_SETTING = "Htjy_Price_Setting_SharedPreferences";//文件名，只用于行情模块的设置信息
	public static final String FILE_NAME_MESSAGE_SETTING = "Htjy_Message_Setting_SharedPreferences";//文件名，只用于资讯模块的设置信息
	public static final String KEY_KLINE_LAST_REFRESH_TIME_PRENAME = "key_kline_last_refresh_time_";//key的前缀，后面添加 周期_商品编号，完整显示为key_kline_last_refresh_time_周期_商品编号
//	public static final String KEY_PRICE_SELECTED_COMMODITY_NUMBERS = "key_price_selected_commodity_numbers";//自选商品商品编号，分号隔开
    public static final String KEY_PRICE_SELECTED_COMMODITY_NUMBERS_AND_ZUID = "key_price_selected_commodity_numbers_and_zuid";//自选商品商品编号，分号隔开,商品编码:组ID
    public static final String KEY_PRICE_HAS_SHOW_TIP_SWIPE = "key_price_has_show_tip_swipe";//是否显示过滑动提示
	public static final String KEY_PRICE_HAS_SHOW_TIP_SWIPE_TRADE_LOGINLIST = "key_price_has_show_tip_swipe_trade_loginlist";//是否显示过滑动提示(交易-账户)
	public static final String KEY_PRICE_HAS_SHOW_TIP_LONG_CLICK = "key_price_has_show_tip_long_click";//是否显示过长按自选商品的提示
	public static final String KEY_CUR_VERSIONCODE = "key_cur_versioncode";//是否是首次运行
	/**
	 * 自选列表中手动删除的持仓商品（key后面需要加上账号-交易所id-环境id，这个是需要按账号和环境区分开的）。<br>key的格式（不含花括号）：{key_deleted_chi_cang_list}-{交易所id}-{环境}<br>value的值用英文分号隔开
	 */
	public static final String KEY_DELETED_CHI_CANG_LIST = "key_deleted_chi_cang_list";
	public static final String KEY_IS_FIRST_RUN = "key_is_first_run";//是否是首次运行
	public static final String KEY_CONTENT_SERVER_REFRESH_TIME = "key_content_server_refresh_time";//目录服务器数据更新时间
	public static final String KEY_HAS_AGREE = "key_has_agree";//是否同意过使用条款
	public static final String KEY_TISHI_ROTATE = "key_tishi_rotate";//是否已经跳过此提示
	public static final String KEY_TISHI_ZOOM = "key_tishi_zoom";//是否已经跳过此提示
	public static final String KEY_TISHI_MOVE = "key_tishi_move";//是否已经跳过此提示
	public static final String KEY_TISHI_CLICK = "key_tishi_click";//是否已经跳过此提示
	public static final String KEY_TISHI_CHANGE_LINE = "key_tishi_change_line";//是否已经跳过此提示，切换分时k线
	public static final String TRADER_PUBLIC_NEW_INFO = "TRADER_PUBLIC_NEW_INFO";//是否显示公告的红点
	public static final String HAS_SHOW_YIN_DAO_YE = "HAS_SHOW_YIN_DAO_YE";//是否已显示引导页
	//微信
	public static final String WEI_XIN = "WEI_XIN";//微信相关
	public static final String WEI_XIN_UNION_ID = "WEI_XIN_UNION_ID";//union_id
	public static final String WEI_XIN_NICK_NAME = "WEI_XIN_NICK_NAME";//昵称


	//心跳信号
	public static final String BROAD_ID = "BROAD_ID";
	public static final String DEAL_COUNT = "DEAL_COUNT";
	public static final String LAST_ID = "LAST_ID";
	public static final String TD = "TD";

	//策略者
	public static final String TRADER_DEAL = "TRADER_DEAL";
	private SharedPreferences pre;
	public SharedPreferenceManager(Context context, String fileName) {
		super();
		this.pre = context.getSharedPreferences(fileName, 0);
	}
	
	/**
	 * 检查是否包含某个key
	 * @param key
	 * @return
	 */
	public boolean contains(String key){
		return pre.contains(key);
	}
	
	/**
	 * 获取k线缓存时间的KEY值
	 * @param cycle
	 * @param commodityNum
	 * @return
	 */
	public String getKLineLastRefreshTimeKey(int cycle, int commodityNum){
		return KEY_KLINE_LAST_REFRESH_TIME_PRENAME+cycle+"_"+commodityNum;
	}

	public float getFloat(final Context pContext, final String pKey,
			final float pDefaultValue) {
		return this.pre.getFloat(pKey, pDefaultValue);
	}

	public boolean putFloat(final Context pContext, final String pKey,
			final float pValue) {
		try {
			final SharedPreferences.Editor editor = this.pre.edit();
			editor.putFloat(pKey, pValue);
			return editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int getInt(final Context pContext, final String pKey,
			final int pDefaultValue) {
		return this.pre.getInt(pKey, pDefaultValue);
	}

	public boolean putInt(final Context pContext, final String pKey,
			final int pValue) {
		try {
			final SharedPreferences.Editor editor = this.pre.edit();
			editor.putInt(pKey, pValue);
			return editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public long getLong(final Context pContext, final String pKey,
			final long pDefaultValue) {
		return this.pre.getLong(pKey, pDefaultValue);
	}

	public boolean putLong(final Context pContext, final String pKey,
			final long pValue) {
		try {
			final SharedPreferences.Editor editor = this.pre.edit();
			editor.putLong(pKey, pValue);
			return editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean getBoolean(final Context pContext, final String pKey,
			final boolean pDefaultValue) {
		return this.pre.getBoolean(pKey, pDefaultValue);
	}

	public boolean putBoolean(final Context pContext, final String pKey,
			final boolean pValue) {
		try {
			final SharedPreferences.Editor editor = this.pre.edit();
			editor.putBoolean(pKey, pValue);
			return editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getString(final Context pContext, final String pKey,
			final String pDefaultValue) {
		return this.pre.getString(pKey, pDefaultValue);
	}

	public boolean putString(final Context pContext, final String pKey,
			final String pValue) {
		try {
			final SharedPreferences.Editor editor = this.pre.edit();
			editor.putString(pKey, pValue);
			return editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean remove(final Context context, final String key) {
		try {
			final SharedPreferences.Editor editor = this.pre.edit();
			editor.remove(key);
			return editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
}