package com.cpxiao.crazy.aa.activity;

import android.os.Bundle;

import com.cpxiao.crazy.aa.fragment.HomeFragment;
import com.cpxiao.gamelib.activity.BaseAdsActivity;
import com.cpxiao.gamelib.fragment.BaseFragment;

public class MainActivity extends BaseAdsActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return HomeFragment.newInstance(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAdMobAds50("ca-app-pub-4157365005379790/1261816645");
        initAdMobAds50("ca-app-pub-4157365005379790/8066720531");
        initAdMobAds50("ca-app-pub-4157365005379790/3325050810");
        initAdMobAds50("ca-app-pub-4157365005379790/2403330318");
        initFbAds50("458552211195548_460257481025021");
        initFbAds50("458552211195548_460385924345510");
//        ZAdManager.getInstance().init(getApplicationContext());
//        initAds(ZAdPosition.POSITION_HOME);
    }


}
