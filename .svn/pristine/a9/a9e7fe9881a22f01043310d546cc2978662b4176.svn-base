package com.puxtech.weipan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.R;
import com.puxtech.weipan.adapter.ChooseItemAdapter;
import com.puxtech.weipan.data.ChooseItemData;
import com.puxtech.weipan.helper.WidgetHelper;

import java.util.ArrayList;


public class ChooseItemActivity extends BaseAppCompatActivity implements OnItemClickListener {
    public static final String KEY_CODEID = "KEY_CODEID";
    public static final String KEY_CODENAME = "KEY_CODENAME";
    public static final String TITLE = "TITLE";

    ChooseItemAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseitem);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);

        WidgetHelper.initToolBarWithSubTitleAndFinishIcon(this, getIntent().getExtras().getString(TITLE), toolbar);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
//	ArrayList user=(User)intent.getSerializableExtra(ConctInfoInputActivity.CONTRACT_CHOOSE_ITEM_DATA);
        ArrayList<ChooseItemData> chooseItemDataArrayList = (ArrayList<ChooseItemData>) getIntent().getExtras().getSerializable(GeRenActInfoContractInfoInputActivity.CONTRACT_CHOOSE_ITEM_DATA);
        adapter = new ChooseItemAdapter(context, chooseItemDataArrayList);
        listView.setAdapter(adapter);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        ChooseItemData chooseItemData = adapter.getItem(position);
        Intent intent = new Intent();
        intent.putExtra(ChooseItemActivity.KEY_CODEID,
                chooseItemData.getCode());
        intent.putExtra(ChooseItemActivity.KEY_CODENAME,
                chooseItemData.getName());
        setResult(Constant.CODE_SUCCESS, intent);
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
           setFailResultCode();

        }

        return false;

    }

    private void setFailResultCode() {
        setResult(Constant.CODE_FAIL);
        finish();
    }
}
