package com.puxtech.weipan.ResponseData;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.data.BaseResponseDataTrade;
import com.puxtech.weipan.data.SysInfoData;
import com.puxtech.weipan.data.SysInfoDetailData;
import com.puxtech.weipan.util.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 15/11/5.
 */
public class SysInfoDetailResponseData extends BaseResponseDataTrade {
    private static final String TAG_ID = "ID";
    private static final String TAG_TITLE = "TITLE";
    private static final String TAG_TYPE = "TYPE";
    private static final String TAG_PUBLISH = "PUBLISH";
    private static final String TAG_SEND_TIME = "SEND_TIME";
    private static final String TAG_EXPIRY_TIME = "EXPIRY_TIME";
    private static final String TAG_CONTENT = "CONTENT";
    SysInfoDetailData detailData;

    public void parseXML(String jsString) {
        try {
            JSONObject root = checkFail(jsString);
            if (retCode != 0) {
                return;
            }
            JSONObject rec = root.getJSONObject(Constant.TAG_MMTS)
                    .getJSONObject(Constant.TAG_REP);
//            JSONObject rec = rep.getJSONObject(Constant.TAG_LIST)
//                    .getJSONArray(Constant.TAG_REC).getJSONObject(0);
            detailData = new SysInfoDetailData();
            detailData.setID(rec.getString(TAG_ID));
            detailData.setTITLE(rec.getString(TAG_TITLE));
            detailData.setTYPE(rec.getString(TAG_TYPE));
            detailData.setPUBLISH(rec.getString(TAG_PUBLISH));
            detailData.setCONTENT(rec.getString(TAG_CONTENT));
            detailData.setSEND_TIME(ActivityUtils.getRealData(rec.getString(TAG_SEND_TIME)));
            detailData.setEXPIRY_TIME(ActivityUtils.getRealData(rec.getString(TAG_EXPIRY_TIME)));


        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }

    public SysInfoDetailData getDetailData() {
        return detailData;
    }
}