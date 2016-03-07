package com.drprog.moodstory.core.binding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Recycler ViewHolder to use with data binding
 * Created by roman.donchenko on 18.01.2016
 */
public class BindingHolder<VB extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private VB binding;

    public static <VB extends ViewDataBinding> BindingHolder<VB> newInstance(
            @LayoutRes int layoutId, LayoutInflater inflater,
            @Nullable ViewGroup parent, boolean attachToParent) {

        final VB vb = DataBindingUtil.inflate(inflater, layoutId, parent, attachToParent);
        return new BindingHolder<>(vb);
    }

    public BindingHolder(VB _binding) {
        super(_binding.getRoot());
        binding = _binding;
    }

    public VB getBinding() {
        return binding;
    }
}