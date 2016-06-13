package com.puxtech.weipan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.puxtech.weipan.data.entitydata.InterfaceEntity;

import java.util.ArrayList;

/**
 * <b>Description:</b>
 * <p>
 *
 * </p>
 * 

 */
public class InterfaceEntityAdapter extends BaseAdapter {

	Context context;
	ArrayList<InterfaceEntity> list;

	public InterfaceEntityAdapter(Context context, ArrayList<InterfaceEntity> list) {
		super();
		this.context = context;
		this.list = list;
	}

	
	public ArrayList<InterfaceEntity> getList() {
		return list;
	}

	public void setList(ArrayList<InterfaceEntity> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public InterfaceEntity getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		return getItem(position).getView(position,convertView,
				LayoutInflater.from(context));
	}

}
