package com.puxtech.weipan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.data.CommodityData;
import com.puxtech.weipan.data.entitydata.PriceCommodityEntity;
import com.puxtech.weipan.util.ActivityUtils;
import com.puxtech.weipan.util.PriceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 15/11/6.
 */
public class HorizontalScrollViewAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<PriceCommodityEntity> mDatas;

    public HorizontalScrollViewAdapter(Context context, ArrayList<PriceCommodityEntity> mDatas) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    public int getCount() {
        return mDatas.size();
    }

    public Object getItem(int position) {
        return mDatas.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(
                    R.layout.fragment_home_commoditylist_item, parent, false);
            LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.home_three_ll);

            WindowManager wm = (WindowManager) mContext
                    .getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            int height = wm.getDefaultDisplay().getHeight();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width / 3,
                    width / 4);//导入上面所述的包
            ll.setLayoutParams(layoutParams);

            viewHolder.tv_1 = (TextView) convertView
                    .findViewById(R.id.tv_1);
            viewHolder.tv_2 = (TextView) convertView
                    .findViewById(R.id.tv_2);
            viewHolder.tv_3 = (TextView) convertView
                    .findViewById(R.id.tv_3);
            viewHolder.tv_4 = (TextView) convertView
                    .findViewById(R.id.tv_4);
            viewHolder.img_arrow = (ImageView) convertView
                    .findViewById(R.id.img_arrow);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_1.setText(mDatas.get(position).getName());


//        /**
//         * 根据最小变动价位格式化价格
//         *
//         * @return
//         */
//    public static String formatPrice (Context context, double price,
//                                      int commodityNumber) {
//        MyApplication myapp = (MyApplication) context.getApplicationContext();
//        PriceCommodityEntity entity = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getCommodityByNumber(
//                commodityNumber);
//        return keepPrecision(price, entity.getSpread()) + "";
//    }
        viewHolder.tv_2.setText(PriceUtil.formatPrice(mContext,Double.valueOf(mDatas.get(position).getBuy()),mDatas.get(position).getNumber()));
        viewHolder.tv_3.setText(mDatas.get(position).getCha());
        viewHolder.tv_4.setText(mDatas.get(position).getBaifenbi()+"%");
        viewHolder.img_arrow.setVisibility(View.VISIBLE);
        if (Double.valueOf(viewHolder.tv_3.getText().toString()) > 0) {
            viewHolder.img_arrow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.arrow_up_red));
            viewHolder.tv_2.setTextColor(mContext.getResources().getColor(R.color.home_button_red));
            viewHolder.tv_3.setTextColor(mContext.getResources().getColor(R.color.home_button_red));
            viewHolder.tv_4.setTextColor(mContext.getResources().getColor(R.color.home_button_red));

        } else if (Double.valueOf(viewHolder.tv_3.getText().toString()) < 0) {
            viewHolder.img_arrow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.arrow_down_green));
            viewHolder.tv_2.setTextColor(mContext.getResources().getColor(R.color.home_function_button_die));
            viewHolder.tv_3.setTextColor(mContext.getResources().getColor(R.color.home_function_button_die));
            viewHolder.tv_4.setTextColor(mContext.getResources().getColor(R.color.home_function_button_die));
        } else {
            viewHolder.tv_2.setTextColor(mContext.getResources().getSystem().getColor(android.R.color.tertiary_text_light));
            viewHolder.tv_3.setTextColor(mContext.getResources().getColor(android.R.color.tertiary_text_light));
            viewHolder.tv_4.setTextColor(mContext.getResources().getColor(android.R.color.tertiary_text_light));
            viewHolder.img_arrow.setVisibility(View.INVISIBLE);

        }
        return convertView;
    }

    private class ViewHolder {

        TextView tv_1, tv_2, tv_3, tv_4;
        ImageView img_arrow;
    }

}