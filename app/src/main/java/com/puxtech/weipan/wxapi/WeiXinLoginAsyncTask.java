package com.puxtech.weipan.wxapi;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.ResponseData.AccessTokenResponseData;
import com.puxtech.weipan.ResponseData.UnionIDResponseData;
import com.puxtech.weipan.data.BaseResponseData;
import com.puxtech.weipan.data.entitydata.WeiXinAccessTokenEntity;
import com.puxtech.weipan.network.HttpManagerWeiXin;
import com.puxtech.weipan.util.SharedPreferenceManager;

public class WeiXinLoginAsyncTask {
    Context context;
    MyApplication myapp;
    public WeiXinLoginAsyncTask(Context context) {
        this.context = context;
        myapp=(MyApplication)context.getApplicationContext();
    }

    /**
     * 请求access_token和openId的异步线程
     */
    public class WeiXinGetTokenAsync extends AsyncTask<String, Void, String> {
        HttpManagerWeiXin httpManagerWeiXin;
        BaseResponseData responseData;

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... weixin_get_code) {
            try {
                httpManagerWeiXin = new HttpManagerWeiXin(context);
                responseData = httpManagerWeiXin.getAccessTokenByWXCodeRequest(weixin_get_code[0]);
                if (responseData.getRetCode() == 0) {
                    AccessTokenResponseData accessTokenResponseData=(AccessTokenResponseData) responseData;
                    //先存储accessTokenEntity;
                    myapp.getWeiXinEntity().setWeiXinAccessTokenEntity(accessTokenResponseData.getGetTokenEntity());
                    WeiXinAccessTokenEntity accessTokenEntity = accessTokenResponseData.getGetTokenEntity();


                    responseData = httpManagerWeiXin.getUnionIdRequest(accessTokenEntity.getAccess_token(), accessTokenEntity.getOpenid());
                }
                if (responseData.getRetCode() == 0) {
                    responseData.setRetMessage("微信授权登陆成功");
                    UnionIDResponseData unionIDResponseData=(UnionIDResponseData) responseData;
                    myapp.getWeiXinEntity().setWeiXinGetUnionID(unionIDResponseData.getGetUnionIDEntity());
                    //保存unionId到本地
                    SharedPreferenceManager spf = new SharedPreferenceManager(context, SharedPreferenceManager.WEI_XIN);
                    spf.putString(context,SharedPreferenceManager.WEI_XIN_UNION_ID,unionIDResponseData.getGetUnionIDEntity().getUnionid());
                    spf.putString(context,SharedPreferenceManager.WEI_XIN_NICK_NAME,unionIDResponseData.getGetUnionIDEntity().getNickname());



                    httpManagerWeiXin.getHeadImgRequest(unionIDResponseData.getGetUnionIDEntity().getHeadimgurl());

                }
            } catch (Exception e) {
                e.printStackTrace();
                responseData.setRetCode(Constant.CODE_ERROR_UNKNOW);
                responseData.setRetMessage(Constant.MESSAGE_ERROR_UNKNOW);
            }
            return "";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute("");
            WXEntryActivity wxEntry = (WXEntryActivity) context;
            wxEntry.returnResult(responseData);
           // if (0 == responseData.getRetCode()) {
              //  ActivityUtils.showCenterToast(context, "协议返回成功并解析成功", 2000);

              //  wxEntry.returnUnionID(result);
//            } else {
//                wxEntry.returnMessage(responseData.getRetMessage());
           // }
        }

    }


}
