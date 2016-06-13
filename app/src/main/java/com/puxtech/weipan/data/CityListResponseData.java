package com.puxtech.weipan.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 15/11/5.
 */
public class CityListResponseData extends ContractChooseItemResponseData {
    protected static final String TAG_CITY_LIST = "city_list";
    protected static final String TAG_CITY_ID = "city_id";
    protected static final String TAG_CITY_NAME = "city_name";


    public void parseXML(String jsString) {

        try {
            JSONObject root = checkFail(jsString);
            if (retCode != 0) {
                return;
            }
            JSONObject rep = root.getJSONObject(TAG_PUXT)
                    .getJSONObject(TAG_REP_BODY);
            JSONArray array = rep
                    .getJSONArray(TAG_CITY_LIST);
            chooseItemDataArrayList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject rec = array.getJSONObject(i);
                ChooseItemData itemData = new ChooseItemData();
                itemData.setCode(rec.getString(TAG_CITY_ID));
                itemData.setName(rec.getString(TAG_CITY_NAME));
                chooseItemDataArrayList.add(itemData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }


}
