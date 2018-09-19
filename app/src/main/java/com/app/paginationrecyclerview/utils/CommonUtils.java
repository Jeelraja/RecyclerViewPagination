package com.app.paginationrecyclerview.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class CommonUtils {

    private static ProgressDialog dialog;

    public static void startProgressDialog(Context context) {
        /**
         * Progress Dialog for User Interaction
         */
        dialog = new ProgressDialog(context);
        dialog.setTitle("Process");
        dialog.setMessage("Please Wait..");
        dialog.show();
    }

    public static void dismissProgressDialog(Context context) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static boolean isInternetAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            } else {
                Toast.makeText(context, "No Intrnet Connection", Toast.LENGTH_SHORT).show();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
