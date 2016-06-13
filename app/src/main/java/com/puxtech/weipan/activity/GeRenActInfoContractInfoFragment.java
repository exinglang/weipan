package com.puxtech.weipan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.R;
import com.puxtech.weipan.data.entitydata.OpenAccountInfoEntity;
import com.puxtech.weipan.data.entitydata.SignBankEntity;
import com.puxtech.weipan.helper.TradeHelper;
import com.puxtech.weipan.util.ActivityUtils;

import java.util.ArrayList;


public class GeRenActInfoContractInfoFragment extends BaseFragment {
   // public static final int INPUT_OPEN_ACCOUNT = 2000;//开户

    private Context context;
    private static GeRenActInfoContractInfoFragment homeFragment;
    private View view;
    Button bt_contract;
    TextView tv_prompt,tv_id,tv_bankname,tv_branch,tv_province;
    // TODO: Rename and change types and number of parameters
    public static GeRenActInfoContractInfoFragment getInstance(Context context) {
        if (homeFragment == null) {
            homeFragment = new GeRenActInfoContractInfoFragment(context);
        }
        return homeFragment;
    }

    public GeRenActInfoContractInfoFragment(Context context) {
        super();
        this.context = context;

    }

    public GeRenActInfoContractInfoFragment() {
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        } else {
            try {
                view = inflater.inflate(R.layout.fragment_contract, container, false);
                initWidget();

            } catch (InflateException e) {
            }

        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (new TradeHelper(context).hasContract()) {
            tv_prompt.setText("已签约,您可以编辑签约信息");
            bt_contract.setText("编辑信息");
            ArrayList<SignBankEntity> bankList = myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity().getSignBankList();

            tv_id.setText(bankList.get(0).getAccount());
            tv_bankname.setText(bankList.get(0).getBank_name());
            tv_branch.setText(bankList.get(0).getBranch_name());
            tv_province.setText(bankList.get(0).getProvince_name());
        }
    }

    private void initWidget() {
        bt_contract = (Button) view.findViewById(R.id.bt_contract);
        tv_prompt= (TextView) view.findViewById(R.id.tv_prompt);
        tv_id= (TextView) view.findViewById(R.id.tv_id);
        tv_bankname= (TextView) view.findViewById(R.id.tv_bankname);
        tv_branch= (TextView) view.findViewById(R.id.tv_branch);
        tv_province= (TextView) view.findViewById(R.id.tv_province);
        bt_contract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(hasContract()) {
                    Intent intent = new Intent(context, GeRenActInfoContractInfoInputActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    /**
     * 判断是否已开户并签约,如果没有,进行提示
     * @return
     */
    private boolean hasContract(){
        TradeHelper tradeHelper =new TradeHelper(context);
        if (!tradeHelper.hasOpen()) {
            showSnackNoOpen(view);
            return false;
        } else{

            return true;
        }

    }
    private void showSnackNoOpen(View v) {
        ActivityUtils.showSnackbar(context, v, Constant.NO_OPEN, Constant.LIJIKAIHU, new View.OnClickListener() {
                    public void onClick(View v) {
                        gotoTargetPage( TabHomeFragment.KAI_HU);
                    }
                }
        );
    }
    private void showSnackNoContract(View v) {
        ActivityUtils.showSnackbar(context, v, Constant.NO_CONTRACT,Constant.LIJIQIANYUE, new View.OnClickListener() {
                    public void onClick(View v) {
                        gotoTargetPage( TabHomeFragment.CONTRACT);
                    }
                }
        );
    }
    /**
     * 跳转至对应页面
     */
    private void gotoTargetPage(String targetAction) {
        Bundle bd = new Bundle();
        bd.putString(Constant.TYPE, targetAction);
       // ((GeRenActInfoActivity) getActivity()).setCheck();
        ((GeRenActInfoActivity) getActivity()).setCurrentPageOpenAccount();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == INPUT_OPEN_ACCOUNT) {
//            if (resultCode == OpenAccInfoInputActivity.SUCESS_OPEN_TO_CONTRACT) {
//                ((GeRenZhangHuXinXiFragmentActiv) getActivity()).finishOpenToInputContract();
//            }
//        }
    }
}
