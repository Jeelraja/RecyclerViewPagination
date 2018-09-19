package com.app.paginationrecyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.paginationrecyclerview.MainActivity;
import com.app.paginationrecyclerview.R;
import com.app.paginationrecyclerview.model.CatAssignedProductListModel;
import com.app.paginationrecyclerview.pagination.PaginationAdapterCallback;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ProductListAdapter.class.getSimpleName();
    private Context mContext;
    private List<CatAssignedProductListModel.CatAssignedProductListDataModel> mProductList;

    // View Types
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback mCallback;

    private String errorMsg;


    public ProductListAdapter(Context mContext, List<CatAssignedProductListModel.CatAssignedProductListDataModel> list) {
        this.mContext = mContext;
        this.mProductList = list;
        this.mCallback = (PaginationAdapterCallback) mContext;
    }

    public List<CatAssignedProductListModel.CatAssignedProductListDataModel> getMovies() {
        return mProductList;
    }

    public void setMovies(List<CatAssignedProductListModel.CatAssignedProductListDataModel> movieResults) {
        this.mProductList = movieResults;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.item_list, parent, false);
                viewHolder = new ProductVH(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CatAssignedProductListModel.CatAssignedProductListDataModel result = mProductList.get(position); // Movie

        switch (getItemViewType(position)) {

            case ITEM:
                final ProductVH movieVH = (ProductVH) holder;
                movieVH.txt_product_name.setText(result.getName());
                break;

            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;
                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    mContext.getString(R.string.error_msg_unknown));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mProductList == null ? 0 : mProductList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mProductList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

     /*
        Helpers - Pagination
   _________________________________________________________________________________________________
    */

    public void add(CatAssignedProductListModel.CatAssignedProductListDataModel r) {
        mProductList.add(r);
        notifyItemInserted(mProductList.size() - 1);
    }

    public void addAll(List<CatAssignedProductListModel.CatAssignedProductListDataModel> moveResults) {
        for (CatAssignedProductListModel.CatAssignedProductListDataModel result : moveResults) {
            add(result);
        }
    }

    public void remove(CatAssignedProductListModel.CatAssignedProductListDataModel r) {
        int position = mProductList.indexOf(r);
        if (position > -1) {
            mProductList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new CatAssignedProductListModel.CatAssignedProductListDataModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mProductList.size() - 1;
        CatAssignedProductListModel.CatAssignedProductListDataModel result = getItem(position);

        if (result != null) {
            mProductList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public CatAssignedProductListModel.CatAssignedProductListDataModel getItem(int position) {
        return mProductList.get(position);
    }

    /**
     * Displays Pagination retry footer view along with appropriate errorMsg
     *
     * @param show
     * @param errorMsg to display if page load fails
     */
    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(mProductList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }

    /**
     * Main list's content ViewHolder
     */
    protected class ProductVH extends RecyclerView.ViewHolder {
        private TextView txt_product_name;

        public ProductVH(View itemView) {
            super(itemView);

            txt_product_name = itemView.findViewById(R.id.txt_product_name);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = (ImageButton) itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = (TextView) itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = (LinearLayout) itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    mCallback.retryPageLoad();

                    break;
            }
        }
    }
}