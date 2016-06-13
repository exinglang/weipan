package com.puxtech.weipan.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * 屏幕尺寸相关工具类<br>
 * 
 * @author fanshuo
 *
 */
public class ScreenSizeUtil {

	private static DisplayMetrics metrics;

	/**
	 * 根据绝对尺寸得到相对尺寸，在不同的分辨率设备上有一致的显示效果, dip->pix
	 * 
	 * @param context
	 * @param
	 *            绝对尺寸
	 * @return
	 */
	public static int getSizeByGivenAbsSize(Context context, int givenAbsSize) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				givenAbsSize, context.getResources().getDisplayMetrics());
	}

	private static DisplayMetrics getDisplayMetrics(Context context) {
		if (metrics != null) {
			return metrics;
		}
		metrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		return metrics;
	}

	public static int getScreenWidth(Context context) {
		return getDisplayMetrics(context).widthPixels; // 屏幕宽度（像素）
	}

	public static int getScreenHeight(Context context) {
		return getDisplayMetrics(context).heightPixels;// 屏幕高度（像素）
	}

	public static float getScreenDensity(Context context) {
		return getDisplayMetrics(context).density; // 屏幕密度（0.75 / 1.0 / 1.5）
	}

	public static int getScreenDensityDpi(Context context) {
		return getDisplayMetrics(context).densityDpi;// 屏幕密度DPI（120 / 160 / 240）
	}

	/**
	 * 把dp转换成px
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int Dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	/**
	 * 把px转换成dp
	 * @param context
	 * @param px
	 * @return
	 */
	public static int Px2Dp(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}
	
	/**
	 * 
	 */
	public static Integer getStatusBarHeight(Activity activity){
		WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
	  	DisplayMetrics metrics = new DisplayMetrics();
			windowManager.getDefaultDisplay().getMetrics(metrics);
			Rect rectgle = new Rect();
			activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rectgle);
			int statusBarHeight = rectgle.top;
			if (0 == statusBarHeight) {
				Class<?> localClass;
				try {
					localClass = Class.forName("com.android.internal.R$dimen");
					Object localObject = localClass.newInstance();
					int i5 = Integer.parseInt(localClass
							.getField("status_bar_height").get(localObject)
							.toString());
					statusBarHeight = activity.getResources().getDimensionPixelSize(i5);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
			}
			return statusBarHeight;

	}
}
