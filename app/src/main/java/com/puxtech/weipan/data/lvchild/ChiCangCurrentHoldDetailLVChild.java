package com.puxtech.weipan.data.lvchild;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.data.HoldDetailData;
import com.puxtech.weipan.data.entitydata.InterfaceEntity;

/**
 * 交易首页中的一个账号
 *
 * @author fanshuo
 */
public class ChiCangCurrentHoldDetailLVChild implements InterfaceEntity {
    MyApplication myapp;
    //ArrayList<PriceCommodityEntity> priceCommodityEntityArrayList;
    Context context;
    HoldDetailData holdDetailData;

    public ChiCangCurrentHoldDetailLVChild(Context context, HoldDetailData holdDetailData) {
        this.myapp = (MyApplication) context.getApplicationContext();
        this.context = context;
        this.holdDetailData = holdDetailData;
        //  priceCommodityEntityArrayList=  myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getAllCommodityList();
    }

//    public MemberEntity getMemberEntity() {
//        return memberEntity;
//    }
//
//    public void setMemberEntity(MemberEntity memberEntity) {
//        this.memberEntity = memberEntity;
//    }

    @Override
    public int getLayoutId() {
        return R.layout.chicangholddetail_item;
    }

    @Override
    public View getView(int position, View convertView, LayoutInflater inflater) {
        final Holder holder;
        if (convertView == null || convertView.getTag() == null
                || convertView.getTag().getClass() != Holder.class) {

            holder = new Holder();
            convertView = inflater.inflate(getLayoutId(), null);

            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.bt_drop = (Button) convertView.findViewById(R.id.bt_drop);

            holder.tv_chicangjia = (TextView) convertView.findViewById(R.id.tv_chicangjia);
            holder.tv_baozhenjin = (TextView) convertView.findViewById(R.id.tv_baozhenjin);
            holder.tv_chiyouliang = (TextView) convertView.findViewById(R.id.tv_chiyouliang);
            holder.tv_fudongyinkui = (TextView) convertView.findViewById(R.id.tv_fudongyinkui);
            holder.tv_zhisun = (TextView) convertView.findViewById(R.id.tv_zhisun);
            holder.tv_zhiyin = (TextView) convertView.findViewById(R.id.tv_zhiyin);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv_name.setText(holdDetailData.getStuffName());
        holder.tv_time.setText(holdDetailData.getBuildTime());
        holder.tv_chicangjia.setText(holdDetailData.getHoldPrice());
        holder.tv_baozhenjin.setText(holdDetailData.getBaoZhengJin());
        holder.tv_chiyouliang.setText(holdDetailData.getHoldNumber());
        holder.tv_fudongyinkui.setText(holdDetailData.getFlp());
        holder.tv_zhisun.setText(holdDetailData.getStopLoss());
        holder.tv_zhiyin.setText(holdDetailData.getStopEarn());

        return convertView;
    }


    class Holder {
        TextView tv_name, tv_time;
        Button bt_drop;
        TextView tv_chicangjia, tv_baozhenjin, tv_chiyouliang, tv_fudongyinkui, tv_zhisun, tv_zhiyin;
    }

}
