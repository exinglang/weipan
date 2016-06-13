package com.puxtech.weipan.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.ResponseData.AccessTokenResponseData;
import com.puxtech.weipan.ResponseData.UnionIDResponseData;
import com.puxtech.weipan.util.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zuochenyong on 15/11/4.
 */
public class HttpManagerWeiXin extends HttpManagerBase {

    public HttpManagerWeiXin(Context context) {
        super(context);

    }

    public HttpManagerWeiXin() {
        super();

    }

    public AccessTokenResponseData getAccessTokenByWXCodeRequest(String WXCode) {
        AccessTokenResponseData responseData = new AccessTokenResponseData();
        String weixin_getAccessToken_tokenUrl;
        try {
            weixin_getAccessToken_tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + MyApplication.WX_APP_ID + "&secret=" + MyApplication.WX_APP_SECRET + "&code=" + WXCode + "&grant_type=authorization_code";
            outPutRequestLog(weixin_getAccessToken_tokenUrl);
        } catch (Exception e) {
            createError(responseData, e);
            return responseData;
        }
        HttpSender httpSender = new HttpSender();
        String responseStr;
        try {
            responseStr = httpSender.getRequest(weixin_getAccessToken_tokenUrl);
        } catch (Exception e) {
            networkError(responseData, e);
            return responseData;
        }
        responseData.parse(responseStr);
        return responseData;
    }

    public UnionIDResponseData getUnionIdRequest(String access_token, String openId) {
        UnionIDResponseData responseData = new UnionIDResponseData();
        String weixin_getUnionID_url;
        try {
            weixin_getUnionID_url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openId;
            outPutRequestLog(weixin_getUnionID_url);
        } catch (Exception e) {
            createError(responseData, e);
            return responseData;
        }
        HttpSender httpSender = new HttpSender();
        String responseStr;
        try {
            responseStr = httpSender.getRequest(weixin_getUnionID_url);
        } catch (Exception e) {
            networkError(responseData, e);
            return responseData;
        }
        responseData.parse(responseStr);
        return responseData;
    }

    public void getHeadImgRequest(String headImgUrl) {
        UnionIDResponseData responseData = new UnionIDResponseData();

        try {

            outPutRequestLog("微信头像地址:" + headImgUrl);

            HttpSender httpSender = new HttpSender();

            InputStream inputStream = httpSender.getStreamFromURL(headImgUrl);

            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            File bitmapFile= FileUtils.createNewFile(context, Constant.WEI_XIN_DIR,Constant.WEI_XIN_HEAD_IMG);

            FileOutputStream fos;
            try {
                fos = new FileOutputStream(bitmapFile);
                bitmap.compress(Bitmap.CompressFormat.PNG,
                        100, fos);
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            unknowError(responseData, e);
        }


    }

//    String weixin_adccess_token_effective_URL = "https://api.weixin.qq.com/sns/auth?access_token=" + getTokenEntity.getAccess_token() + "&openid=" + getTokenEntity.getOpenid();

}
