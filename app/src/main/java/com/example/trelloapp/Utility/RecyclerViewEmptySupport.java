package com.example.trelloapp.Utility;

import android.content.Context;

import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.example.trelloapp.Main.CustomRecyclerScrollViewListener;
import com.example.trelloapp.Main.MainFragment;

import static android.view.View.VISIBLE;

public class RecyclerViewEmptySupport extends RecyclerView {
    private View emptyView;

    private RecyclerView.AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            showEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            showEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            showEmptyView();
        }
    };
    private boolean hasFixedSize;


    public RecyclerViewEmptySupport(Context context) {
        super(context);
    }

    public void showEmptyView() {

        Adapter<?> adapter = getAdapter();
        if (adapter != null && emptyView != null) {
            if (adapter.getItemCount() == 0) {
                emptyView.setVisibility(VISIBLE);
                RecyclerViewEmptySupport.this.setVisibility(GONE);
            } else {
                emptyView.setVisibility(GONE);
                RecyclerViewEmptySupport.this.setVisibility(VISIBLE);
            }
        }

    }

    public RecyclerViewEmptySupport(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewEmptySupport(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
            observer.onChanged();
        }
    }

    public void setEmptyView(View v) {
        emptyView = v;
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        this.hasFixedSize = hasFixedSize;
    }

    public boolean getHasFixedSize() {
        return hasFixedSize;
    }

    public void addOnScrollListener(CustomRecyclerScrollViewListener customRecyclerScrollViewListener) {
    }

    public void removeOnScrollListener(CustomRecyclerScrollViewListener customRecyclerScrollViewListener) {
    }

    public void setAdapter(MainFragment.BasicListAdapter adapter) {
    }
}
