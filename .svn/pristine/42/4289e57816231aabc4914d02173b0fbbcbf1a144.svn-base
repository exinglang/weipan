package com.puxtech.weipan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.puxtech.weipan.R;
import com.puxtech.weipan.data.ChooseItemData;
import com.puxtech.weipan.data.SysInfoData;

import java.util.ArrayList;
import java.util.List;

public class GeRenSysInfoAdapter extends BaseAdapter {

	Context context;
	ArrayList<SysInfoData> list;

	public GeRenSysInfoAdapter(Context context, ArrayList<SysInfoData> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public SysInfoData getItem(int position) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_sysinfo_listview_item, parent, false);
			viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);

			convertView.setTag(viewHolder);
		}
		else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_title.setText(getItem(position).getTITLE());
		viewHolder.tv_time.setText(getItem(position).getSEND_TIME());

		return convertView;
	}
	
	static class ViewHolder{
		TextView tv_title,tv_time;
	}

}
