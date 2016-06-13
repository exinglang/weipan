package com.puxtech.weipan.data;

import org.json.JSONObject;

/**
 * Created by mac on 15/11/5.
 */
public class VirtualAccountResponseData extends BaseResponseDataOpenAccountContract {
    protected static final String CUSTOMER_NO = "customer_no";
    private String customerNo;

    public void parseXML(String jsString) {
        try {
            JSONObject root = checkFail(jsString);
            if (retCode != 0) {
                return;
            }
            JSONObject rep = root.getJSONObject(TAG_PUXT)
                    .getJSONObject(TAG_REP_BODY);
            customerNo = rep.getString(CUSTOMER_NO);

        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }

    public String getCustomerNo() {
        return customerNo;
    }
}
