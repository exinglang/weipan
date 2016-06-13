package com.puxtech.weipan.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.puxtech.weipan.R;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.data.entitydata.WuDangEntity;
import com.puxtech.weipan.util.PriceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * <b>Description:</b>
 * <p>
 * 五档明细
 * </p>
 * 
 * @Company 北京乐赢索思软件科技有限责任公司
 * @author zuochenyong
 * @date 2014-12-9 下午15:55:18
 * @version V1.0
 * @FileName WuDangMingXi.java
 * @Package com.happyinsource.htjy.android.view
 */
public class WuDangMingXi extends MingXi implements OnClickListener {

	TextView pricedetailactivity_wudangmingxi_wd;
	public WuDangMingXi(Context context) {
		super(context);
		this.context = context;
	}
	LinearLayout wd_layout;
	LinearLayout sell_Linear, buy_Linear;
	public void initWuDangMingXi(LinearLayout wudangmingxi) {
		LayoutInflater inflater = LayoutInflater.from(context);

		View view_wudang = inflater.inflate(
				R.layout.pricedetailactivity_wudangmingxi, null);
		LinearLayout ll=(LinearLayout)view_wudang;
		pricedetailactivity_wudangmingxi_mx=(TextView)ll.findViewById(R.id.pricedetailactivity_wudangmingxi_mx);
		pricedetailactivity_wudangmingxi_wd=(TextView)ll.findViewById(R.id.pricedetailactivity_wudangmingxi_wd);
		pricedetailactivity_wudangmingxi_wd.setVisibility(View.VISIBLE);
		pricedetailactivity_wudangmingxi_mx.setVisibility(View.VISIBLE);
		
		pricedetailactivity_wudangmingxi_mx.setOnClickListener(this);
		pricedetailactivity_wudangmingxi_wd.setOnClickListener(this);
		wd_layout=(LinearLayout) ll.getChildAt(ll.getChildCount()-2);
		mx_layout=(LinearLayout) ll.getChildAt(ll.getChildCount()-1);
		wd_layout.setVisibility(View.VISIBLE);
		FrameLayout fl=(FrameLayout) wd_layout.getChildAt(0);
		sell_Linear=(LinearLayout)fl.getChildAt(1);//退 的 布局
		
		fl=(FrameLayout) wd_layout.getChildAt(2);
		buy_Linear=(LinearLayout)fl.getChildAt(1);//摘的布局
		
		wudangmingxi.removeAllViews();
		wudangmingxi.addView(view_wudang, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

	}
	private TextView getIndexTextView(boolean buy,int index,int tv_index){
		TextView tv;
		RelativeLayout rl;
		if(buy){
			rl=(RelativeLayout) buy_Linear.getChildAt(index);
		}else{
			rl=(RelativeLayout) sell_Linear.getChildAt(index);
		}
		if(rl==null)return null;
		tv=(TextView)rl.getChildAt(tv_index);
		return tv;
	}
	private void switchBg() {
		Drawable wdW=pricedetailactivity_wudangmingxi_wd.getBackground();
		Drawable wdM=pricedetailactivity_wudangmingxi_mx.getBackground();
		pricedetailactivity_wudangmingxi_mx.setBackgroundDrawable(wdW);
		pricedetailactivity_wudangmingxi_wd.setBackgroundDrawable(wdM);
	}
	
	public void refreshWD(PriceEntity entity) {
		ArrayList<WuDangEntity> wuDangList=entity.getWuDangList();
		if(wuDangList==null||wuDangList.size()==0){
			return;
		}
		ArrayList<WuDangEntity> buy_list=new ArrayList<WuDangEntity>();
		ArrayList<WuDangEntity> sell_list=new ArrayList<WuDangEntity>();
		for(WuDangEntity en:wuDangList){
			if(en.isIfBuy()){
				buy_list.add(en);
			}else{
				sell_list.add(en);
			}
		}
		Comparator<WuDangEntity> comparator=new Comparator<WuDangEntity>(){
			@Override
			public int compare(WuDangEntity lhs, WuDangEntity rhs) {
				if(lhs.getPrice()>rhs.getPrice()){
					return 1;
				}else if(lhs.getPrice()<rhs.getPrice()){
					return -1;
				}else
				return 0;
			}
		};
		Collections.sort(buy_list, comparator);
		Collections.sort(sell_list, comparator);
		TextView tv;
		double price;
		for (int i = 0,len=buy_list.size(); i < len; i++) {
			tv=getIndexTextView(true, i, 2);
			if(tv==null)break;
			tv.setText(String.valueOf(buy_list.get(len - 1 - i).getGuaPaiNumber() - buy_list.get(len - 1 - i).getZhaiPaiNumber()));
			
			tv=getIndexTextView(true, i,1);
			price=buy_list.get(len-1-i).getPrice();
			setTvColor(tv, entity, price);
			if(price==0.0)tv.setText("-");
			else
			tv.setText(PriceUtil.formatPrice(context, price, entity.getNumber()));
		}
		if(buy_list.size()<5){
			for (int i = buy_list.size(); i < 5; i++) {
				tv=getIndexTextView(true, i, 2);
				tv.setText("-");
				
				tv=getIndexTextView(true, i,1);
				tv.setText("-");
			}
		}
		ArrayList<WuDangEntity > removes=new ArrayList<WuDangEntity>();
		for (int i = 0; i < sell_list.size(); i++) {
			if(sell_list.get(i).getPrice()==0.0){
				removes.add(sell_list.get(i));
			}
		}
		sell_list.removeAll(removes);
		sell_list.addAll(removes);
		for (int i = 0,len=sell_list.size(); i < len; i++) {
			tv=getIndexTextView(false, 4-i, 2);
			if(tv==null)break;
			tv.setText(String.valueOf(sell_list.get(i).getGuaPaiNumber() - sell_list.get(i).getZhaiPaiNumber()));
			
			tv=getIndexTextView(false, 4-i,1);
			price = sell_list.get(i).getPrice();
			setTvColor(tv, entity, price);
			if(price==0.0)tv.setText("-");
			else
			tv.setText(PriceUtil.formatPrice(context, price,entity.getNumber()));
		}
		if(sell_list.size()<5){
			for (int i = sell_list.size(); i < 5; i++) {
				tv=getIndexTextView(false, 4-i, 2);
				tv.setText("-");
				
				tv=getIndexTextView(false, 4-i,1);
				tv.setText("-");
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		int id=v.getId();
		if(id==R.id.pricedetailactivity_wudangmingxi_wd){
			if(wd_layout.getVisibility()== View.VISIBLE){
				return;
			}
			wd_layout.setVisibility(View.VISIBLE);
			mx_layout.setVisibility(View.GONE);
			switchBg();
			
		}else if(id== R.id.pricedetailactivity_wudangmingxi_mx){
			if(mx_layout.getVisibility()== View.VISIBLE){
				return;
			}
			wd_layout.setVisibility(View.GONE);
			mx_layout.setVisibility(View.VISIBLE);
			switchBg();
		}
	}
	
	

}
