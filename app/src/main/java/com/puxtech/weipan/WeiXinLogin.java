package com.puxtech.weipan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.puxtech.weipan.activity.AutoLogin;
import com.puxtech.weipan.activity.BaseActivity;
import com.puxtech.weipan.activity.Login;
import com.puxtech.weipan.util.ActivityUtils;
import com.puxtech.weipan.util.SharedPreferenceManager;
import com.puxtech.weipan.wxapi.WXEntryActivity;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


public class WeiXinLogin extends BaseActivity {
    IWXAPI api;
    WeXinLoginEndReceiver weXinLoginEndReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wei_xin_login);

        api = WXAPIFactory.createWXAPI(this, myapp.WX_APP_ID, true);
        ImageView rl_weibo = (ImageView) findViewById(R.id.rl_weibo);
        rl_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLogin(v);

            }
        });


        SharedPreferenceManager spf = new SharedPreferenceManager(context, SharedPreferenceManager.WEI_XIN);
        if (spf.contains(SharedPreferenceManager.WEI_XIN_UNION_ID)) {
            //有unionId,说明登陆过微信


        String   randdom = ActivityUtils.getRandomId();
//
//
//076630134211307035
//
//           spf.putString(context,SharedPreferenceManager.WEI_XIN_UNION_ID,randdom);

//            spf.getString(context, SharedPreferenceManager.WEI_XIN_UNION_ID, "");
            gotoLogin();

        }
        //注册微信登陆结束的广播
         weXinLoginEndReceiver=new WeXinLoginEndReceiver();
        registerReceiver(weXinLoginEndReceiver,new IntentFilter(WXEntryActivity.END_WEIXIN_LOGIN));
//        else{
//            weiXinLogin();
//        }
    }


    protected void requestLogin(View view) {
//        gotoLogin();
        if (api.getWXAppSupportAPI() == 0) {

            ActivityUtils.showSnackbar(context, view, "您没有微信客户端,请先下载微信!", "下载微信", new View.OnClickListener() {
                public void onClick(View v) {
                    Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("http://weixin.qq.com"));
                    it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                    startActivity(it);
                }
            });
        } else {

            weiXinLogin();


        }

    }

    private void weiXinLogin() {
        api.registerApp(myapp.WX_APP_ID);
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        api.sendReq(req);
    }

    //        startActivity(new Intent(this,Login.class));
//        finish();
    public class WeXinLoginEndReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WXEntryActivity.END_WEIXIN_LOGIN)) {
                gotoLogin();
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(weXinLoginEndReceiver);
    }

    private void gotoLogin() {
        startActivity(new Intent(this, AutoLogin.class));
        finish();
    }

}
