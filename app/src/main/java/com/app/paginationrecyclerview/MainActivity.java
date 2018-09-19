package com.app.paginationrecyclerview;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.app.paginationrecyclerview.adapter.ProductListAdapter;
import com.app.paginationrecyclerview.api.ApiService;
import com.app.paginationrecyclerview.api.ProductModel;
import com.app.paginationrecyclerview.model.CatAssignedProductListModel;
import com.app.paginationrecyclerview.network.RetroClient;
import com.app.paginationrecyclerview.pagination.PaginationAdapterCallback;
import com.app.paginationrecyclerview.pagination.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PaginationAdapterCallback {

    private static final String TAG = "MainActivity";
    private RecyclerView rv_list;
    private ProgressBar progressBar;
    private ProductListAdapter productListAdapter;
    private List<CatAssignedProductListModel.CatAssignedProductListDataModel> mProductList;
    private LinearLayoutManager linearLayoutManager;
    LinearLayout errorLayout;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 10;
    private int currentPage = PAGE_START;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProductList = new ArrayList<>();
        rv_list = findViewById(R.id.rv_list);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        progressBar = findViewById(R.id.main_progress);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);

        productListAdapter = new ProductListAdapter(this, mProductList);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list.setLayoutManager(linearLayoutManager);
        rv_list.setItemAnimator(new DefaultItemAnimator());
        rv_list.setAdapter(productListAdapter);

        rv_list.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        //init service and load data
        apiService = RetroClient.getApiService();
        loadFirstPage();
    }

    private void loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ");

        // To ensure list is visible when retry button in error view is clicked
        hideErrorView();
        callProductApi().enqueue(new Callback<CatAssignedProductListModel>() {
            @Override
            public void onResponse(Call<CatAssignedProductListModel> call, Response<CatAssignedProductListModel> response) {
                // Got data. Send it to adapter

                hideErrorView();

                List<CatAssignedProductListModel.CatAssignedProductListDataModel> results = fetchResults(response);
                progressBar.setVisibility(View.GONE);
                productListAdapter.addAll(results);

                if (currentPage <= TOTAL_PAGES) productListAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<CatAssignedProductListModel> call, Throwable t) {
                t.printStackTrace();
                showErrorView(t);
            }
        });
    }

    private Call<CatAssignedProductListModel> callProductApi() {
        ProductModel productModel = new ProductModel();
        productModel.setApi_key("ICXE1wOphgKgcyMoHr0hVHbbJ");
        productModel.setCatid("12");
        productModel.setLimit(5);
        productModel.setPage(currentPage);
        return apiService.productPostObject(
                productModel
        );
    }

    private void loadNextPage() {
        Log.i("Call", "2");
        Log.d(TAG, "loadNextPage: " + currentPage);
        callProductApi().enqueue(new Callback<CatAssignedProductListModel>() {

            @Override
            public void onResponse(Call<CatAssignedProductListModel> call, Response<CatAssignedProductListModel> response) {
                productListAdapter.removeLoadingFooter();
                isLoading = false;

                List<CatAssignedProductListModel.CatAssignedProductListDataModel> results = fetchResults(response);
                productListAdapter.addAll(results);

                if (currentPage != TOTAL_PAGES) productListAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<CatAssignedProductListModel> call, Throwable t) {
                t.printStackTrace();
                productListAdapter.showRetry(true, fetchErrorMessage(t));
            }
        });
    }

    /**
     * @param
     * @return
     */
    private List<CatAssignedProductListModel.CatAssignedProductListDataModel> fetchResults(Response<CatAssignedProductListModel> response) {
        CatAssignedProductListModel catAssignedProductListModel = response.body();
        return catAssignedProductListModel.getData();
    }

    /**
     * @param throwable to identify the type of error
     * @return appropriate error message
     */
    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!isNetworkConnected()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }

    /**
     * Remember to add android.permission.ACCESS_NETWORK_STATE permission.
     *
     * @return
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    // Helpers -------------------------------------------------------------------------------------


    private void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * @param throwable required for {@link #fetchErrorMessage(Throwable)}
     * @return
     */
    private void showErrorView(Throwable throwable) {

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            //txtError.setText(fetchErrorMessage(throwable));
        }
    }

    @Override
    public void retryPageLoad() {
        loadNextPage();
    }
}
