package com.drprog.moodstory.core;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;

import java.lang.ref.WeakReference;

/**
 * This class used to make and ability to show progress bar
 * for asynchronous requests
 * Created by roman.donchenko on 18.01.2016
 */
public class ProgressBarController {

    private static WeakReference<Context> sContextHolder;
    private static ProgressDialog sProgressDialog;

    public static void showProgressDialog(final Context context, final String message) {
        if(context == null
               || (context instanceof Activity && ((Activity)context).isFinishing())) {
            return;
        }
        if(sContextHolder == null || sContextHolder.get() == null
                || sContextHolder.get() != context)
            sContextHolder = new WeakReference<>(context);
        if (sProgressDialog == null) {
            sProgressDialog = new ProgressDialog(context);
            sProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);;
            sProgressDialog.setCancelable(false);
        }
        sProgressDialog.setMessage(message);
        sProgressDialog.show();
    }

    public static void showProgressDialog(final Context context) {
        showProgressDialog(context, null);
    }

    public static void hideProgressDialog() {
        if (sProgressDialog != null && sProgressDialog.isShowing())
            sProgressDialog.dismiss();
        sProgressDialog = null;
        if (sContextHolder != null)
            sContextHolder.clear();
    }
}
