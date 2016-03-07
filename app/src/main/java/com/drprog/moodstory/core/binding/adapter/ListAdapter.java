package com.drprog.moodstory.core.binding.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.drprog.moodstory.core.binding.handler.Model;
import com.hannesdorfmann.adapterdelegates.AbsDelegationAdapter;
import com.hannesdorfmann.adapterdelegates.AdapterDelegatesManager;

import java.util.List;

/**
 * RecyclerAdapter for using with data binding for items
 *
 */
public class ListAdapter<T extends Model> extends AbsDelegationAdapter<List<T>> {

    public ListAdapter(@NonNull AdapterDelegatesManager<List<T>> delegatesManager) {
        super(delegatesManager);
    }

    @Override
    public long getItemId(int position) {
        final T item = items.get(position);
        if (item instanceof Identified){
            final Long id = ((Identified)item).getId();
            if (id != null) return id;
        }
        return RecyclerView.NO_ID;
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

}