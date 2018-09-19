package com.app.paginationrecyclerview.api;

import com.app.paginationrecyclerview.model.CatAssignedProductListModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("categoryAssignedProdList.php")
    Call<CatAssignedProductListModel> productPostObject(@Body ProductModel productModel);
}
