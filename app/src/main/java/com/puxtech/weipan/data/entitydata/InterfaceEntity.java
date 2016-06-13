package com.puxtech.weipan.data.entitydata;

import android.view.LayoutInflater;
import android.view.View;

/**
 * 接口,交易首页中的的实体类
 * 
 * @author zuochenyong
 * 
 */
public interface InterfaceEntity {
	public int getLayoutId();

	public View getView(int position, View convertView, LayoutInflater inflater);

}
