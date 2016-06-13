package com.puxtech.weipan.data.lvchild;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.activity.PanFragmentChiCangMingXi;
import com.puxtech.weipan.data.entitydata.InterfaceRVEntity;
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
public class ChiCangSelectCommodityRVChild implements InterfaceRVEntity {
    MyApplication myapp;
    ArrayList<PriceCommodityEntity> priceCommodityEntityArrayList;
    Context context;
    PanFragmentChiCangMingXi chiCangMingXi;
    String code;
    public ChiCangSelectCommodityRVChild(Context context, PanFragmentChiCangMingXi chiCangMingXi, String code) {
        this.myapp = (MyApplication) context.getApplicationContext();
        this.context = context;
        this.chiCangMingXi = chiCangMingXi;
        this.code = code;
        priceCommodityEntityArrayList = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getAllCommodityList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.chicangselectcommodity, parent,
                false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        List list =new TradeHelper(context).getPriceCommodityNameList();
        final ArrayList<String> addAllList = new ArrayList<>();
        addAllList.add("全部");
        addAllList.addAll(list);
         MyViewHolder myViewHolder = (MyViewHolder) holder;
        // ,code
        if (code.equals("")) {
            myViewHolder.tv_commodity.setText("全部");
        } else {
            myViewHolder.tv_commodity.setText(new TradeHelper(context).getCommodityNameByCode( code));
        }
       ActivityUtils.setSpinnerAdapter(myViewHolder.ll_commodity, addAllList,
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        ((PopupWindow) parent.getTag()).dismiss();
                        if (position == 0) {
                            //选择的是全部
                            chiCangMingXi.reSelectCommodity("");
                        } else {
                            PriceCommodityEntity priceCommodityEntity = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getAllCommodityList().get(position - 1);
                            chiCangMingXi.reSelectCommodity(priceCommodityEntity.getCode());
                        }
                    }
                });


    }


   public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_commodity;
        TextView tv_commodity;

        public MyViewHolder(View view) {
            super(view);
            ll_commodity = (LinearLayout) view.findViewById(R.id.ll_commodity);
            tv_commodity = (TextView) view.findViewById(R.id.tv_commodity);
        }
    }
}
