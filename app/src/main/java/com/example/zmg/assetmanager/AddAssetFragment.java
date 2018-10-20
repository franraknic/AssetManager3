package com.example.zmg.assetmanager;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zmg.assetmanager.model.Api;
import com.example.zmg.assetmanager.model.Asset;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddAssetFragment extends Fragment {

    private Button btnSave;
    private EditText etName;
    private EditText etComment;
    private String qrCode;


    public AddAssetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View addAssetFragment = inflater.inflate(R.layout.fragment_add_asset, container, false);

        initWidgets(addAssetFragment);
        setupListeners();

        return addAssetFragment;
    }

    public void setupListeners(){

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAsset();
            }
        });
    }

    public void initWidgets(View fragment){

        btnSave = fragment.findViewById(R.id.btnSave);
        etName = fragment.findViewById(R.id.etName);
        etComment = fragment.findViewById(R.id.etComment);

        // get qr code from scanner activity
        qrCode = getArguments().getString("qr");//Get pass data with its key value

    }

    public void saveAsset(){


        String name = etName.getText().toString();
        String comment = etComment.getText().toString();

        if (name.isEmpty() || comment.isEmpty()){
            Toast.makeText(getContext(), "Enter text to all fields!", Toast.LENGTH_SHORT).show();
        }else {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Api api = retrofit.create(Api.class);

            Call<List<Asset>> call = api.saveAsset(qrCode, name, comment);

            call.enqueue(new Callback<List<Asset>>() {
                @Override
                public void onResponse(Call<List<Asset>> call, Response<List<Asset>> response) {
                    List<Asset> assets = response.body();
                    Toast.makeText(getContext(), "Added Asset: " + assets.get(0).getName(), Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Call<List<Asset>> call, Throwable t) {
                    //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            Intent intent = new Intent(getActivity(), AssetListActivity.class);
            startActivity(intent);

        }



    }

}
