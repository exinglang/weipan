package com.puxtech.weipan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.data.entitydata.InterfaceRVEntity;
import com.puxtech.weipan.data.lvchild.ChiCangCurrentHoldDetailRVChild;
import com.puxtech.weipan.data.lvchild.ChiCangSelectCommodityRVChild;

import java.util.ArrayList;

/**
 * @author fanshuo
 */
public class InterfaceRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    MyApplication myapp;
    Context context;
    ArrayList<InterfaceRVEntity> list;

    public InterfaceRecycleViewAdapter(Context context, ArrayList<InterfaceRVEntity> list) {
        this.myapp = (MyApplication) context.getApplicationContext();
        this.context = context;
        this.list = list;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return list.get(viewType).onCreateViewHolder(parent, viewType);
    }

    public void changeData(int position) {
        //mDatas.remove(position);

        notifyItemInserted(position);
    }

    //    public void add(int location, E object) {
//        synchronized (lock) {
//            list.add(location, object);
//            notifyItemInserted(location);
//        }
//    }
    public void addItem(int position, InterfaceRVEntity item) {
        list.add(position, item);
        notifyItemInserted(position);

    }

    public void removeData(int position) {
        //mDatas.remove(position);
//        noti
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //if(holder instanceof ChiCangCurrentHoldDetailRVChild.MyViewHolder) {
//            ((ChiCangSelectCommodityRVChild.MyViewHolder)holder).

        list.get(position).onBindViewHolder(holder, position);
        // }
    }

    public void mNotifyDataSetChanged() {
        //mDatas.remove(position);
//        noti

        notifyDataSetChanged();
        ;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
