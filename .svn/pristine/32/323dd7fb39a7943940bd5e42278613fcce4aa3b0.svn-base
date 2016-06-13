package com.puxtech.weipan.data;

import android.content.Context;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.data.entitydata.ContentsServerEntity;
import com.puxtech.weipan.data.entitydata.ContentsServerEnvEntity;
import com.puxtech.weipan.data.entitydata.ContentsServerJysEntity;
import com.puxtech.weipan.data.entitydata.ContentsServerServiceEntity;
import com.puxtech.weipan.data.entitydata.ContentsServerZuEntity;
import com.puxtech.weipan.helper.PriceLinkHelper;
import com.puxtech.weipan.network.TokenBManager;
import com.puxtech.weipan.parser.ContentsServerParser;
import com.puxtech.weipan.util.SharedPreferenceManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by mac on 15/11/5.
 */
public class CatalogResponseData extends BaseResponseDataOpenAccountContract {
    protected static final String TAG_BANK_LIST = "bank_list";
    protected static final String TAG_BANK_ID = "bank_id";
    protected static final String TAG_BANK_NAME = "bank_name";


    public void parse(Context context, byte[] data) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream byteIS = new ByteArrayInputStream(data);
            Document document = builder.parse(byteIS);
            // 获取根节点
            Element root = document.getDocumentElement();
            ContentsServerParser parser = new ContentsServerParser(context);
            ContentsServerEntity csEntity = parser.parse(root);
            // 如果请求验证失败就不保存到本地
            if (csEntity != null && csEntity.getCode() != 0) {
                parseError();
                return;
            }

            // 分离tknb
            Element resp = (Element) root.getElementsByTagName("RESP").item(0);
            Element tknb = (Element) resp.getElementsByTagName("TKNB").item(0);
            resp.removeChild(tknb);

            // 保存tknb
            TokenBManager tokenBManager = new TokenBManager(context);
            tokenBManager.saveTokenB(tknb);
            // 保存contentServer到本地
            ((MyApplication) context.getApplicationContext()).setContentsServerEntity(csEntity);
            saveUrl(context);
            saveContentServerEntity(context, root);
        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }

    private void saveUrl(Context context) throws Exception {
        List<ContentsServerServiceEntity> serviceList = ((MyApplication) context.getApplicationContext())
                .getContentsServerEntity().getYwSystemEntity().getServiceList();
        for (ContentsServerServiceEntity serviceEntity : serviceList) {
            // 行情服务前置机地址  5;实盘
            if (serviceEntity.getType() == 5) {
//                for (ContentsServerZuEntity zuEntity : serviceEntity.getZuList()) {
//                    PriceLinkHelper linkHelper = new PriceLinkHelper(context, zuEntity.getId());
//                    Constant.PRICE_SERVER_URL_MAP.put(zuEntity.getId(), linkHelper.getLinkRandom());
//                    PriceRuntimeData priceData = new PriceRuntimeData();
//                    priceData.setZuId(zuEntity.getId());
//                    mApplication.addPriceRuntimeData(priceData);
//                }
                Constant.PRICE_URL_SHIPAN = serviceEntity.getZuList().get(0).getLlList().get(0).getUrl();
               // Constant.PRICE_URL_SHIPAN ="http://123.151.205.141:8180/quotation_query/query.do";
//                http://123.151.205.141:8180/quotation_query/query.do          汇通电子模拟盘 行情前置地址
            }
            //6.模拟盘
            if (serviceEntity.getType() == 6) {
                Constant.PRICE_URL_MONI = serviceEntity.getZuList().get(0).getLlList().get(0).getUrl();

              //  Constant.PRICE_URL_MONI="http://123.151.205.141:8180/quotation_query/query.do";;
            }

        }
        List<ContentsServerEnvEntity> envList = ((MyApplication) context.getApplicationContext())
                .getContentsServerEntity().getYwSystemEntity().getJysList().get(0).getEnvList();
        for (ContentsServerEnvEntity envEntity : envList) {
            //1实盘, 2模拟盘,3.会员验证实盘
            if (envEntity.getCategory() == 1) {
                //1是实盘
                Constant.TRADE_URL_SHIPAN = envEntity.getYwList().get(0).getLlList().get(0).getUrl();
                Constant.TRADE_URL_SHIPAN_MONEY = envEntity.getYwList().get(2).getLlList().get(0).getUrl();
                Constant.TRADE_URL_SHIPAN_REPORT = envEntity.getYwList().get(1).getLlList().get(0).getUrl();
                ((MyApplication) context.getApplicationContext()).getShipanTradeEntity().setTradeUrl(envEntity.getYwList().get(0).getLlList().get(0).getUrl());
                ((MyApplication) context.getApplicationContext()).getShipanTradeEntity().setMoneyUrl(envEntity.getYwList().get(2).getLlList().get(0).getUrl());
                ((MyApplication) context.getApplicationContext()).getShipanTradeEntity().setReportUrl(envEntity.getYwList().get(1).getLlList().get(0).getUrl());

            }
            if (envEntity.getCategory() == 2) {
                Constant.TRADE_URL_MONI = envEntity.getYwList().get(0).getLlList().get(0).getUrl();
                Constant.TRADE_URL_MONI_MONEY = envEntity.getYwList().get(2).getLlList().get(0).getUrl();
                Constant.TRADE_URL_MONI_REPORT = envEntity.getYwList().get(1).getLlList().get(0).getUrl();
                ((MyApplication) context.getApplicationContext()).getMoniTradeEntity().setTradeUrl(envEntity.getYwList().get(0).getLlList().get(0).getUrl());
                ((MyApplication) context.getApplicationContext()).getMoniTradeEntity().setMoneyUrl(envEntity.getYwList().get(2).getLlList().get(0).getUrl());
                ((MyApplication) context.getApplicationContext()).getMoniTradeEntity().setReportUrl(envEntity.getYwList().get(1).getLlList().get(0).getUrl());

            }

        }
        //=[]  executeNextTask();
    }

    public void saveContentServerEntity(Context context, Element rootElement) {
        if (rootElement == null) {
            return;
        }
        // 保存到本地
        File cacheDir = context.getCacheDir();
        File fileDir = new File(cacheDir.getPath() + "/contentServer");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        try {
            TransformerFactory xformFactory = TransformerFactory.newInstance();

            Transformer idTransform = xformFactory.newTransformer();
            idTransform.setOutputProperty(OutputKeys.ENCODING, TokenBManager.ENCODE);
            idTransform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
                    "yes");

            Source input = new DOMSource(rootElement);

            Result output = new StreamResult(new FileOutputStream(
                    fileDir.getPath() + "/contentServerEntity"));
            idTransform.transform(input, output);

            // 保存此次更新时间
            SharedPreferenceManager spMgr = new SharedPreferenceManager(
                    context, SharedPreferenceManager.FILE_NAME_PRICE_SETTING);
            spMgr.putLong(context,
                    SharedPreferenceManager.KEY_CONTENT_SERVER_REFRESH_TIME,
                    System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
