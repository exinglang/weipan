package com.puxtech.weipan.wxapi;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.data.BaseResponseData;
import com.puxtech.weipan.network.Logger;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    public static final String END_WEIXIN_LOGIN="end_weixin_login";

    protected IWXAPI api;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, MyApplication.WX_APP_ID, false);
        context = this;
        System.out.println("GET======INTENT");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Logger.i("WXEntry======getType" + req.getType());
        switch (req.getType()) {

            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                Logger.i("WXEntry======COMMAND_GETMESSAGE_FROM_WX");
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                Logger.i("WXEntry======COMMAND_SHOWMESSAGE_FROM_WX");
                break;
            default:
                break;
        }
    }

    @Override
    public void onResp(BaseResp resp) {
        String result = "";
        Logger.i("WXEntry======respresp");
        System.out.println("WXEntry======respresp");
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (resp.transaction == null) {
                    String code = ((SendAuth.Resp) resp).code;
                    String wxResult = "";//微信返回的数据
                    if (code != null) {
//                        Toast.makeText(this, "成功", Toast.LENGTH_LONG).show();
//                        finish();
                        ;
                       // WeiXinLogin weiXinLogin = new WeiXinLogin(this);
                        //	String weixin_getAccess_tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+MyApplication.WX_APP_ID+"&secret="+MyApplication.WX_APP_SECRET+"&code="+code+"&grant_type=authorization_code";
                        //weiXinLogin.new WeiXinGetTokenAsync().execute(code);
                        WeiXinLoginAsyncTask weiXinLoginAsyncTask = new WeiXinLoginAsyncTask(context);
                        weiXinLoginAsyncTask.new WeiXinGetTokenAsync().execute(code);
                    } else {
                        wxResult = "没有权限";
                    }
                    result = wxResult;
                } else {
                    result = "resp.transaction ==null";
                }
                //returnMessage(result);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "用户取消";
                returnMessage(result);
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "操作失败，没有权限";
                returnMessage(result);
                break;
            default:
                result = "操作失败，未知错误";
                returnMessage(result);
                break;
        }
        /*Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		finish();*/
    }

    public void returnMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        finish();
    }

    public void returnUnionID(String UnionID) {
        Intent intent = new Intent("toLoginActivityUnionID");
        intent.putExtra("UnionID", UnionID);
        System.out.println("returnUnionID--" + intent.getStringExtra("UnionID"));
        sendBroadcast(intent);
        finish();
    }
//    public void returnResult(String message){
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//        finish();
//    }

    public void returnResult(BaseResponseData responseData) {
        Intent intent =new Intent(END_WEIXIN_LOGIN);

        sendBroadcast(intent);


        Toast.makeText(this, responseData.getRetMessage() + responseData.getRetCode(), Toast.LENGTH_LONG).show();
        if (responseData.getRetCode() == 0) {
            setResult(Constant.CODE_SUCCESS);
        } else {
            setResult(Constant.CODE_FAIL);
        }
        finish();
    }
}