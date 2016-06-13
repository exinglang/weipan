package com.puxtech.weipan.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;


import com.puxtech.weipan.activity.PanFragmentWeiTuoXiaDan;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.service.PriceService;

import java.util.ArrayList;

/**
 * <b>Description:</b>
 * <p>
 * 最新价格广播接收器

 */
public class PriceBroadCastReceiver extends BroadcastReceiver {

	

	private Handler handler;

	public PriceBroadCastReceiver(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(PanFragmentWeiTuoXiaDan.ACTION_ALL_COMMODITY_PRICE)) {
			ArrayList<PriceEntity> allCommodityPrice = intent
					.getParcelableArrayListExtra(PriceService.KEY_ALL_COMMODITY_PRICE);
			handler.obtainMessage(PanFragmentWeiTuoXiaDan.MESSAGE_COMMODITY_PRICE, allCommodityPrice)
					.sendToTarget();

		}
	}

}
