package com.puxtech.weipan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.puxtech.weipan.R;
import com.puxtech.weipan.helper.WidgetHelper;
import com.puxtech.weipan.service.PriceService;

public class WebViewActivity extends BaseAppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.webview);
        Toolbar   toolbar = (Toolbar) findViewById(R.id.toolbar);

        WidgetHelper.initToolBarWithSubTitleAndFinishIcon(this, "风险提醒", toolbar);

        webView=(WebView)findViewById(R.id.mwebView);
        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getIntent().getAction());
    }

}

