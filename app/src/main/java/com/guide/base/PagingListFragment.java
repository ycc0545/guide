package com.guide.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;

import com.android.volley.VolleyError;
import com.guide.MyApplication;

import java.util.List;

public abstract class PagingListFragment<T> extends PullToRefreshListFragment implements AbsListView.OnScrollListener, VolleyRequest.Callbacks<List<T>> {
    private static final String TAG = "pagingList";
    private boolean isLoadingMore;
    private boolean isRefreshing;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setOnScrollListener(this);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLoadingMore = false;
        isRefreshing = false;
        refresh();
    }

    public void loadData(int offset) {
        VolleyRequest request = createRequest(offset);
        MyApplication.getInstance().addToRequestQueue(request.createRequest());
    }

    protected abstract VolleyRequest createRequest(int offset);

    protected abstract BaseListAdapter createListAdapter(List<T> data);

    protected int getLimit() {
        return 20;
    }


    @Override
    protected void refresh() {
        if (!isRefreshing && !isLoadingMore) {
            isRefreshing = true;
            loadData(0);
        }


    }


    @Override
    public void onResponse(List<T> data) {
        isLoadingMore = false;
        if (isRefreshing) {
            isRefreshing = false;
            onRefreshStopped();

            if (getListAdapter() != null) {
                ((BaseListAdapter) getListAdapter()).setData(null);
            }
        }

        if (getListAdapter() == null) {
            setListAdapter(createListAdapter(data));
        } else {
            setListAdapter(createListAdapter(data));
        }
    }


    @Override
    public void onError(VolleyError error) {
        isRefreshing = false;
        isLoadingMore = false;
        onRefreshFailed();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && getListAdapter().getCount() > 0 && getListAdapter().getCount() % getLimit() == 0) {
            if (!isLoadingMore && !isRefreshing) {
                isLoadingMore = true;
                loadData(getListAdapter().getCount());
            }
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }


}
