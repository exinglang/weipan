package com.puxtech.weipan.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * <b>Description:</b>
 * <p>
 * 不可滑动的viewpager
 * </p>
 *
 * @author fanshuo
 * @version V1.0
 * @Company 北京乐赢索思软件科技有限责任公司
 * @date 2014-2-13 上午10:18:50
 * @FileName CustomViewPager.java
 * @Package com.happyinsource.htjy.android.widget
 */
public class CustomViewPager extends ViewPager {

    private boolean isCanScroll = false;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

/*	@Override
    public void scrollTo(int x, int y) {
		if (isCanScroll) {
			super.scrollTo(x, y);
		}
	}*/

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
		/*if (!isCanScroll) {
			return false;
		}
		return super.onTouchEvent(arg0);*/
        return false;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
		/*if (!isCanScroll) {
			return false;
		}
		return super.onInterceptTouchEvent(arg0);*/
        return false;
    }

}
