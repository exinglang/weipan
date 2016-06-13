package com.puxtech.weipan.network;

import android.content.Context;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.data.TradeEntity;
import com.puxtech.weipan.data.entitydata.OpenAccountInfoEntity;
import com.puxtech.weipan.util.ActivityUtils;
import com.puxtech.weipan.util.TradeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mac on 15/11/4.
 */
public class RequestDataCreatorOpenAccountContract extends RequestDataCreatorBase {

    private static final String mTakenB = "<TKNB><APPK>95efd63e-79aa-4c6a-8323-f080cd505242</APPK><DEVID>979d6c09-d6a0-41d8-ae72-75fa7002abea</DEVID><DATE>2015-12-25 12:02:05</DATE><SALT>CI/RUA==</SALT><SIGN>dRNlGSEWKV3x/bFA7aYS0QoDQ9NPNj1tgSkKEfRo2fi3LynhtJ6w/RK3U9BOexrc4yQuDQrUEtp8weBFXFrqKA==</SIGN></TKNB>";
    private static final String MEMBER_NO ="402";
    private static final String MEMBER_NAME ="PUXTECH";
    private static final String CUSTOME_NAME ="ZUOCHENYONG";

    public RequestDataCreatorOpenAccountContract(Context context) {
        super(context);

    }


    /**
     * @param rName         协议名称
     * @param start         开始位置
     * @param commodityCode
     * @return
     * @throws Exception
     */
    public String createHoldTotalRequestData(String rName, int start, String commodityCode) throws Exception {


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("STARTNUM", String.valueOf(start));
        map.put("RECCNT", "");
        map.put("COMMODITY_ID", commodityCode);
        map.put("MARKET_ID", "1");
        map.put("SORTFLD", "");
        map.put("SORTFLD", "");
        map.put("A_N", "");
        map.put("P_P", "");

        return getRequestData(map, rName);
    }

    /**
     * 请求验证码
     *
     * @param rName 协议名称
     * @return
     * @throws Exception
     */
    public String createSendVerifyCodeRequestData(String rName, String phoneNumber) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phoneNumber);

        return getRequestData(map, rName);
    }

    /**
     * 检查验证码
     *
     * @param rName 协议名称
     * @return
     * @throws Exception
     */
    public String createCheckVerifyCodeRequestData(String rName, String phoneNumber, String code) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phoneNumber);
        map.put("val_code", code);
        return getRequestData(map, rName);
    }

    /**
     * 开户
     *
     * @param rName 协议名称
     * @throws Exception
     */
    public String createOpenAccountRequestData(String rName, String phoneNumber, String id,String name) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("member_no", MEMBER_NO);
        map.put("member_name", MEMBER_NAME);
        map.put("customer_name", name);
        map.put("papers_type", "1");
        map.put("papers_num", id);
        map.put("phone", phoneNumber);
        map.put("ip", "");
        return getRequestData(map, rName);
    }
    /**
     * 开户信息
     *
     * @param rName 协议名称
     * @throws Exception
     */
    public String createOpenAccountInfoRequestData(String rName,String customNo) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("customer_no", customNo);

        return getRequestData(map, rName);
    }
    /**
     * 签约操作
     *
     *
     * @param bankNumber
     *@param bankName
     * @param provinceId
     * @param rName 协议名称  @throws Exception
     */
    public String createContractInfoRequestData(String rName,String bankNumber, String bankName, String provinceId, String bankId, String branchId) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        OpenAccountInfoEntity oaEntity=myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity();
        map.put("member_no", MEMBER_NO);
        map.put("member_name", MEMBER_NAME);
        map.put("customer_no", oaEntity.getCustomer_no());
        map.put("customer_name", oaEntity.getCustomer_name());
        map.put("papers_type",oaEntity.getId_type());
        map.put("papers_num", oaEntity.getId_no());
        map.put("phone", oaEntity.getPhone());
        map.put("bank_account", bankNumber);
        map.put("bank_name", bankName);
        map.put("bank_province", provinceId);
        map.put("bank_id", "900");
        map.put("ip", "");
        map.put("branch_id", branchId);

        return getRequestData(map, rName);
    }
    /**
     * 检查验证码
     *
     * @param rName 协议名称
     * @return
     * @throws Exception
     */
    public String createBankListRequestData(String rName) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("bank_id", "");
        return getRequestData(map, rName);
    }
    public String createProvinceListRequestData(String rName) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("province_id", "");
        return getRequestData(map, rName);
    }
    public String createCityListRequestData(String rName,String provinceId) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("province_id", provinceId);
        return getRequestData(map, rName);
    }
    public String createBranchBankRequestData(String rName,String cityId,String bankId) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("bank_id", bankId);
        map.put("city_id", cityId);

        return getRequestData(map, rName);
    }
    // 将map中的值,组成json
    protected String getRequestData(HashMap<String, String> map, String rName) {
        JSONObject json = new JSONObject();
        String req = null;
        try {
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> m = it.next();
                String key = m.getKey();
                String value = m.getValue();
                json.put(key, value);
            }
            req = jsonRequestPutHead(json, rName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return req;

    }

    // 给一些复用的KEY赋值
    protected String jsonRequestPutHead(JSONObject body, String name)
            throws JSONException {
        // TODO Auto-generated method stub
        body.put("token_b",mTakenB);
        JSONObject head = new JSONObject();
        head.put("version", "1.0");
        head.put("protocol_name", name);
        JSONObject rep = new JSONObject();
        rep.put("req_header", head);
        rep.put("req_body", body);
        JSONObject mmts = new JSONObject();
        mmts.put("puxt", rep);

//        TradeLogRecorder tlr = new TradeLogRecorder();
//        tlr.saveLog(myapp, "请求:"+json.toString());
        return mmts.toString();
    }

}
