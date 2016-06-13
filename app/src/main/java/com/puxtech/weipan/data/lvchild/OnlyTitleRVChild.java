package com.puxtech.weipan.data.lvchild;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.data.entitydata.InterfaceRVEntity;

/**
 * 交易首页中的一个账号
 *
 * @author fanshuo
 */
public class OnlyTitleRVChild implements InterfaceRVEntity {
    MyApplication myapp;
    Context context;
    String title;
    int imgId;
    public OnlyTitleRVChild(Context context, String title, int imgId) {

        this.myapp = (MyApplication) context.getApplicationContext();
        this.context = context;
        this.title = title;
        this.imgId=imgId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.fragment_shipan_public_only_text_item, parent,
                false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).tv_title.setText(title);
        Drawable drawable = context.getResources().getDrawable(imgId);
/// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        ((MyViewHolder) holder).tv_title.setCompoundDrawables(drawable, null, null, null);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;

        public MyViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
        }
    }
}
