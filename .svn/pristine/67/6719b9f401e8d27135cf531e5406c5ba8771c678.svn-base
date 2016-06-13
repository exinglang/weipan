package com.puxtech.weipan.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.puxtech.weipan.data.AllCommodityPriceResponseData;
import com.puxtech.weipan.data.CommodityInfoResponseData;
import com.puxtech.weipan.data.EnvTradeTimeResponseData;
import com.puxtech.weipan.data.KLineAndZipUrlResponseData;
import com.puxtech.weipan.data.KLineCycleResponseData;
import com.puxtech.weipan.data.KLineFromZipUrlResponseData;
import com.puxtech.weipan.data.KLineOneResponseData;
import com.puxtech.weipan.data.MarketInfoResponseData;
import com.puxtech.weipan.data.PriceLogonResponseData;
import com.puxtech.weipan.data.PriceSercetListResponseData;
import com.puxtech.weipan.data.ProtocolResponseData;
import com.puxtech.weipan.data.TimeLineDaysResponseData;
import com.puxtech.weipan.data.TimeLineOneResponseData;
import com.puxtech.weipan.data.entitydata.EnvironmentEntity;
import com.puxtech.weipan.data.entitydata.KLineEntity;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.helper.PriceLinkHelper;
import com.puxtech.weipan.util.AES;
import com.puxtech.weipan.util.Algorithm;
import com.puxtech.weipan.util.BytesConverter;
import com.puxtech.weipan.util.MD5;
import com.puxtech.weipan.util.PriceUtil;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;

/**
 * Created by zuochenyong on 15/11/4.
 */
public class HttpManagerPrice extends HttpManagerBase {

    public HttpManagerPrice(Context context) {
        super(context);
    }

    public HttpManagerPrice() {
        super();
    }
    //协议版本
    public ProtocolResponseData protocolRequest() {
        ProtocolResponseData responseData = new ProtocolResponseData();
        ByteBuffer requestData;
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1);
            buffer.put((byte) 1);
            requestData =buffer;
        } catch (Exception e) {
            createError(responseData, e);
            return responseData;
        }
        byte[] retData;

        try {
            retData = requestBinaryAndChangeAvailableLink( requestData);
        } catch (Exception e) {
            networkError(responseData, e);
            return responseData;
        }
        responseData.convertProtocol(myapp, retData);
        return responseData;
    }

    //登录协议
    public PriceLogonResponseData priceLogonRequest() {
        PriceLogonResponseData responseData = new PriceLogonResponseData();
        ByteBuffer requestData;
        try {
            String version = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getProtocolInfo().getVersion();
            byte[] versionBytes = version.getBytes();
            byte[] key = Algorithm.GenerateGUID();
            String randomString = Algorithm.randomString(6);
            byte[] random = BytesConverter.getBytes(randomString);
            byte[] random1 = MD5.encrypt(random, key);// 加密后的随机数

            String userName = "android";
            String pwd = "123456";
            String memberId = "memberAndroid";
            String domain = "KPI";
            byte[] pwdBytes = BytesConverter.getBytes(pwd);
            pwdBytes = MD5.Md5(pwdBytes);
            byte[] userNameBytes = userName.getBytes();
            byte[] domainBytes = domain.getBytes();
            byte[] memberIdBytes = memberId.getBytes();

            ByteBuffer uerInfoBuffer = ByteBuffer.allocate(16 + pwdBytes.length
                    + userNameBytes.length + domainBytes.length
                    + memberIdBytes.length);
            uerInfoBuffer.putInt(userNameBytes.length);
            uerInfoBuffer.put(userNameBytes);
            uerInfoBuffer.putInt(domainBytes.length);
            uerInfoBuffer.put(domainBytes);
            uerInfoBuffer.putInt(pwdBytes.length);
            uerInfoBuffer.put(pwdBytes);
            uerInfoBuffer.putInt(memberIdBytes.length);
            uerInfoBuffer.put(memberIdBytes);
            byte[] userInfo = uerInfoBuffer.array();
            userInfo = MD5.encrypt(userInfo, key);

            ByteBuffer buffer = ByteBuffer
                    .allocate(10 + versionBytes.length + key.length + random.length
                            + random1.length + userInfo.length);
            buffer.put((byte) 15);
            buffer.put((byte) versionBytes.length);
            buffer.put(versionBytes);
            buffer.put((byte) 1);
            buffer.put((byte) key.length);
            buffer.put(key);
            buffer.put((byte) random.length);
            buffer.put(random);
            buffer.put((byte) random1.length);
            buffer.put(random1);

            buffer.putInt(userInfo.length);
            buffer.put(userInfo);
            requestData =buffer;
        } catch (Exception e) {
            createError(responseData, e);
            return responseData;
        }
        byte[] retData;
        try {
            retData = requestBinaryAndChangeAvailableLink(requestData);
        } catch (Exception e) {
            networkError(responseData, e);
            return responseData;
        }
        responseData.convertPriceLogon(retData);
        return responseData;
    }

    //k线周期
    public KLineCycleResponseData KLineCycleRequest() {
        KLineCycleResponseData responseData = new KLineCycleResponseData();
        ByteBuffer requestData;
        try {
            byte[] head = getHead(11);
            ByteBuffer buffer = ByteBuffer.allocate(head.length);
            buffer.put(head);
            requestData = buffer;
        } catch (Exception e) {
            createError(responseData, e);
            return responseData;
        }
        byte[] retData;
        try {
            retData = requestBinaryAndChangeAvailableLink( requestData);
        } catch (Exception e) {
            networkError(responseData, e);
            return responseData;
        }
        responseData.convertKLineCycle(retData);
        return responseData;
    }

    //市场列表
    public MarketInfoResponseData marketInfoRequest() {
        MarketInfoResponseData responseData = new MarketInfoResponseData();
        ByteBuffer requestData;
        try {
            byte[] head = getHead(0x22);
            ByteBuffer buffer = ByteBuffer.allocate(head.length);
            buffer.put(head);
            requestData = buffer;
        } catch (Exception e) {
            createError(responseData, e);
            return responseData;
        }
        byte[] retData;
        try {
            retData = requestBinaryAndChangeAvailableLink(requestData);
        } catch (Exception e) {
            networkError(responseData, e);
            return responseData;
        }
        responseData.convertMarketInfo(retData);
        return responseData;
    }

    /**
     * 获取商品信息
     *
     * @param
     * @param entity 盘id
     * @return
     */

    public CommodityInfoResponseData commodityInfoRequest( EnvironmentEntity entity) {
        CommodityInfoResponseData responseData = new CommodityInfoResponseData();
        ByteBuffer requestData;
        try {
            byte[] head = getHead(0x23);
            ByteBuffer buffer = ByteBuffer.allocate(head.length + 4);
            buffer.put(head);
            buffer.putShort((short) entity.getMarketId());
            buffer.putShort((short) entity.getId());
            requestData = buffer;
        } catch (Exception e) {
            createError(responseData, e);
            return responseData;
        }
        byte[] retData;
        try {
            retData = requestBinaryAndChangeAvailableLink( requestData);
        } catch (Exception e) {
            networkError(responseData, e);
            return responseData;
        }
        responseData.convertCommodityInfo(retData);
        return responseData;
    }

    /**
     * 同步获取所有商品的最新一口价
     */
    public AllCommodityPriceResponseData getAllCommodityPriceUseSyncMode(
            EnvironmentEntity envir) {
        return getAllCommodityPriceUseSyncModeBase(envir);
    }


    /**
     * 同步获取所有商品的最新一口价
     */


    public AllCommodityPriceResponseData getAllCommodityPriceUseSyncModeBase(EnvironmentEntity entity) {
        AllCommodityPriceResponseData responseData = new AllCommodityPriceResponseData();
        ByteBuffer requestData;
        try {
            byte[] head = getHead(0x25);
            ByteBuffer buffer = ByteBuffer.allocate(head.length + 4);
            buffer.put(head);
            buffer.putShort((short) entity.getMarketId());
            buffer.putShort((short) entity.getId());

            requestData =buffer;
        } catch (Exception e) {
            createError(responseData, e);
            return responseData;
        }
        byte[] retData;
        try {
            retData = requestBinaryAndChangeAvailableLink( requestData);
        } catch (Exception e) {
            networkError(responseData, e);
            return responseData;
        }
        responseData.convertAllCommodityPrice(myapp, retData);
        return responseData;
    }
    /**
     * 5.32 多日分时查询 协议v2
     */
//    public byte[] getTimeLineDays(
//                                  int commodityNumber, int marketNumber, int evnNumber, int zuId) {
//        byte[] head = sharedDataManager.getHead(context, 0x1A, zuId);
//        ByteBuffer buffer = ByteBuffer.allocate(head.length + 2 + 2 + 2);
//        buffer.put(head);
//        buffer.putShort((short) marketNumber);
//        buffer.putShort((short) evnNumber);
//        buffer.putShort((short) commodityNumber);
//
//
//            retData = priceHttpSender.requestBinary(buffer.array(), linkHelper.getCurrentUrl());
//
//
//        return retData;
    // }

    /**
     * 5.32 多日分时查询 协议v2
     */
    public TimeLineDaysResponseData getTimeLineDays(int commodityNumber, int marketNumber, int evnNumber) {
        TimeLineDaysResponseData responseData = new TimeLineDaysResponseData();
        ByteBuffer requestData;
        try {
            byte[] head = getHead(0x1A);
            ByteBuffer buffer = ByteBuffer.allocate(head.length + 2 + 2 + 2);
            buffer.put(head);
            buffer.putShort((short) marketNumber);
            buffer.putShort((short) evnNumber);
            buffer.putShort((short) commodityNumber);
            requestData = buffer;
        } catch (Exception e) {
            createError(responseData, e);
            return responseData;
        }
        byte[] retData;
        try {
            retData = requestBinaryAndChangeAvailableLink( requestData);
        } catch (Exception e) {
            networkError(responseData, e);
            return responseData;
        }
        responseData.convertTimeLineDays(retData);
        return responseData;
    }


    /**
     * 获取密钥，没有从服务器下载密钥列表并保存
     */
    public PriceSercetListResponseData getPriceSercetList() {
        //如果为空,从服务器获取

        PriceSercetListResponseData responseData = new PriceSercetListResponseData();
        ByteBuffer requestData;
        try {
            byte[] head = getHead(0x18);
            ByteBuffer buffer = ByteBuffer.allocate(head.length );
            buffer.put(head);
            requestData = buffer;
        } catch (Exception e) {
            createError(responseData, e);
            return responseData;
        }
        byte[] retData;
        try {
            retData = requestBinaryAndChangeAvailableLink( requestData);
        } catch (Exception e) {
            networkError(responseData, e);
            return responseData;
        }
        responseData.convertPriceSercetList(retData);
        return responseData;
    }

    /**
     * 获取密钥，没有从服务器下载密钥列表并保存
     */
    public EnvTradeTimeResponseData getEnvTradeTime(int marketNumber, int evnNumber) {


        EnvTradeTimeResponseData responseData = new EnvTradeTimeResponseData();
        ByteBuffer requestData;
        try {

            byte[] head = getHead(0x24);
            ByteBuffer buffer = ByteBuffer.allocate(head.length + 4);
            buffer.put(head);
            buffer.putShort((short)marketNumber);
            buffer.putShort((short)evnNumber);
            requestData = buffer;
        } catch (Exception e) {
            createError(responseData, e);
            return responseData;
        }
        byte[] retData;
        try {
            retData = requestBinaryAndChangeAvailableLink( requestData);
        } catch (Exception e) {
            networkError(responseData, e);
            return responseData;
        }
        responseData.convertEnvTradeTime(retData);
        return responseData;
    }


    /**
     * 同步获取分时数据，周期默认1分钟
     * <p>
     * <p>
     * 商品数量，0为取所有的
     *
     * @param commodityNumber 商品编号
     * @param cycle           周期
     * @param startTime       yyyyMMddHHmm,-1当前最后一个交易时间
     * @param direction       0为左 1为右
     * @param count           请求的个数，1322为一天总数
     */
    public TimeLineOneResponseData getTimeLineSync(PriceEntity pe,
                                                   int commodityNumber, short cycle,
                                                   long startTime, int direction, int count) {
        return getTimeLineSyncBase(pe,
                commodityNumber, cycle, startTime, direction,
                count);

    }

    /**
     * 同步获取分时数据，周期默认1分钟，兼容首次和重试
     */
    public TimeLineOneResponseData getTimeLineSyncBase(
            PriceEntity pe,
            int commodityNumber, short cycle, long startTime, int direction,
            int count) {

        TimeLineOneResponseData responseData = new TimeLineOneResponseData();
        ByteBuffer requestData;
        try {
            byte[] head = getHead( 0x20);
            ByteBuffer buffer = null;
            buffer = ByteBuffer.allocate(head.length + 2 + 2 + 2 + 2 + 8 + 1 + 4);
            buffer.put(head);
            buffer.putShort((short) pe.getMarketNumber());
            buffer.putShort((short) pe.getEnvNumber());
            buffer.putShort((short) commodityNumber);
            buffer.putShort(cycle);
            buffer.putLong(startTime);// 参考开始时间
            buffer.put((byte) direction);// 方向
            buffer.putInt(count);// 请求的个数
            requestData =buffer;
        } catch (Exception e) {
            createError(responseData, e);
            return responseData;
        }
        byte[] retData;
        try {
            retData = requestBinaryAndChangeAvailableLink( requestData);
        } catch (Exception e) {
            networkError(responseData, e);
            return responseData;
        }
        responseData.convertTimeLineOne(retData);
        return responseData;


    }

    /**
     * 获取K线原始数据（同步方式）
     *
     * @param commodityNumber 商品编号
     * @param cycle           k线周期，之前获取的
     * @param startTime       参考开始时间，yyyyMMddHHmm,-1当前最后一个交易时间
     * @param direction       0为左 1为右
     * @param count           请求个数
     */
    public KLineOneResponseData getKLineSync(int commodityNumber, short cycle,
                                             long startTime, int direction, int count, int marketNumber,
                                             int envNumber) {

        KLineOneResponseData responseData = new KLineOneResponseData();
        ByteBuffer requestData;
        try {
            byte[] head = getHead(0x1E);
            ByteBuffer buffer = null;
            buffer = ByteBuffer.allocate(head.length + 4 + 2 + 2 + 8 + 1 + 4);

            buffer.put(head);
            buffer.putShort((short) marketNumber);
            buffer.putShort((short) envNumber);
            buffer.putShort((short) commodityNumber);// 商品编号
            buffer.putShort(cycle);// 周期
            buffer.putLong(startTime);// 参考开始时间
            buffer.put((byte) direction);// 方向
            buffer.putInt(count);// 请求的个数
            requestData = buffer;
        } catch (Exception e) {
            createError(responseData, e);
            return responseData;
        }
        byte[] retData;
        try {
            retData = requestBinaryAndChangeAvailableLink(requestData);
        } catch (Exception e) {
            networkError(responseData, e);
            return responseData;
        }
        responseData.convertKLineOne(retData);
        return responseData;


    }


    /**
     * V2协议5.39 K线-按日查询
     *
     * @param cycle K线周期
     * @return
     */
    public KLineAndZipUrlResponseData getKLineAndZipUrl(
            short commodityNumber, short cycle, int martketNumber, int envNumber) {

        KLineAndZipUrlResponseData responseData = new KLineAndZipUrlResponseData();
        ByteBuffer requestData;
        try {
            byte[] head = getHead( 0x21);
            ByteBuffer buffer = ByteBuffer.allocate(head.length + 4 + 2 + 2 + 1);
            buffer.put(head);
            buffer.putShort((short) martketNumber);
            buffer.putShort((short) envNumber);
            buffer.putShort(commodityNumber);
            buffer.putShort(cycle);
            buffer.put(new byte[]{(byte) 30});
            requestData = buffer;
        } catch (Exception e) {
            createError(responseData, e);
            return responseData;
        }
        byte[] retData;
        try {
            retData = requestBinaryAndChangeAvailableLink( requestData);
        } catch (Exception e) {
            networkError(responseData, e);
            return responseData;
        }
        responseData.convertKLineAndZipUrl(retData);
        return responseData;


    }


    /**
     * 直接从下载地址下载ZIP包
     *
     * @param
     * @return
     */
    /*public KLineFromZipUrlResponseData getKLineFromZipUrl(
            String zipUrl) {

        KLineFromZipUrlResponseData kLineFromZipUrlResponseData = new KLineFromZipUrlResponseData();

        byte[] kData = null;
        // 尝试从sd卡获取压缩包

        String zipName = zipUrl.substring(zipUrl.lastIndexOf('/') + 1);

        kData = myapp.getCurrentTradeEntity().getPriceData().getObjFromDiskCache(zipUrl);//从本地缓存获取
        if (kData == null || kData.length == 0) {
            // sd卡没缓存压缩包从网络获取


            kData = downLoadZip(zipUrl);
            if (kData == null) {
                Logger.d("kLineLoadAll...kData == null");
                return null;
            }
            myapp.getCurrentTradeEntity().getPriceData().addObjToFileCache(zipUrl, kData);
        }
        if (myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getPriceSecretEntityArrayList() == null) {
            PriceSercetListResponseData priceSercetListResponseData = getPriceSercetList();
            myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().setPriceSecretEntityArrayList(priceSercetListResponseData.getPriceSec());
        }

        try {
            byte[] secret = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getSecret(zipName.substring(0, 3));
            kData = AES.decrypt(kData,
                    Base64.decode(new String(secret), Base64.DEFAULT));
            kData = PriceUtil.decompress(kData);
        } catch (Exception e) {
            unknowError(kLineFromZipUrlResponseData, e);
            return null;
        }

        kLineFromZipUrlResponseData.convertKLineFromZipUrl(kData);


        return kLineFromZipUrlResponseData;


    }*/


    //下载TimeLineZip包
    public byte[] downLoadZip(String urlStr) {
        if (TextUtils.isEmpty(urlStr))
            return null;
        byte bAr[] = null;
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();

            BufferedInputStream bis = new BufferedInputStream(
                    conn.getInputStream());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = bis.read(b)) != -1) {
                baos.write(b, 0, len);
            }
            bAr = baos.toByteArray();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bAr;
    }


    public byte[] requestBinaryAndChangeAvailableLink( ByteBuffer requestData) throws Exception {
        return requestBinaryAvailableLink(requestData, new PriceLinkHelper(context));

    }

    public byte[] requestBinaryAvailableLink(ByteBuffer requestData, PriceLinkHelper linkHelper) throws Exception {
        byte[] retData;
        HttpSender httpSender = new HttpSender();
        try {
            retData = httpSender.requestBinary(requestData.array(), linkHelper.getCurrentUrl());
        } catch (Exception e) {
            // 失败后切换链路递归请求
            Logger.e("priceLogonRequest()...请求失败，开始切换链路");
            String availableUrl = linkHelper.switchLink();
            if (availableUrl != null) {
                return requestBinaryAvailableLink(requestData, linkHelper);
            } else {
                throw new Exception();
            }
        }
        // linkHelper.clearFailedLink();//可能没用,先去掉
        return retData;
    }


    /**
     * 取得公用头部，不包括协议编号
     * @param
     * @param protocolNumber 协议编号
     * @return
     */
    public byte[] getHead( int protocolNumber){
        byte[] sessionId = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getSessionId();
        byte[] version = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getProtocolInfo().getVersion().getBytes();
        byte sessionidCount = (byte)1;//sessionid个数
        byte sessionFlagLength = (byte)3;//session标识长度
        byte[] sessionFlag = new byte[]{(byte)1, (byte)5, (byte)9};//session标识内容

        byte tokenCount = (byte)3;//token个数
        byte tokenLength = (byte)1;//token长度
        byte[] token = new byte[]{(byte)9};//token内容

        ByteBuffer buffer = ByteBuffer.allocate(1 + 1 + version.length + 1 + sessionidCount*(1 + sessionFlagLength + 1 + sessionId.length) + 1 + tokenCount*(1+tokenLength));
        buffer.put((byte) protocolNumber);
        buffer.put((byte) version.length);
        buffer.put(version);
        buffer.put(sessionidCount);
        for (int i = 0; i < sessionidCount; i++) {
            buffer.put(sessionFlagLength);
            buffer.put(sessionFlag);
            buffer.put((byte) sessionId.length);
            buffer.put(sessionId);
        }
        buffer.put(tokenCount);
        for (int i = 0; i < tokenCount; i++) {
            buffer.put(tokenLength);
            buffer.put(token);
        }
        return buffer.array();
    }

}
