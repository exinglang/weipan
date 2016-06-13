package com.puxtech.weipan.wxapi;

import android.content.Context;
import android.os.AsyncTask;


import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.data.entitydata.WeiXinAccessTokenEffective;
import com.puxtech.weipan.data.entitydata.WeiXinAccessTokenEntity;
import com.puxtech.weipan.data.entitydata.WeiXinGetUnionID;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class WeiXinLogin {
    private static final String QUFWQ_FAILURE = "请求服务器失败";
    private static final String HQ_ACCESS_TOKEN_FAILURE = "获取access_token返回错误";
    private static final String JX_ACCESS_TOKEN_FAILURE = "解析access_token失败";
    private static final String JX_ACCESS_TOKEN_EFFECTIVE_FAILURE = "解析access_token有效性失败";
    private static final String HQ_UNIONID_FAILURE = "获取UnionID失败";
    private static final String JX_UNIONID_FAILURE = "解析UnionID失败";
    private static final String SUCCESS = "操作成功";
    private String message = "";
    Context context;
//
//    public WeiXinLogin(Context context) {
//        this.context = context;
//    }
//    /*
//     * 微信为获取access_token请求服务器
//	 */
//
//    public String weiXinGetToken(String weixin_getAccess_tokenUrl) {
//        String result;
//        try {
//            URL url = new URL(weixin_getAccess_tokenUrl);
//            HttpURLConnection conn;
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setConnectTimeout(5000);
//            if (conn.getResponseCode() == 200) {
//                InputStream in = conn.getInputStream();
//                result = convertStreamToString(in);
//                return result;
//            } else {
//                result = QUFWQ_FAILURE;
//            }
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            result = QUFWQ_FAILURE;
//        }
//        return result;
//    }
//
//    /**
//     * 解析为获取access_token请求服务器返回的数据
//     * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
//     * 刷新或续期access_token使用
//     * https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
//     */
//    public WeiXinAccessTokenEntity resolveGetToken(String result) {
//        WeiXinAccessTokenEntity getTokenEntity = new WeiXinAccessTokenEntity();
//        try {
//            JSONObject json = new JSONObject(result);
//            if (!json.isNull("errcode")) {
//                getTokenEntity.setErrcode(json.getInt("errcode"));
//                getTokenEntity.setErrmsg(HQ_ACCESS_TOKEN_FAILURE);
//            } else {
//                getTokenEntity.setErrcode(0);
//                getTokenEntity.setAccess_token(json.getString("access_token"));
//                getTokenEntity.setExpires_in(json.getInt("expires_in"));
//                getTokenEntity.setRefresh_token(json.getString("refresh_token"));
//                getTokenEntity.setOpenid(json.getString("openid"));
//                getTokenEntity.setScope(json.getString("scope"));
//                getTokenEntity.setErrmsg(SUCCESS);
//            }
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            getTokenEntity.setErrcode(-1);
//            getTokenEntity.setErrmsg(JX_ACCESS_TOKEN_FAILURE);
//        }
//        return getTokenEntity;
//    }
//
//    /**
//     * 解析access_token是否有效返回的数据
//     * https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID
//     */
//
//    public WeiXinAccessTokenEffective resolveRefreshTokenEffective(String result) {
//        WeiXinAccessTokenEffective accessTokenEffective = new WeiXinAccessTokenEffective();
//        try {
//            JSONObject json = new JSONObject(result);
//            accessTokenEffective.setErrcode(json.getInt("errcode"));
//            accessTokenEffective.setErrmsg(json.getString("errmsg"));
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            accessTokenEffective.setErrcode(-1);
//            accessTokenEffective.setErrmsg(JX_ACCESS_TOKEN_EFFECTIVE_FAILURE);
//        }
//        return accessTokenEffective;
//    }
//
//    /**
//     * 解析获取用户个人信息（UnionID机制）
//     * https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
//     */
//    public WeiXinGetUnionID resolveGetUnionID(String result) {
//        WeiXinGetUnionID getUnionID = new WeiXinGetUnionID();
//        try {
//            JSONObject json = new JSONObject(result);
//            if (!json.isNull("errcode")) {
//                getUnionID.setErrcode(json.getInt("errcode"));
//                getUnionID.setErrmsg(HQ_UNIONID_FAILURE);
//            } else {
//                getUnionID.setErrcode(0);
//                getUnionID.setOpenid(json.getString("openid"));
//                getUnionID.setNickname(json.getString("nickname"));
//                getUnionID.setSex(json.getInt("sex"));
//                getUnionID.setProvince(json.getString("province"));
//                getUnionID.setCity(json.getString("city"));
//                getUnionID.setCountry(json.getString("country"));
//                getUnionID.setHeadimgurl(json.getString("headimgurl"));
//                getUnionID.setUnionid(json.getString("unionid"));
//                getUnionID.setErrmsg(SUCCESS);
//            }
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            getUnionID.setErrcode(-1);
//            getUnionID.setErrmsg(JX_UNIONID_FAILURE);
//        }
//        return getUnionID;
//    }
//
//    /**
//     * 将返回的数据转化的String类型的
//     */
//    public static String convertStreamToString(InputStream is) throws IOException {
//        if (is != null) {
//            StringBuffer sb = new StringBuffer();
//            String line = null;
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//            is.close();
//            return sb.toString();
//        } else {
//            return "";
//        }
//    }
//
//    /**
//     * 请求access_token的异步线程
//     */
//    public class WeiXinGetTokenAsync extends AsyncTask<String, Void, String> {
//        HashMap<String, String> hm = new HashMap<>();
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... weixin_getAccess_tokenUrl) {
//            // TODO Auto-generated method stub
//            //通过code获取access_token
//            String wxResult_get_access_token = weiXinGetToken(weixin_getAccess_tokenUrl[0]);
//
//
//            if (wxResult_get_access_token != null && !wxResult_get_access_token.equals(QUFWQ_FAILURE)) {//如果请求access_token时返回为空或请求失败
//                WeiXinAccessTokenEntity getTokenEntity = resolveGetToken(wxResult_get_access_token);
//                //System.out.println("getTokenEntity.getErrcode---"+getTokenEntity.getErrcode());
//                if (getTokenEntity.getErrcode() == 0) {//请求access_token正确返回
//                    String weixin_adccess_token_effective_URL = "https://api.weixin.qq.com/sns/auth?access_token=" + getTokenEntity.getAccess_token() + "&openid=" + getTokenEntity.getOpenid();
//                    //检验授权凭证（access_token）是否有效
//                    String wxResult_effective = weiXinGetToken(weixin_adccess_token_effective_URL);
//                    if (wxResult_effective != null && !wxResult_effective.equals(QUFWQ_FAILURE)) {//如果判断access_token是否有效时返回为空或请求失败
//                        WeiXinAccessTokenEffective accessTokenEffective = resolveRefreshTokenEffective(wxResult_effective);
//                        String weixin_getUnionID = "";
//                        if (accessTokenEffective.getErrcode() == 0 && accessTokenEffective.getErrmsg().equals("ok")) {//判断access_token是否有效正确返回
//                            weixin_getUnionID = "https://api.weixin.qq.com/sns/userinfo?access_token=" + getTokenEntity.getAccess_token() + "&openid=" + getTokenEntity.getOpenid();
//                        } else if (accessTokenEffective.getErrcode() == 0 && !accessTokenEffective.getErrmsg().equals("ok")) {
//                            String refresh_weixin_getAccess_tokenUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + MyApplication.WX_APP_ID + "&grant_type=refresh_token" + "&refresh_token" + getTokenEntity.getRefresh_token();
//                            String wxResult_refresh = weiXinGetToken(refresh_weixin_getAccess_tokenUrl);
//                            if (wxResult_refresh != null && !wxResult_refresh.equals(QUFWQ_FAILURE)) {//如果刷新access_token时返回为空或请求失败
//                                //System.out.println("wxResult_refresh--"+wxResult_refresh);
//                                WeiXinAccessTokenEntity getTokenEntityEffective = resolveGetToken(wxResult_refresh);
//                                if (getTokenEntityEffective.getErrcode() == 0) {//刷新access_token时返回正确
//                                    weixin_getUnionID = "https://api.weixin.qq.com/sns/userinfo?access_token=" + getTokenEntityEffective.getAccess_token() + "&openid=" + getTokenEntityEffective.getOpenid();
//                                } else {
//                                    message = getTokenEntityEffective.getErrmsg();
//                                }
//                            } else {
//                                message = QUFWQ_FAILURE;
//                            }
//                        } else {
//                            message = accessTokenEffective.getErrmsg();
//                        }
//                        if (accessTokenEffective.getErrcode() == 0) {//请求UnionID返回正确的时候才获取UnionID
//                           // 获取用户个人信息（UnionID机制）
//                            String wxResult_getUnionID = weiXinGetToken(weixin_getUnionID);
//                            //System.out.println("wxResult_getUnionID---"+wxResult_getUnionID);
//                            if (wxResult_getUnionID != null && !wxResult_getUnionID.equals(QUFWQ_FAILURE)) {
//                                WeiXinGetUnionID getUnionID = resolveGetUnionID(wxResult_getUnionID);
//                                if (getUnionID.getErrcode() == 0) {
//                                    //System.out.println("getUnionID.getUnionid()====--"+getUnionID.getUnionid());
//                                    message = getUnionID.getUnionid();
//                                    hm.put(getUnionID.getErrmsg(), getUnionID.getUnionid());
//                                } else {
//                                    message = getUnionID.getErrmsg();
//                                }
//                            } else {
//                                message = QUFWQ_FAILURE;
//                            }
//                        }
//                    } else {
//                        message = QUFWQ_FAILURE;
//                    }
//                } else {
//                    message = getTokenEntity.getErrmsg();
//                }
//            } else {
//                message = QUFWQ_FAILURE;
//            }
//            return message;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            // TODO Auto-generated method stub
//            super.onPostExecute(result);
//            WXEntryActivity wxEntry = (WXEntryActivity) context;
//            if (hm.containsKey(SUCCESS)) {
//                //System.out.println("result---"+result);
//                wxEntry.returnMessage(SUCCESS);
//                wxEntry.returnUnionID(result);
//            } else {
//                wxEntry.returnMessage(result);
//            }
//        }
//    }
}
