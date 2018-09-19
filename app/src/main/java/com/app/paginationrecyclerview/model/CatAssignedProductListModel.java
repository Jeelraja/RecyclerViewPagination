package com.app.paginationrecyclerview.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CatAssignedProductListModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("total_page")
    @Expose
    private Integer totalPage;
    @SerializedName("totalProducts")
    @Expose
    private Integer totalProducts;
    @SerializedName("data")
    @Expose
    private List<CatAssignedProductListDataModel> data = null;
    @SerializedName("filters")
    @Expose
    private Filters filters;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Integer totalProducts) {
        this.totalProducts = totalProducts;
    }

    public List<CatAssignedProductListDataModel> getData() {
        return data;
    }

    public void setData(List<CatAssignedProductListDataModel> data) {
        this.data = data;
    }

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    public static class CatAssignedProductListDataModel {

        @SerializedName("prod_id")
        @Expose
        private String prodId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("sku")
        @Expose
        private String sku;
        @SerializedName("price")
        @Expose
        private double price;
        @SerializedName("discount_price")
        @Expose
        private double discountPrice;
        @SerializedName("in_stock")
        @Expose
        private String inStock;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("image_url")
        @Expose
        private String imageUrl;
        @SerializedName("thumbnail_imageurl")
        @Expose
        private String thumbnailImageurl;
        @SerializedName("prod_in_cart")
        @Expose
        private String prodInCart;
        @SerializedName("multiple_images")
        @Expose
        private List<MultipleImage> multipleImages = null;
        @SerializedName("wishlist")
        @Expose
        private Boolean wishlist;

        public String getProdId() {
            return prodId;
        }

        public void setProdId(String prodId) {
            this.prodId = prodId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(double discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getInStock() {
            return inStock;
        }

        public void setInStock(String inStock) {
            this.inStock = inStock;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getThumbnailImageurl() {
            return thumbnailImageurl;
        }

        public void setThumbnailImageurl(String thumbnailImageurl) {
            this.thumbnailImageurl = thumbnailImageurl;
        }

        public String getProdInCart() {
            return prodInCart;
        }

        public void setProdInCart(String prodInCart) {
            this.prodInCart = prodInCart;
        }

        public List<MultipleImage> getMultipleImages() {
            return multipleImages;
        }

        public void setMultipleImages(List<MultipleImage> multipleImages) {
            this.multipleImages = multipleImages;
        }

        public Boolean getWishlist() {
            return wishlist;
        }

        public void setWishlist(Boolean wishlist) {
            this.wishlist = wishlist;
        }
    }

    public class Filters {

        @SerializedName("priceRange")
        @Expose
        private PriceRange priceRange;
        @SerializedName("priceRangeApplied")
        @Expose
        private PriceRangeApplied priceRangeApplied;
        @SerializedName("attributes")
        @Expose
        private List<Object> attributes = null;

        public PriceRange getPriceRange() {
            return priceRange;
        }

        public void setPriceRange(PriceRange priceRange) {
            this.priceRange = priceRange;
        }

        public PriceRangeApplied getPriceRangeApplied() {
            return priceRangeApplied;
        }

        public void setPriceRangeApplied(PriceRangeApplied priceRangeApplied) {
            this.priceRangeApplied = priceRangeApplied;
        }

        public List<Object> getAttributes() {
            return attributes;
        }

        public void setAttributes(List<Object> attributes) {
            this.attributes = attributes;
        }

    }

    public class MultipleImage {

        @SerializedName("multiple")
        @Expose
        private String multiple;

        public String getMultiple() {
            return multiple;
        }

        public void setMultiple(String multiple) {
            this.multiple = multiple;
        }

    }

    public class PriceRange {

        @SerializedName("minPrice")
        @Expose
        private Integer minPrice;
        @SerializedName("maxPrice")
        @Expose
        private Integer maxPrice;

        public Integer getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(Integer minPrice) {
            this.minPrice = minPrice;
        }

        public Integer getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(Integer maxPrice) {
            this.maxPrice = maxPrice;
        }

    }

    public class PriceRangeApplied {

        @SerializedName("minPrice")
        @Expose
        private Integer minPrice;
        @SerializedName("maxPrice")
        @Expose
        private Integer maxPrice;

        public Integer getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(Integer minPrice) {
            this.minPrice = minPrice;
        }

        public Integer getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(Integer maxPrice) {
            this.maxPrice = maxPrice;
        }

    }

}

