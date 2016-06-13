package com.puxtech.weipan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.R;
import com.puxtech.weipan.helper.TradeHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabMoNiPanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabMoNiPanFragment#} factory method to
 * create an instance of this fragment.
 */
public class TabMoNiPanFragment extends TabPanFragment {

    private static TabMoNiPanFragment moNiFragment;

    public static TabMoNiPanFragment getInstance(Context context) {
        if (moNiFragment == null) {

            moNiFragment = new TabMoNiPanFragment(context);
        }
        return moNiFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //判断模拟盘开户--签约,是否成功,没成功,显示按钮
        if (new TradeHelper(context).hasMoNiContract()) {
            setOptionsMenuVisible(false);//已开户,不显示最上面的开户按钮
        }else{
            setOptionsMenuVisible(true);
        }

        onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_settings:
                        startActivity(new Intent(getActivity(),AutoLogin.class));
                        getActivity().finish();

                        break;
                }
                return true;

            }
        };
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    private TabMoNiPanFragment(Context context) {

        super(context);
        TITLE = new String[]{"委托下单", "持仓明细", "成交明细"};//标题
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
