package com.puxtech.weipan.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.puxtech.weipan.R;
import com.puxtech.weipan.WeiXinLogin;
import com.puxtech.weipan.util.SharedPreferenceManager;
import com.puxtech.weipan.yindaoye.ParallaxContainer;

/**
 * @author zuochenyong 2015-12-3
 */

public class GuideActivity extends WeiXinLogin {

    ImageView iv_man;
    ImageView rl_weibo;
    ParallaxContainer parallaxContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yindao);

        /**
         * 动画支持11以上sdk,11以下默认不显示动画
         * 若需要支持11以下动画,也可导入https://github.com/JakeWharton/NineOldAndroids
         */
        if (android.os.Build.VERSION.SDK_INT > 10) {
            iv_man = (ImageView) findViewById(R.id.iv_man);
            parallaxContainer = (ParallaxContainer) findViewById(R.id.parallax_container);

            if (parallaxContainer != null) {
                parallaxContainer.setImage(iv_man);
                parallaxContainer.setLooping(false);

                iv_man.setVisibility(View.VISIBLE);
                parallaxContainer.setupChildren(getLayoutInflater(),
                        R.layout.view_intro_1, R.layout.view_intro_2,
                        R.layout.view_intro_3, R.layout.view_intro_4,
                        R.layout.view_intro_5, R.layout.activity_wei_xin_login);
            }
        } else {
            setContentView(R.layout.activity_wei_xin_login);
        }

        rl_weibo = (ImageView) findViewById(R.id.rl_weibo);
        rl_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(context, SharedPreferenceManager.FILE_NAME_YIN_DAO_YE);
                if (!sharedPreferenceManager.getBoolean(context, sharedPreferenceManager.HAS_SHOW_YIN_DAO_YE, false)) {
                    sharedPreferenceManager.putBoolean(context,sharedPreferenceManager.HAS_SHOW_YIN_DAO_YE,true);

                }
                    requestLogin(view);
                }
            }


            );
        }
    }
