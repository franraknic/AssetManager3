package com.example.zmg.assetmanager.model;
import com.example.zmg.assetmanager.model.Asset;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL = "http://li1817-216.members.linode.com/public/index.php/";

    @GET("assets")
    Call<List<Asset>> getAssets();

    @GET("assets/{id}")
    Call<List<Asset>> getOneAsset(@Path("id") int id);

    @DELETE("assets/{id}")
    Call<List<Asset>> deleteAsset(@Path("id") int id);

    @POST("assets")
    Call<List<Asset>> saveAsset(
            @Query("qr") String qr,
            @Query("name") String name,
            @Query("comment") String comment
    );

}
