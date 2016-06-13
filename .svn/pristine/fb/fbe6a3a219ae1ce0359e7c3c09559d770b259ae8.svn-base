package com.puxtech.weipan.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.puxtech.weipan.R;
import com.puxtech.weipan.data.AccountInfoData;
import com.puxtech.weipan.helper.OtherHelper;
import com.puxtech.weipan.helper.TradeHelper;

import java.util.Timer;
import java.util.TimerTask;


public class GeRenChongZhiAssetInfoFragment extends BaseFragment {
    // public static final int INPUT_OPEN_ACCOUNT = 2000;//开户
    TextView tv_qichuquanyi, tv_dangqianquanyi, tv_dangrichurujin,
            tv_shishiyinkui,
            tv_dangriyinkuiheji,
            tv_keyongzijin,
            tv_dongjiezijin,
            tv_yijiaodinjin,
            tv_dongjiedinjin,
            tv_dangrishouxufeiheji,
            tv_dongjieshouxufei,
            tv_fenxianlv;
    private Context context;
    private View view;
    Timer timer;
    TimerTask task;

    // TODO: Rename and change types and number of parameters
//    public static GeReCZTXAssetInfoFragment getInstance(Context context) {
//        if (homeFragment == null) {
//            homeFragment = new GeenCZTXAssetInfoFragment(context);
//        }
//        return homeFragment;
//    }

    public GeRenChongZhiAssetInfoFragment(Context context) {
        super();
        this.context = context;

    }

    public GeRenChongZhiAssetInfoFragment() {
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        } else {
            try {
                view = inflater.inflate(R.layout.geren_chongzhi_assetinfo_fragment, container, false);
                initWidget(view);

                if (myapp.getCurrentTradeEntity().getTradeData().getAccountInfoData() != null) {
                    refreshData();
                    new OtherHelper().startTimer(timer,
                            new TimerTask() {
                                public void run() {
                                    hd.sendEmptyMessage(0);
                                }
                            }
                    );
                }
            } catch (InflateException e) {
            }

        }
        return view;
    }


    private Handler hd = new Handler() {

        public void handleMessage(Message msg) {
            refreshData();
        }

    };

    @Override
    public void onDetach() {
        super.onDetach();
        OtherHelper otherHelper = new OtherHelper();
        otherHelper.stopTimer(timer, task);
    }

    /**
     * 刷新界面数据
     */
    private void refreshData() {
        AccountInfoData accountInfo = new TradeHelper(context).calSaveAccountInfo();

        tv_qichuquanyi.setText(accountInfo.getIB());
        tv_dangqianquanyi.setText(accountInfo.getDangqianquanyi());
        tv_dangrichurujin.setText(accountInfo.getIOF());
        tv_shishiyinkui.setText(accountInfo.getFlp());
        tv_dangriyinkuiheji.setText(accountInfo.getPL());
        tv_keyongzijin.setText(accountInfo.getKeyongbaozhenjin());
        tv_dongjiezijin.setText(accountInfo.getOR_F());
        tv_yijiaodinjin.setText(  accountInfo.getCM());
        tv_dongjiedinjin.setText(accountInfo.getOR_F_M());
        tv_dangrishouxufeiheji.setText(accountInfo.getFEE());
        tv_dongjieshouxufei.setText(accountInfo.getOR_F_F());
        tv_fenxianlv.setText(accountInfo.getFenxianlv());
    }

    private void initWidget(View view) {
        tv_qichuquanyi = (TextView) view.findViewById(R.id.tv_qichuquanyi);
        tv_dangqianquanyi = (TextView) view.findViewById(R.id.tv_dangqianquanyi);
        tv_dangrichurujin = (TextView) view.findViewById(R.id.tv_dangrichurujin);
        tv_shishiyinkui = (TextView) view.findViewById(R.id.tv_shishiyinkui);
        tv_dangriyinkuiheji = (TextView) view.findViewById(R.id.tv_dangriyinkuiheji);
        tv_keyongzijin = (TextView) view.findViewById(R.id.tv_keyongzijin);
        tv_dongjiezijin = (TextView) view.findViewById(R.id.tv_dongjiezijin);
        tv_yijiaodinjin = (TextView) view.findViewById(R.id.tv_yijiaodinjin);
        tv_dongjiedinjin = (TextView) view.findViewById(R.id.tv_dongjiedinjin);
        tv_dangrishouxufeiheji = (TextView) view.findViewById(R.id.tv_dangrishouxufeiheji);
        tv_dongjieshouxufei = (TextView) view.findViewById(R.id.tv_dongjieshouxufei);
        tv_fenxianlv = (TextView) view.findViewById(R.id.tv_fenxianlv);
    }


}
