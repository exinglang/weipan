package com.puxtech.weipan.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.R;
import com.puxtech.weipan.ResponseData.SysInfoDetailResponseData;
import com.puxtech.weipan.ResponseData.SysInfoResponseData;
import com.puxtech.weipan.adapter.GeRenSysInfoAdapter;
import com.puxtech.weipan.data.BaseResponseData;
import com.puxtech.weipan.data.SysInfoData;
import com.puxtech.weipan.data.SysInfoDetailData;
import com.puxtech.weipan.helper.WidgetHelper;
import com.puxtech.weipan.network.HttpManagerTrade;
import com.puxtech.weipan.util.ActivityUtils;

import java.util.ArrayList;

/**
 * Created by mac on 15/11/4.
 */
public class GeRenSysInfoDetailActivity extends BaseAppCompatActivity {


    Toolbar toolbar;
    GeRenSysInfoAdapter adapter;
    TextView tv_title,tv_content;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sysinfo_detail);

        InitWidget();
        WidgetHelper.initToolBarWithSubTitleAndFinishIcon(this, "系统消息详情", toolbar);


        Intent intent = this.getIntent();
        SysInfoDetailData detailData=(SysInfoDetailData)intent.getSerializableExtra(GeRenSysInfoActivity.CONTRACT_CHOOSE_ITEM_DATA);
        tv_title.setText(detailData.getTITLE());
        tv_content.setText(detailData.getCONTENT());
    }


    private void InitWidget() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_content = (TextView) findViewById(R.id.tv_content);

    }


}
