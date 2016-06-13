package com.puxtech.weipan.data.entitydata;
/**
 * 此类只用于给LruCache增加size 因为刷新时导致缓存的KlineEntity 中的pointList增加，
 * trimtoSize时  可能会算出结果为负值程序抛出异常
 * @author happyinsource
 *
 */
public class AddSizeKlineEntity extends KLineEntity {
	private String key;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	private int new_point_len;
	public void setNew_point_len(int new_point_len) {
		this.new_point_len = new_point_len;
	}
	public int getNew_point_len() {
		return new_point_len;
	}
}
