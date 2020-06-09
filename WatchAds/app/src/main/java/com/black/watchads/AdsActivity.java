package com.black.watchads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class AdsActivity extends AppCompatActivity {
    // admob- app ID = ca-app-pub-3039999263140842~8065591733
    // admob- unit ID = ca-app-pub-3039999263140842/3897108406
    private AdViewAdapter adViewAdapter;
    private List<AdsEntity> adsEntityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        MobileAds.initialize(this, String.valueOf(R.string.addmob_app_id));
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(String.valueOf(R.string.addmob_unit_id));

        // User Activities
        adsEntityList = new ArrayList<>();
        ListView adsListView = findViewById(R.id.adsList);
        adViewAdapter = new AdViewAdapter(this,adsEntityList);
        adsListView.setAdapter(adViewAdapter);
    }
}
