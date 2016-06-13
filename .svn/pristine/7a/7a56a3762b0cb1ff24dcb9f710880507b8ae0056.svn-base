package com.puxtech.weipan.data.lvchild;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.activity.PanFragmentChiCangMingXi;
import com.puxtech.weipan.data.HoldDetailData;
import com.puxtech.weipan.data.entitydata.InterfaceRVEntity;

/**
 * 交易首页中的一个账号
 *
 * @author fanshuo
 */
public class ChiCangCurrentHoldDetailRVChild implements InterfaceRVEntity {
    MyApplication myapp;
    Context context;
    HoldDetailData holdDetailData;
    PanFragmentChiCangMingXi.buttonCallBack buttonCallBack;
    public ChiCangCurrentHoldDetailRVChild(Context context, HoldDetailData holdDetailData, PanFragmentChiCangMingXi.buttonCallBack buttonCallBack) {
        this.myapp = (MyApplication) context.getApplicationContext();
        this.context = context;
        this.holdDetailData = holdDetailData;
        this.buttonCallBack=buttonCallBack;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return   new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.chicangholddetail_item, parent,
                false));
    }
    public HoldDetailData getHoldDetailData(){

        return  holdDetailData;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            ((MyViewHolder) holder).tv_name.setText(holdDetailData.getStuffName());
            ((MyViewHolder) holder).tv_time.setText(holdDetailData.getBuildTime());
            ((MyViewHolder) holder).tv_chicangjia.setText(holdDetailData.getHoldPrice());
            ((MyViewHolder) holder).tv_baozhenjin.setText(holdDetailData.getBaoZhengJin());
            ((MyViewHolder) holder).tv_chiyouliang.setText(holdDetailData.getHoldNumber());
            ((MyViewHolder) holder).tv_fudongyinkui.setText(holdDetailData.getFlp());
            ((MyViewHolder) holder).tv_zhisun.setText(holdDetailData.getStopLoss());
            ((MyViewHolder) holder).tv_zhiyin.setText(holdDetailData.getStopEarn());
            ((MyViewHolder) holder).bt_drop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonCallBack.dropButon(holdDetailData);
                }
            });
        }catch (Exception e){
            e.printStackTrace();;
        }
    }



   public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_time;
        Button bt_drop;
        TextView tv_chicangjia, tv_baozhenjin, tv_chiyouliang, tv_fudongyinkui, tv_zhisun, tv_zhiyin;
        public MyViewHolder(View convertView) {
            super(convertView);
           tv_name = (TextView)convertView.findViewById(R.id.tv_name);
           tv_time = (TextView)convertView.findViewById(R.id.tv_time);
           bt_drop = (Button) convertView.findViewById(R.id.bt_drop);
           tv_chicangjia = (TextView) convertView.findViewById(R.id.tv_chicangjia);
           tv_baozhenjin = (TextView) convertView.findViewById(R.id.tv_baozhenjin);
           tv_chiyouliang = (TextView) convertView.findViewById(R.id.tv_chiyouliang);
           tv_fudongyinkui = (TextView) convertView.findViewById(R.id.tv_fudongyinkui);
           tv_zhisun = (TextView) convertView.findViewById(R.id.tv_zhisun);
           tv_zhiyin = (TextView) convertView.findViewById(R.id.tv_zhiyin);


        }
    }

}
