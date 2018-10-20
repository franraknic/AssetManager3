package com.example.zmg.assetmanager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zmg.assetmanager.model.Api;
import com.example.zmg.assetmanager.model.Asset;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AssetListFragment extends Fragment {

    private Button btnScanQR;
    private ListView listView;
    private List<Asset> assets;
    private AssetAdapter assetAdapter;



    public AssetListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View assetListFragment = inflater.inflate(R.layout.fragment_asset_list, container, false);

        initWidgets(assetListFragment);

        setupListeners();

        return assetListFragment;
    }

    private void setupListeners() {
        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // launch barcode activity.
                Intent intent = new Intent(getActivity(), ScannerActivity.class);
                startActivityForResult(intent, 9001);

            }
        });

    }

    private void populateListView(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Asset>> call = api.getAssets();

        call.enqueue(new Callback<List<Asset>>() {
            @Override
            public void onResponse(Call<List<Asset>> call, Response<List<Asset>> response) {

                assets = response.body();
                assetAdapter = new AssetAdapter(getContext(), new ArrayList<Asset>(assets));
                listView.setAdapter(assetAdapter);
            }

            @Override
            public void onFailure(Call<List<Asset>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initWidgets(View assetListFragment) {
        btnScanQR = assetListFragment.findViewById(R.id.btnScanQR);
        listView = assetListFragment.findViewById(R.id.listView);
        populateListView();

    }

    class AssetAdapter extends ArrayAdapter<Asset>{

        private Context mcontext;
        private List<Asset> assetList = new ArrayList<>();

        private AssetAdapter(Context context, ArrayList<Asset> list){
            super(context,0, list);
            mcontext = context;
            assetList = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;
            if(listItem == null){
                listItem = LayoutInflater.from(mcontext).inflate(R.layout.asset_list_item, parent, false);
            }
            Asset asset = assetList.get(position);

            TextView name = listItem.findViewById(R.id.tvName);
            name.setText(asset.getName());

            TextView comment = listItem.findViewById(R.id.tvComment);
            comment.setText(asset.getComment());

            return listItem;
        }
    }

}
