package com.puxtech.weipan.data;

import android.content.Context;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.helper.WeiPanFrontLinkHelper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by mac on 15/11/5.
 */
public class AppKeyPropertyResponseData extends BaseResponseDataOpenAccountContract {


    protected final String TAG_RESP = "RESP";
    protected final String TAG_RESP_CODE = "CODE";
    protected final String TAG_RESP_MSG = "MSG";
    protected final String TAG_OPENACCOUNTFRONTURL1 = "OPENACCOUNTFRONTURL1";
    protected final String TAG_OPENACCOUNTFRONTURL2 = "OPENACCOUNTFRONTURL2";
    protected final String TAG_WPUSERFRONTURL1 = "WPUSERFRONTURL1";
    protected final String TAG_WPUSERFRONTURL2 = "WPUSERFRONTURL2";
    protected final String TAG_PROPERTY = "PROPERTY";

    protected final String TAG_RSAPUBKEYSP = "RSAPUBKEYSP";
    protected final String TAG_RSAPUBKEYMNP = "RSAPUBKEYMNP";


    public void parse(Context context, byte[] data) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream byteIS = new ByteArrayInputStream(data);
            Document document = builder.parse(byteIS);
            // 获取根节点
            Element root = document.getDocumentElement();
            Element resp = (Element) root.getElementsByTagName(TAG_RESP)
                    .item(0);
            Element respCode = (Element) resp.getElementsByTagName(TAG_RESP_CODE)
                    .item(0);
            retCode = Integer.parseInt(respCode.getTextContent());
            Element respMsg = (Element) resp.getElementsByTagName(TAG_RESP_MSG)
                    .item(0);
            retMessage = respMsg.getTextContent();
            // 如果服务器验证失败就直接返回
            if (retCode != 0) {
                return ;
            }
            Element propertyElement = (Element) root.getElementsByTagName(TAG_PROPERTY).item(0);
//            MyApplication myApplication=(MyApplication)context.getApplicationContext();
//            myApplication.getContentsServerEntity().setOPENACCOUNTFRONTURL1(getString(propertyElement, TAG_OPENACCOUNTFRONTURL1));
//            myApplication.getContentsServerEntity().setOPENACCOUNTFRONTURL2(getString(propertyElement, TAG_OPENACCOUNTFRONTURL2));
//            myApplication.getContentsServerEntity().setWPUSERFRONTURL1(getString(propertyElement, "WPUSERFRONTURL1"));
//            myApplication.getContentsServerEntity().setWPUSERFRONTURL2(getString(propertyElement, "WPUSERFRONTURL2"));
//            Constant.OPEN_ACCOUNT_URL=getString(propertyElement,"OPENACCOUNTFRONTURL1");
//            Constant.WEIPAN_URL=getString(propertyElement,"WPUSERFRONTURL1");
            ArrayList   allLink = new ArrayList<>();
            allLink.add(getString(propertyElement, TAG_WPUSERFRONTURL1));
            allLink.add(getString(propertyElement, TAG_WPUSERFRONTURL2));
            WeiPanFrontLinkHelper.getInstance(context).setAllLink(allLink);


            MyApplication myApplication =(MyApplication)context.getApplicationContext();
            myApplication.getMoniTradeEntity().getOtherData().setRSA(getString(propertyElement, TAG_RSAPUBKEYMNP));
            myApplication.getShipanTradeEntity().getOtherData().setRSA(getString(propertyElement, TAG_RSAPUBKEYSP));

        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }

    protected String getString(Element e, String tag) {
        try{
            if (((Element) (e.getElementsByTagName(tag).item(0))).getFirstChild() != null) {
                return ((Element) (e.getElementsByTagName(tag).item(0)))
                        .getFirstChild().getNodeValue();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return "";

    }
}
