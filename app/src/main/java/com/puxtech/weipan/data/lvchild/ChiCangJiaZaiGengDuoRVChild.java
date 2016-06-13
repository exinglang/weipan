package com.puxtech.weipan.data.lvchild;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.activity.PanFragmentChiCangMingXi;
import com.puxtech.weipan.data.entitydata.InterfaceRVEntity;

/**
 * 交易首页中的一个账号
 *
 * @author fanshuo
 */
public class ChiCangJiaZaiGengDuoRVChild implements InterfaceRVEntity {
    MyApplication myapp;
    Context context;
    PanFragmentChiCangMingXi.buttonCallBack buttonCallBack;
    int type;
    public ChiCangJiaZaiGengDuoRVChild(Context context, PanFragmentChiCangMingXi.buttonCallBack buttonCallBack, int type) {
        this.myapp = (MyApplication) context.getApplicationContext();
        this.context = context;
        this.buttonCallBack=buttonCallBack;
        this.type=type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.jiazaigengduo, parent,
                false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        try {
        ((MyViewHolder) holder).bt_more.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        buttonCallBack.holdDetailLoadMore(type);
                    }
                }
        );
        }catch(Exception e){
            e.printStackTrace();;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        Button bt_more;

        public MyViewHolder(View view) {
            super(view);
            bt_more = (Button) view.findViewById(R.id.bt_more);
        }
    }
}
