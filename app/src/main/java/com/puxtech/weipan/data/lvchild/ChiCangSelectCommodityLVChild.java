package com.puxtech.weipan.data.lvchild;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.activity.PanFragmentChiCangMingXi;
import com.puxtech.weipan.data.entitydata.InterfaceEntity;
import com.puxtech.weipan.data.entitydata.PriceCommodityEntity;
import com.puxtech.weipan.helper.TradeHelper;
import com.puxtech.weipan.util.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易首页中的一个账号
 *
 * @author fanshuo
 */
public class ChiCangSelectCommodityLVChild implements InterfaceEntity {
    MyApplication myapp;
    ArrayList<PriceCommodityEntity> priceCommodityEntityArrayList;
    Context context;
    PanFragmentChiCangMingXi chiCangMingXi;

    public ChiCangSelectCommodityLVChild(Context context, PanFragmentChiCangMingXi chiCangMingXi) {
        this.myapp = (MyApplication) context.getApplicationContext();
        this.context = context;
        this.chiCangMingXi = chiCangMingXi;
        priceCommodityEntityArrayList = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getAllCommodityList();
    }


    @Override
    public int getLayoutId() {
        return R.layout.chicangselectcommodity;
    }

    @Override
    public View getView(int position, View convertView, LayoutInflater inflater) {
        final Holder holder;
        if (convertView == null || convertView.getTag() == null
                || convertView.getTag().getClass() != Holder.class) {

            holder = new Holder();
            convertView = inflater.inflate(getLayoutId(), null);
            holder.ll_commodity = (LinearLayout) convertView.findViewById(R.id.ll_commodity);

            holder.tv_commodity = (TextView) convertView.findViewById(R.id.tv_commodity);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        List list = new TradeHelper(context).getPriceCommodityNameList();
        final ArrayList<String> addAllList = new ArrayList<>();
        addAllList.add("全部");
        addAllList.addAll(list);
        ActivityUtils.setSpinnerAdapter(holder.ll_commodity, addAllList,
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        if (position == 0) {
                            //选择的是全部
                            chiCangMingXi.reSelectCommodity("");
                        } else {
                            PriceCommodityEntity priceCommodityEntity = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getAllCommodityList().get(position - 1);
                            chiCangMingXi.reSelectCommodity(priceCommodityEntity.getCode());
                        }
                        holder.tv_commodity.setText(addAllList.get(position));
                        PopupWindow pop = (PopupWindow) holder.ll_commodity.getTag();
                        if (pop != null && pop.isShowing()) {
                            pop.dismiss();
                        }
                    }
                });
        return convertView;
    }

    class Holder {
        LinearLayout ll_commodity;
        TextView tv_commodity;
    }

}
