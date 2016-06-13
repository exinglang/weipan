package com.puxtech.weipan.helper;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

public class WDMXHelper {
	// 根据商品类型获得对应的类
	/**
	 * 
	 * 
	 * @param commodityType
	 * @param ctx
	 * @param b 是否是竖屏
	 * @param layout_top_line1  购入价,售出价
	 * @return
	 */
	public static IWuDangMingxi getIW(int commodityType, Context ctx,
			boolean b, LinearLayout layout_top_line1) {
		IWuDangMingxi wm = null;
		if (commodityType == 2 || commodityType == 5) {
			wm = new MingXi(ctx);
		} else if (commodityType == 4) {
			wm = new WuDangMingXi(ctx);

		} else {
			wm = new NoWuDangMi(ctx);
		}
		if (b) {
			if (commodityType == 2 || commodityType == 5) {
				layout_top_line1.setVisibility(View.VISIBLE);
			} else if (commodityType == 4) {
				layout_top_line1.setVisibility(View.GONE);

			} else {
				layout_top_line1.setVisibility(View.VISIBLE);
			}
		}
		return wm;
	}

	/**
	 * 
	 * @param commodityType
	 * @param pvd
	 * @return
	 */
	public static float frameEndXPortrait(int commodityType, PriceViewData pvd) {
		if (commodityType == 2 || commodityType == 5 || commodityType == 4) {
			return (pvd.getLineViewWidthPortrait()) / 3 * 2;
		}
		return pvd.getLineViewWidthPortrait();
	}

	public static float frameEndYPortrait(int commodityType,float getHeight, int textSize, float  margin) {
		float r = getHeight - (textSize + margin) ;
		if ( commodityType == 4) {
			return r/ 4 * 3;
		}
		return r;
	}


	/**
	 * 获取横屏五档明细 的宽度
	 * 
	 * @param commodityType
	 *            商品类型
	 * @param pvd
	 * @return
	 */
	public static float LandScape_WuDangMingXiWidth(int commodityType,
			PriceViewData pvd) {
		if (commodityType == 2 || commodityType == 5 || commodityType == 4) {
			return pvd.getLineViewWidthPortrait() / 3;
		} else {
			return 0.0f;
		}
	}

	/*
	 * public static float frameEndYLandscape(int commodityType,PriceRuntimeData
	 * pvd,float margin){
	 * if(commodityType==2||commodityType==5||commodityType==4){ return
	 * pvd.getLineViewHeightLandscape()-margin; } return
	 * pvd.getLineViewHeightLandscape(); }
	 */

}
