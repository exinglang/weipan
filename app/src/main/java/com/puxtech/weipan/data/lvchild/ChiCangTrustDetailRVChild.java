package com.puxtech.weipan.data.lvchild;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.activity.PanFragmentChiCangMingXi;
import com.puxtech.weipan.data.TradeTrustData;
import com.puxtech.weipan.data.entitydata.InterfaceRVEntity;

/**
 * 交易首页中的一个账号
 *
 * @author fanshuo
 */
public class ChiCangTrustDetailRVChild implements InterfaceRVEntity {
    MyApplication myapp;
    Context context;
    TradeTrustData holdDetailData;
    PanFragmentChiCangMingXi.buttonCallBack buttonCallBack;

    public ChiCangTrustDetailRVChild(Context context, TradeTrustData holdDetailData,PanFragmentChiCangMingXi.buttonCallBack buttonCallBack) {
        this.myapp = (MyApplication) context.getApplicationContext();
        this.context = context;
        this.holdDetailData = holdDetailData;
        this.buttonCallBack=buttonCallBack;
    }

    public TradeTrustData getHoldTrustData(){

        return  holdDetailData;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            ((MyViewHolder) holder).tv_name.setText(holdDetailData.getName());
            ((MyViewHolder) holder).tv_time.setText(holdDetailData.getTime());
            ((MyViewHolder) holder).tv_weituojia.setText(holdDetailData.getPri());
            ((MyViewHolder) holder).tv_weituoshuliang.setText(holdDetailData.getQty());
            ((MyViewHolder) holder).tv_dongjiebaozhenjin.setText(holdDetailData.getF_mar());
            ((MyViewHolder) holder).tv_dongjieshouxufei.setText(holdDetailData.getF_fee());
            ((MyViewHolder) holder).bt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonCallBack.cancelTrust(holdDetailData);

                }
            });
        }catch(Exception e){
            e.printStackTrace();;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_time;
        Button bt_cancel;
        TextView tv_weituojia, tv_weituoshuliang, tv_dongjiebaozhenjin, tv_dongjieshouxufei;
        public MyViewHolder(View convertView) {
            super(convertView);
           tv_name = (TextView)convertView.findViewById(R.id.tv_name);
           tv_time = (TextView)convertView.findViewById(R.id.tv_time);
            bt_cancel = (Button) convertView.findViewById(R.id.bt_cancel);
            tv_weituojia = (TextView) convertView.findViewById(R.id.tv_weituojia);
            tv_weituoshuliang = (TextView) convertView.findViewById(R.id.tv_weituoshuliang);
            tv_dongjiebaozhenjin = (TextView) convertView.findViewById(R.id.tv_dongjiebaozhenjin);
            tv_dongjieshouxufei = (TextView) convertView.findViewById(R.id.tv_dongjieshouxufei);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return   new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.chicangtrustdetail_item, parent,
                false));
    }

}
