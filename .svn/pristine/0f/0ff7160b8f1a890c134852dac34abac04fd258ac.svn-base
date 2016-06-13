package com.puxtech.weipan.data.lvchild;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.data.entitydata.InterfaceRVEntity;

/**
 * 交易首页中的一个账号
 *
 * @author fanshuo
 */
public class ChiCangDaiChengJiaoJianCangRVChild implements InterfaceRVEntity {
    MyApplication myapp;
    Context context;
    public ChiCangDaiChengJiaoJianCangRVChild(Context context) {
        this.myapp = (MyApplication) context.getApplicationContext();
         this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return   new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.chicangdaichengjiaojiancang, parent,
                false));
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View view) {
            super(view);
        }
    }
}
