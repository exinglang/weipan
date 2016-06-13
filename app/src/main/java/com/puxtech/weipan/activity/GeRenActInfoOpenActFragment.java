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

import com.puxtech.weipan.R;
import com.puxtech.weipan.data.entitydata.OpenAccountInfoEntity;
import com.puxtech.weipan.helper.TradeHelper;


public class GeRenActInfoOpenActFragment extends BaseFragment {
    public static final int INPUT_OPEN_ACCOUNT = 2000;//开户

    private Context context;
    private static GeRenActInfoOpenActFragment homeFragment;
    private View view;
    Button bt_kaihu;
    TextView tv_prompt,tv_name,tv_id,tv_phone;

    // TODO: Rename and change types and number of parameters
    public static GeRenActInfoOpenActFragment getInstance(Context context) {
        if (homeFragment == null) {
            homeFragment = new GeRenActInfoOpenActFragment(context);
        }
        return homeFragment;
    }

    public GeRenActInfoOpenActFragment(Context context) {
        super();
        this.context = context;

    }

    public GeRenActInfoOpenActFragment() {
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
                view = inflater.inflate(R.layout.fragment_kaihu, container, false);
                initWidget();



            } catch (InflateException e) {
            }

        }

        return view;
    }

    private void initWidget() {
        bt_kaihu = (Button) view.findViewById(R.id.bt_kaihu);
        bt_kaihu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, GeRenActInfoOpenActInputActivity.class);
                startActivityForResult(intent, INPUT_OPEN_ACCOUNT);

            }
        });
        tv_prompt = (TextView) view.findViewById(R.id.tv_prompt);
        tv_name= (TextView) view.findViewById(R.id.tv_name);
        tv_id= (TextView) view.findViewById(R.id.tv_id);
        tv_phone= (TextView) view.findViewById(R.id.tv_phone);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INPUT_OPEN_ACCOUNT) {
            if (resultCode == GeRenActInfoOpenActInputActivity.SUCESS_OPEN_TO_CONTRACT) {
                ((GeRenActInfoActivity) getActivity()).finishOpenToInputContract();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (new TradeHelper(context).hasOpen()) {

            tv_prompt.setText("已开户,您可以编辑开户信息");
            bt_kaihu.setText("编辑信息");
            OpenAccountInfoEntity entity = myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity();
            tv_name.setText(entity.getCustomer_name());
            tv_id.setText(entity.getId_no());
            tv_phone.setText(entity.getPhone());
        }
    }
}
