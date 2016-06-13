package com.puxtech.weipan.data.lvchild;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.data.TradeDealData;
import com.puxtech.weipan.data.entitydata.InterfaceRVEntity;
import com.puxtech.weipan.helper.TradeHelper;

/**
 * 交易首页中的一个账号
 *
 * @author fanshuo
 */
public class ChengJiaoTodayItemRVChild implements InterfaceRVEntity {
    MyApplication myapp;
    Context context;
    TradeDealData tradeDealData;

    public ChengJiaoTodayItemRVChild(Context context, TradeDealData tradeDealData) {
        this.myapp = (MyApplication) context.getApplicationContext();
        this.context = context;
        this.tradeDealData = tradeDealData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.chengjiao_item, parent,
                false));
    }

    //    public HoldDetailData getHoldDetailData(){
//
//        return  holdDetailData;
//    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        ((MyViewHolder) holder).tv_name.setText(holdDetailData.getStuffName());
//        ((MyViewHolder) holder).tv_time.setText(holdDetailData.getBuildTime());
//        ((MyViewHolder) holder).tv_chicangjia.setText(holdDetailData.getHoldPrice());
//        ((MyViewHolder) holder).tv_baozhenjin.setText(holdDetailData.getBaoZhengJin());
//        ((MyViewHolder) holder).tv_chiyouliang.setText(holdDetailData.getHoldNumber());
//        ((MyViewHolder) holder).tv_fudongyinkui.setText(holdDetailData.getFlp());
//        ((MyViewHolder) holder).tv_zhisun.setText(holdDetailData.getStopLoss());
//        ((MyViewHolder) holder).tv_zhiyin.setText(holdDetailData.getStopEarn());
//        ((MyViewHolder) holder).bt_drop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                buttonCallBack.dropButon(holdDetailData);
//            }
//        });
        ((MyViewHolder)holder).tv_type.setText(tradeDealData.getSE_F().equals("1")? Constant.KAI_CANG:Constant.PING_CANG);
        ((MyViewHolder)holder).tv_name.setText(new TradeHelper(context).getCommodityNameByCode( tradeDealData.getCO_I()));
        ((MyViewHolder)holder).tv_time.setText(tradeDealData.getTI());
        ((MyViewHolder)holder).tv_chengjiaojiage.setText(tradeDealData.getPR());
        ((MyViewHolder)holder).tv_chengjiaoshuliang.setText(tradeDealData.getQTY());
        ((MyViewHolder)holder).tv_shouxufei.setText(tradeDealData.getCOMM());
    }
   public  class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_type, tv_name, tv_time, tv_chengjiaojiage, tv_chengjiaoshuliang, tv_shouxufei;

        public MyViewHolder(View convertView) {
            super(convertView);
            tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            tv_chengjiaojiage = (TextView) convertView.findViewById(R.id.tv_chengjiaojiage);
            tv_chengjiaoshuliang = (TextView) convertView.findViewById(R.id.tv_chengjiaoshuliang);
            tv_shouxufei = (TextView) convertView.findViewById(R.id.tv_shouxufei);
        }
    }

}
