package com.example.zmg.assetmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class AssetListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_list);
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, new AssetListFragment()).commit();
    }
}
