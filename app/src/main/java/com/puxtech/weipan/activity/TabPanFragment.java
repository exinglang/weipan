package com.puxtech.weipan.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.R;
import com.puxtech.weipan.adapter.ShiPanFragmentViewPagerAdapter;
import com.puxtech.weipan.helper.WidgetHelper;
import com.puxtech.weipan.util.BaseViewPager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class TabPanFragment extends BaseFragment {

    protected String[] TITLE;
    View fView;
    Context context;

    public Toolbar.OnMenuItemClickListener onMenuItemClick;

    protected TabPanFragment(Context context) {
        super();
        this.context = context;

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


//        if (fView != null) {
//            ViewGroup parent = (ViewGroup) fView.getParent();
//            if (parent != null)
//                parent.removeView(fView);
//        } else {
//            try {
        context = getActivity();
        fView = inflater.inflate(R.layout.toolbar_tablayout_viewpager, container, false);
        initTabLayout();
        Toolbar toolbar = (Toolbar) fView.findViewById(R.id.toolbar);
        WidgetHelper.initToolBar((BaseAppCompatActivity) getActivity(), toolbar);
        //setHasOptionsMenu(true);

        toolbar.setOnMenuItemClickListener(onMenuItemClick);
//            } catch (InflateException e) {
//                e.printStackTrace();
//            }
//
//        }

        return fView;
    }

    protected void setOptionsMenuVisible(boolean b) {
        setHasOptionsMenu(b);

    }

    /**
     * 初始化TABLAYOUT
     */
    private void initTabLayout() {
        ArrayList<Fragment> fgList = getFragmentList();
        //加载四个页面
        TabLayout mTabLayout = (TabLayout) fView.findViewById(R.id.tl);
        FragmentPagerAdapter adapter = new ShiPanFragmentViewPagerAdapter(getChildFragmentManager(), fgList, TITLE);
        pager = (BaseViewPager) fView.findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setScrollable(false);
        pager.setOffscreenPageLimit(4);


        mTabLayout.setupWithViewPager(pager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(adapter);//给Tabs设置适配器
    }

    BaseViewPager pager;
    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;
   // Fragment fragment4;

    /**
     * 将framgment放到List中
     *
     * @return 放好的LIST
     */
    private ArrayList<Fragment> getFragmentList() {
        ArrayList<Fragment> fgList = new ArrayList<>();

         fragment1 = new PanFragmentWeiTuoXiaDan(context);
        fragment2 = new PanFragmentChiCangMingXi(context);
        fragment3 = new PanFragmentChengJiaoMingXi(context);
     //   fragment4  =  new PanFragmentChengJiaoMingXi(context);
        fgList.add(fragment1);
        fgList.add(fragment2);
        fgList.add(fragment3);
      //  fgList.add(fragment4);
        return fgList;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.REQUEST_SUCCESS) {
            ((PanFragmentChiCangMingXi) fragment2).onMActivityResult();//刷新持仓页面
            ((PanFragmentChengJiaoMingXi) fragment3).onMActivityResult();//刷新持仓页面
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


}
