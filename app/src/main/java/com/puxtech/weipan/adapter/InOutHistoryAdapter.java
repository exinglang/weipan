package com.puxtech.weipan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.puxtech.weipan.R;
import com.puxtech.weipan.data.ChooseItemData;
import com.puxtech.weipan.data.entitydata.InOutHistoryEntity;

import java.util.ArrayList;
import java.util.List;

public class InOutHistoryAdapter extends RecyclerView.Adapter<InOutHistoryAdapter.TextViewHolder> {
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;

    private final View header;
    private final List<InOutHistoryEntity> list;

    public InOutHistoryAdapter(View header, List<InOutHistoryEntity> list) {
        if (header == null) {
            throw new IllegalArgumentException("header may not be null");
        }
        this.header = header;
        this.list = list;
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            return new TextViewHolder(header);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inout_history_item, parent, false);
        return new TextViewHolder(view);
    }

    public void onBindViewHolder(final TextViewHolder viewHolder, final int position) {
        if (isHeader(position)) {
            return;
        }
        // final String label = labels.get(position - 1);  // Subtract 1 for header
//        holder.textView.setText(label);
//        holder.textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(
//                        holder.textView.getContext(), label, Toast.LENGTH_SHORT).show();
//            }
//        });P
        int mPosition=position-1;
        viewHolder.tv_time.setText(list.get(mPosition).getTd());
        viewHolder.tv_no.setText(list.get(mPosition).getMid());
        viewHolder.tv_bankname.setText(list.get(mPosition).getBank_name());
        viewHolder.tv_amount.setText(list.get(mPosition).getAM());
        viewHolder.tv_type.setText(list.get(mPosition).getTT());
        viewHolder.tv_state.setText(list.get(mPosition).getTS());
        viewHolder.tv_memo.setText(list.get(mPosition).getREM());
    }

    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {


        return list.size() + 1;
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {

        TextView tv_time, tv_no, tv_bankname, tv_amount, tv_type, tv_state, tv_memo;

        public TextViewHolder(View convertView) {
            super(convertView);
            tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            tv_no = (TextView) convertView.findViewById(R.id.tv_no);
            tv_bankname = (TextView) convertView.findViewById(R.id.tv_bankname);
            tv_amount = (TextView) convertView.findViewById(R.id.tv_amount);
            tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            tv_memo = (TextView) convertView.findViewById(R.id.tv_memo);
        }
    }

}