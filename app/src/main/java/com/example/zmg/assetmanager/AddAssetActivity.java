package com.example.zmg.assetmanager;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddAssetActivity extends AppCompatActivity {

    private String qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset);

        getData();

        Bundle data = new Bundle();
        data.putString("qr", qr);

        AddAssetFragment add_asset_fragment = new AddAssetFragment();

        add_asset_fragment.setArguments(data);

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, add_asset_fragment).commit();
    }

    private void getData(){

        qr = getIntent().getStringExtra("QR");
    }
}
