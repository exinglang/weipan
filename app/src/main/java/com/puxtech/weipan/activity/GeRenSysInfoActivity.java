package com.puxtech.weipan.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.ResponseData.SysInfoDetailResponseData;
import com.puxtech.weipan.ResponseData.SysInfoResponseData;
import com.puxtech.weipan.adapter.GeRenSysInfoAdapter;
import com.puxtech.weipan.data.BaseResponseData;
import com.puxtech.weipan.data.BaseResponseDataOpenAccountContract;
import com.puxtech.weipan.data.ContractChooseItemResponseData;
import com.puxtech.weipan.data.SysInfoData;
import com.puxtech.weipan.helper.WidgetHelper;
import com.puxtech.weipan.network.HttpManagerOpenAccountContract;
import com.puxtech.weipan.network.HttpManagerTrade;
import com.puxtech.weipan.util.ActivityUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mac on 15/11/4.
 */
public class GeRenSysInfoActivity extends BaseAppCompatActivity {
    public static final int TYPE_SYSINFO_LIST = 1;//
    public static final int TYPE_SYSINFO_DETAIL = 2;//
    public static final String CONTRACT_CHOOSE_ITEM_DATA = "CONTRACT_CHOOSE_ITEM_DATA";//


    ListView lv_main;
    Toolbar toolbar;
    String sysinfoId;
    GeRenSysInfoAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sysinfo);

        InitWidget();
        WidgetHelper.initToolBarWithSubTitleAndFinishIcon(this, "系统消息", toolbar);
        requestSysInfo(TYPE_SYSINFO_LIST);

//        lv_main.setOnClickListener(new View.OnClickListener() {
//                                       @Override
//                                       public void onClick(View v) {
//
//                                       }
//                                   }
//        );

    }

    private void requestSysInfo(final int type) {
        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
                         BaseResponseData responseData;
                         Dialog dialog;
                         HttpManagerTrade httpManager = new HttpManagerTrade(context,myapp.getShipanTradeEntity());

                         protected void onPreExecute() {
                             super.onPreExecute();
                             dialog = ActivityUtils.showLoadingProgressDialog(context, "");
                         }

                         @SuppressWarnings("ResourceType")
                         protected Boolean doInBackground(Void... params) {
                             try {

                                 if (type == TYPE_SYSINFO_LIST) {
                                     responseData = httpManager.requestSysInfo("1","");
                                 } else if (type == TYPE_SYSINFO_DETAIL) {
                                     responseData = httpManager.requestSysInfoDetail(sysinfoId);
                                 }


                             } catch (Exception e) {
                                 e.printStackTrace();
                                 responseData.setRetCode(Constant.CODE_ERROR_UNKNOW);
                                 responseData.setRetMessage(Constant.MESSAGE_ERROR_UNKNOW);
                             }

                             return true;
                         }

                         @Override
                         protected void onPostExecute(Boolean result) {
                             super.onPostExecute(result);
                             dialog.dismiss();
                             if (0 == responseData.getRetCode()) {

                                 if (type == TYPE_SYSINFO_LIST) {

                                     SysInfoResponseData sysInfoResponseData = (SysInfoResponseData) responseData;
                                     if (sysInfoResponseData.recordNumber == 0) {
                                         ActivityUtils.showCenterToast(context, "没有新公告", Toast.LENGTH_SHORT);
                                     } else {
                                         adapter = new GeRenSysInfoAdapter(context, sysInfoResponseData.dataList);
                                         lv_main.setAdapter(adapter);
                                         lv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                 SysInfoData data = adapter.getItem(position);
                                                 sysinfoId = data.getID();
                                                 requestSysInfo(TYPE_SYSINFO_DETAIL);
                                             }
                                         });
                                     }
                                 } else if (type == TYPE_SYSINFO_DETAIL) {
                                     SysInfoDetailResponseData sysInfoResponseData = (SysInfoDetailResponseData) responseData;


                                     Intent intent = new Intent();
                                     intent.setClass(context, GeRenSysInfoDetailActivity.class);
                                     Bundle bundle = new Bundle();
                                     bundle.putSerializable(CONTRACT_CHOOSE_ITEM_DATA, sysInfoResponseData.getDetailData());
                                     intent.putExtras(bundle);
                                     startActivity(intent);
                                 }
                             } else {
                                 ActivityUtils.showCenterToast(context, responseData.getRetMessage() + "(" + responseData.getRetCode() + ")", Toast.LENGTH_LONG);

                             }

                         }

                     }

        );
    }

    private void InitWidget() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lv_main = (ListView) findViewById(R.id.lv_main);

    }


}
