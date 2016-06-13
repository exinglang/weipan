package com.puxtech.weipan.data;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.data.entitydata.AdPictureEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 15/11/5.
 */
public class AlertUrlResponseData extends BaseResponseDataOpenAccountContract {

    //"token_b": " <TOKENB>...With User ID...</TOKENB>",
//        "user_id": "11",
//        "phone": "",
//        "nickname": "阿三",
//        "avatar": "
    private String url="";
    protected static final String TAG_URL = "url";

    protected static final String TAG_PROTOCOLS = "protocols";

    ArrayList<AdPictureEntity> adPictureEntityArrayList;


    public void parseXML(MyApplication myapp, String jsString) {

        try {
            JSONObject root = checkFail(jsString);
            if (retCode != 0) {
                return;
            }
            JSONObject rep = root.getJSONObject(TAG_PUXT)
                    .getJSONObject(TAG_REP_BODY);
            JSONArray array = rep
                    .getJSONArray(TAG_PROTOCOLS);
            adPictureEntityArrayList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject rec = array.getJSONObject(i);
                url = rec.getString(TAG_URL);

            }
        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
