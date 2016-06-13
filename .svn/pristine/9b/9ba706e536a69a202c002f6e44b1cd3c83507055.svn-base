package com.puxtech.weipan.helper;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.puxtech.weipan.R;
import com.puxtech.weipan.data.entitydata.FenBi;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.util.PriceUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class MingXi implements IWuDangMingxi {

	Context context;
	TextView pricedetailactivity_wudangmingxi_mx;
	public MingXi(Context context) {

		this.context = context;
	}
	LinearLayout mx_layout;
	public void initWuDangMingXi(LinearLayout wudangmingxi) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view_wudang = inflater.inflate(
				R.layout.pricedetailactivity_wudangmingxi, null);

		setMingXiLiang(context, view_wudang);
		LinearLayout ll=(LinearLayout)view_wudang;
		pricedetailactivity_wudangmingxi_mx=(TextView)ll.findViewById(R.id.pricedetailactivity_wudangmingxi_mx);
		pricedetailactivity_wudangmingxi_mx.setVisibility(View.VISIBLE);


		ll.findViewById(R.id.pricedetailactivity_wudangmingxi_divider).setVisibility(View.GONE);
		mx_layout=(LinearLayout) ll.getChildAt(ll.getChildCount()-1);
		mx_layout.setVisibility(View.VISIBLE);
		wudangmingxi.removeAllViews();
		wudangmingxi.addView(view_wudang, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

	}

	private void setMingXiLiang(Context context, View view) {
		view.findViewById(R.id.liang).setVisibility(View.GONE);
		view.findViewById(R.id.liang_value_1).setVisibility(View.GONE);
		view.findViewById(R.id.liang_value_2).setVisibility(View.GONE);
		view.findViewById(R.id.liang_value_3).setVisibility(View.GONE);
		view.findViewById(R.id.liang_value_4).setVisibility(View.GONE);
		view.findViewById(R.id.liang_value_5).setVisibility(View.GONE);
		view.findViewById(R.id.liang_value_6).setVisibility(View.GONE);
		view.findViewById(R.id.liang_value_7).setVisibility(View.GONE);
		view.findViewById(R.id.liang_value_8).setVisibility(View.GONE);

		view.findViewById(R.id.liang_value_9).setVisibility(View.GONE);

		view.findViewById(R.id.liang_value_10).setVisibility(View.GONE);
	}

	protected void setTvColor(TextView tv,PriceEntity entity,double price){
		if(entity.getYesterdayClosePrice()>price){
			tv.setTextColor(Color.GREEN);
		}
		else if(entity.getYesterdayClosePrice()<price){
			tv.setTextColor(Color.RED);
		}
		else{
			tv.setTextColor(Color.WHITE);
		}
	}
	
	ArrayList<FenBi> tenFenBi = new ArrayList<FenBi>();
	public void refreshMingXi(PriceEntity entity) {
		ArrayList<FenBi> fbs=entity.getFenBiList();
		if(fbs==null||fbs.size()==0){
			return;
		}
		tenFenBi.addAll(fbs);
		Collections.sort(tenFenBi);
		if(tenFenBi.size() > 10){
			int count = tenFenBi.size();
			for (int i = 0; i < count-10; i++) {
				tenFenBi.remove(0);
			}
		}
		int child_count=mx_layout.getChildCount()-1;
//		
		int len=child_count>tenFenBi.size()?tenFenBi.size():child_count;
		
		LinearLayout child_one;
		TextView tv;
		FenBi fb;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		double price;
		Date d=new Date();
		for (int i = 0; i < len; i++) {
			child_one = (LinearLayout) mx_layout.getChildAt(i+1);//要跳过第一行，第一行为标题
			fb = tenFenBi.get(i);//显示最新的
			tv = (TextView) child_one.getChildAt(0);
			d.setTime(fb.getTime());
			tv.setText(sdf.format(d));
			tv = (TextView) child_one.getChildAt(1);
			price=fb.getPrice();
			setTvColor(tv, entity, price);
			tv.setText(PriceUtil.formatPrice(context, fb.getPrice(), entity.getNumber()));
			tv = (TextView) child_one.getChildAt(2);
			tv.setText(String.valueOf(fb.getVolume()));
		}
	}

	@Override
	public void refreshWD(PriceEntity entity) {
		// TODO Auto-generated method stub
		
	}
	

}
