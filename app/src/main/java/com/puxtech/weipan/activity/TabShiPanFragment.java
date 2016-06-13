package com.puxtech.weipan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.R;
import com.puxtech.weipan.helper.TradeHelper;


public class TabShiPanFragment extends TabPanFragment {
    private static TabShiPanFragment shiPanFragment;

    public static TabShiPanFragment getInstance(Context context) {
        if (shiPanFragment == null) {
            shiPanFragment =new TabShiPanFragment(context);
        }
        return shiPanFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (new TradeHelper(context).hasOpen()) {
            setOptionsMenuVisible(false);//已开户,不显示最上面的开户按钮
        }else{
            setOptionsMenuVisible(true);
        }

        onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem menuItem) {
                //  String msg = "";
                switch (menuItem.getItemId()) {
                    case R.id.action_settings:
                        gotoTargetPage(TabActivity.fragment_geren, TabHomeFragment.KAI_HU);
                        break;
                }
                return true;
            }
        };

        if (getArguments() != null) {
            Bundle bd = getArguments();
            if (bd.get(Constant.TYPE).equals(TabHomeFragment.PING_CANG)) {
                pager.setCurrentItem(1);

            }
            if (bd.get(Constant.TYPE).equals(TabHomeFragment.HOME)) {
                pager.setCurrentItem(0);

            }

        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }
    private TabShiPanFragment(Context context) {
        super(context);
        TITLE = new String[]{"委托下单", "持仓明细", "成交明细" };//标题

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 跳转至对应页面
     */
    private void gotoTargetPage(int targetTabFragment,String targetAction) {
        Bundle bd = new Bundle();
        bd.putString(Constant.TYPE, targetAction);
        ((TabActivity) getActivity()).setCheck();
        ((TabActivity) getActivity()).changeFragment(targetTabFragment, bd);
    }
}
