package com.puxtech.weipan.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * 竖屏商品详情的ViewPager
 */
public class PortraitPriceDetailPagerAdapter extends PagerAdapter
{
	public List<View> mListViews;
	public Context context;

	public PortraitPriceDetailPagerAdapter(List<View> mListViews, Context context)
	{
		this.mListViews = mListViews;
		this.context=context;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2)
	{
		((ViewPager) arg0).removeView(mListViews.get(arg1));
	}

	@Override
	public void finishUpdate(View arg0)
	{
	}

	@Override
	public int getCount()
	{
		return mListViews.size();
	}

	@Override
	public Object instantiateItem(View collection, int position)
	{
		((ViewPager) collection).addView(mListViews.get(position), 0);
		return mListViews.get(position);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		return arg0 == (arg1);
	}

	@Override
	public Parcelable saveState()
	{
		return null;
	}

	@Override
	public void startUpdate(View arg0)
	{
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1)
	{
		// TODO Auto-generated method stub

	}
}
