package com.drprog.moodstory.core.image;

import android.view.View;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by d_rom on 08.12.2015.
 */
public class ImageProgressListener implements RequestListener {
    private final View mProgressBar;

    public ImageProgressListener(View _progressBar) {
        mProgressBar = _progressBar;
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
        mProgressBar.setVisibility(View.GONE);
        return false;
    }

    @Override
    public boolean onResourceReady(Object resource, Object model, Target target,
            boolean isFromMemoryCache, boolean isFirstResource) {
        mProgressBar.setVisibility(View.GONE);
        return false;
    }
}
