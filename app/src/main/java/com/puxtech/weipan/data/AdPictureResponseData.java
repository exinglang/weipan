package com.puxtech.weipan.data;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.data.entitydata.AdPictureEntity;
import com.puxtech.weipan.data.entitydata.ThirdPartyLoginEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 15/11/5.
 */
public class AdPictureResponseData extends BaseResponseDataOpenAccountContract {

    //"token_b": " <TOKENB>...With User ID...</TOKENB>",
//        "user_id": "11",
//        "phone": "",
//        "nickname": "阿三",
//        "avatar": "
    AdPictureEntity entity;
    protected static final String TAG_PICTURES = "pictures";
    protected static final String TAG_TYPE = "type";
    protected static final String TAG_TITLE = "title";
    protected static final String TAG_PIC_URL = "pic_url";
    protected static final String TAG_CONTENT_URL = "content_url";


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
                    .getJSONArray(TAG_PICTURES);
            adPictureEntityArrayList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject rec = array.getJSONObject(i);
                AdPictureEntity itemData = new AdPictureEntity();
                itemData.setType(rec.getString(TAG_TYPE));
                itemData.setTitle(rec.getString(TAG_TITLE));
                itemData.setPic_url(rec.getString(TAG_PIC_URL));
                itemData.setContent_url(rec.getString(TAG_CONTENT_URL));

                adPictureEntityArrayList.add(itemData);
            }
            myapp.getOpenAccountContractEntity().setAdPictureEntityArrayList(adPictureEntityArrayList);
        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }


}
