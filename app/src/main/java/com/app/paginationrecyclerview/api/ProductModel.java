package com.app.paginationrecyclerview.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductModel {

    @Expose
    @SerializedName("api_key")
    private String api_key;
    @Expose
    @SerializedName("catid")
    private String catid;
    @Expose
    @SerializedName("page")
    private int page;
    @Expose
    @SerializedName("limit")
    private int limit;

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
