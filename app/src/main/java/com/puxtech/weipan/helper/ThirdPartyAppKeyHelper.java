package com.puxtech.weipan.helper;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;


public class ThirdPartyAppKeyHelper {

    /**
     * 获取微信Appid
     * @return
     */
    public static String getWeiXinAppId(Context context){
        ApplicationInfo appInfo;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            // 从Manifest中取值
            String value = appInfo.metaData.getString("weixin_appid");
            return value;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String getWeiXinAppSecret(Context context){
        ApplicationInfo appInfo;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            // 从Manifest中取值
            String value = appInfo.metaData.getString("weixin_appsecret");
            return value;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取微博AppKey
     * @return
     */
    public static String getWeiBoAppKey(Context context){
        ApplicationInfo appInfo;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            // 从Manifest中取值
            String value = appInfo.metaData.getString("weibo_appkey");
            return value;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

}
