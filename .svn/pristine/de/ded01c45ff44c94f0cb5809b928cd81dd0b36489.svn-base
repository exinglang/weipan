package com.puxtech.weipan.helper;

import android.content.Context;


import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.network.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <b>Description:</b>
 * <p>
 * 行情链路辅助类
 * </p>
 *
 * @Company 北京乐赢索思软件科技有限责任公司
 * @author fanshuo
 * @date 2014年6月4日 上午9:34:45
 */
public class PriceLinkHelper {
    /**
     * 目录服务器返回的所有链路
     */
    List<String> allLink;
    /**
     * 需要调用者主动记录已经失败的链路，成功时会清空
     */
    List<String> failedLink;
    MyApplication myApplication;
    public PriceLinkHelper(Context context) {
        super();
        myApplication=(MyApplication)context.getApplicationContext();
        allLink = new ArrayList<>();
        failedLink = new ArrayList<>();
        initAllLink(context);
    }
    // ////////////////////////////////////////////private method
    /**
     * 初始化所有链路url
     */
    private void initAllLink(Context context){
//        MyApplication mApplication = (MyApplication) context.getApplicationContext();
//        List<ContentsServerServiceEntity> serviceList = mApplication
//                .getContentsServerEntity().getYwSystemEntity().getServiceList();
//        for (ContentsServerServiceEntity serviceEntity : serviceList) {
//            // 行情服务前置机地址
//            if (serviceEntity.getType() == 1) {
//                for (ContentsServerZuEntity zuEntity : serviceEntity.getZuList()) {
//                    if(zuEntity.getId() == zuId){
//                        List<ContentsServerLlEntity> llList = zuEntity.getLlList();
//                        //把所有链路url添加到此类的list中
//                        for (ContentsServerLlEntity llEntity : llList) {
//                            allLink.add(llEntity.getUrl());
//                        }
//                    }
//                }
//            }
//        }
    }

    // ////////////////////////////////////////////public method

    /**
     * 随机取一个链路
     */
    public String getLinkRandom() {
        int size = allLink.size();
        int random = new Random().nextInt(size);
        return allLink.get(random);
    }

    /**
     * 获取当前行情前置机地址
     */
    public String getCurrentUrl(){

        return myApplication.getCurrentTradeEntity().getPriceData().getPRICE_URL();
//        return Constant.PRICE_SERVER_URL_MAP.get(zuId);
    }

    /**
     * 得到一个没有失败过的链路，如果没有了就返回null
     */
    public String switchLink() {
//		先增加失败链路
        addFailedLink(getCurrentUrl());
        // 除去失败过的链路，从剩下的里面随机取一个
        List<String> availableLink = new ArrayList<>();
        for (String link : allLink) {
            if (failedLink.contains(link)) {
                continue;
            }
            availableLink.add(link);
        }
        if (availableLink.size() == 0) {
            return null;
        }
        // 随机取一个
        int size = availableLink.size();
        int random = new Random().nextInt(size);
        String url = availableLink.get(random);
        //刷新当前行情服务器地址
        if(url != null)
            Constant.PRICE_SERVER_URL_MAP=url;

        return url;
    }

    /**
     * 添加已经失败的链路
     */
    public void addFailedLink(String url){
        this.failedLink.add(url);
        Logger.i("已添加失败链路：" + url);
    }

    public void clearFailedLink(){
        if(failedLink.size() > 0){
            Logger.i("清除已失败链路：count = " + failedLink.size());
            this.failedLink = new ArrayList<    >();
        }
    }

    // ///////////////////////////////////////////////////getter and setter

    public List<String> getAllLink() {
        return allLink;
    }

    public void setAllLink(List<String> allLink) {
        this.allLink = allLink;
    }

    public List<String> getFailedLink() {
        return failedLink;
    }

    public void setFailedLink(List<String> failedLink) {
        this.failedLink = failedLink;
    }

}
