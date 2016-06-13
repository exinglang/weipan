package com.puxtech.weipan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.puxtech.weipan.R;
import com.puxtech.weipan.data.ChooseItemData;

import java.util.List;

public class ChooseItemAdapter extends BaseAdapter {

	Context context;
	List<ChooseItemData> list;

	public ChooseItemAdapter(Context context, List<ChooseItemData> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public ChooseItemData getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.chooseitemadapter_item, parent, false);
			viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			convertView.setTag(viewHolder);
		}
		else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_name.setText(getItem(position).getName());
		return convertView;
	}
	
	static class ViewHolder{
		TextView tv_name;
	}

}
