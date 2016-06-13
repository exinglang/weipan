package com.puxtech.weipan.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.R;
import com.puxtech.weipan.adapter.ZhangHuPagerAdapter;
import com.puxtech.weipan.helper.WidgetHelper;

import java.util.ArrayList;

public class GeRenChongZhiActivity extends BaseAppCompatActivity {

    private String[] TITLE = new String[]{"资产信息", "充值提现", "历史记录"};//标题
    Toolbar toolbar;
    private Context context;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.toolbar_tablayout_viewpager);
        initWidget();
        WidgetHelper.initToolBarWithSubTitleAndFinishIcon(this, "充值提现", toolbar);
        initTabLayout();

        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().get(Constant.TYPE).equals(TabHomeFragment.CHONG_ZHI)) {
                    pager.setCurrentItem(1);//
                }
            }

        }
    }

    private void initWidget() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    /**
     * 初始化TABLAYOUT
     */
    private void initTabLayout() {
        ArrayList<Fragment> fgList = getFragmentList();
        //加载四个页面
        FragmentPagerAdapter adapter = new ZhangHuPagerAdapter(getSupportFragmentManager(), fgList, TITLE);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tl);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(pager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(adapter);//给Tabs设置适配器
    }

    /**
     * 将framgment放到List中
     *
     * @return 放好的LIST
     */
    private ArrayList<Fragment> getFragmentList() {
        ArrayList<Fragment> fgList = new ArrayList<>();
        Fragment fragment1 = new GeRenChongZhiAssetInfoFragment(context);
        Fragment fragment2 = new GeRenChongZhiInOutFragment(context);
        Fragment fragment3 = new GeRenChongZhiHistoryFragment(context);
        fgList.add(fragment1);
        fgList.add(fragment2);
        fgList.add(fragment3);

        return fgList;
    }

//    /**
//     * 开户成功后,点击"去签约",进入签约页面
//     */
//    public void finishOpenToInputContract() {
//        pager.setCurrentItem(1);
//       // pager.computeScroll();
//        pager.beginFakeDrag();
////        pager.
//        Intent intent = new Intent(context, ContrInputActivity.class);
//        startActivity(intent);
//    }
}

