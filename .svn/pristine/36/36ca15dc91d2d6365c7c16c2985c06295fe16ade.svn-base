package com.puxtech.weipan.data;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.data.entitydata.OpenAccountInfoEntity;
import com.puxtech.weipan.data.entitydata.SignBankEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.puxtech.weipan.data.entitydata.OpenAccountInfoEntity.*;

/**
 * Created by mac on 15/11/5.
 */
public class OpenAccountInfoResponseData extends BaseResponseDataOpenAccountContract {

    protected static final String TAG_CUSTOMER_NAME = "customer_name";
    protected static final String TAG_CUSTOMER_NO = "customer_no";
    protected static final String TAG_ID_NO = "id_no";
    protected static final String TAG_ID_TYPE = "id_type";
    protected static final String TAG_MEMBER_NAME = "member_name";
    protected static final String TAG_MEMBER_NO = "member_no";
    protected static final String TAG_PHONE = "phone";
    protected static final String TAG_SIGNING_BANK_LIST = "signing_bank_list";
    protected static final String TAG_ACCOUNT = "account";
    protected static final String TAG_ACCOUNT_NAME = "account_name";
    protected static final String TAG_BANK_ID = "bank_id";
    protected static final String TAG_BANK_NAME = "bank_name";

    protected static final String TAG_PROVINCE_ID = "province_id";
    protected static final String TAG_BRANCH_ID = "branch_id";
    OpenAccountInfoEntity entity;


    public void parseXML(MyApplication myapp, String jsString) {
        try {
            JSONObject root = checkFail(jsString);
            if (retCode != 0) {
                return;
            }
            JSONObject body = root.getJSONObject(TAG_PUXT)
                    .getJSONObject(TAG_REP_BODY);
            entity = new OpenAccountInfoEntity();
            entity.setCustomer_name(body.getString(TAG_CUSTOMER_NAME));
            entity.setCustomer_no(body.getString(TAG_CUSTOMER_NO));
            entity.setId_no(body.getString(TAG_ID_NO));
            entity.setId_type(body.getString(TAG_ID_TYPE));
            entity.setMember_name(body.getString(TAG_MEMBER_NAME));
            entity.setMember_no(body.getString(TAG_MEMBER_NO));
            entity.setPhone(body.getString(TAG_PHONE));
            JSONArray bankList = body.getJSONArray(TAG_SIGNING_BANK_LIST);
            ArrayList<SignBankEntity> signBankArrayList = new ArrayList<>();
            for (int i = 0; i < bankList.length(); i++) {
                SignBankEntity signBank = new SignBankEntity();
                JSONObject bankInfo = bankList.getJSONObject(i);
                signBank.setAccount(bankInfo.getString(TAG_ACCOUNT));
                signBank.setAccount_name(bankInfo.getString(TAG_ACCOUNT_NAME));
                signBank.setBank_id(bankInfo.getString(TAG_BANK_ID));
                signBank.setBank_name(bankInfo.getString(TAG_BANK_NAME));
                signBank.setProvince_id(bankInfo.getString(TAG_PROVINCE_ID));
                signBank.setBranch_id(bankInfo.getString(TAG_BRANCH_ID));
                signBankArrayList.add(signBank);

            }
            entity.setSignBankList(signBankArrayList);
            myapp.getOpenAccountContractEntity().setOpenAccountInfoEntity(entity);

        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }

}
