package com.guide.base;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.android.volley.VolleyError;
import com.guide.MyApplication;
import com.guide.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.Date;

import roboguice.util.Ln;

public abstract class PullToRefreshFragment<D> extends BaseDetailFragment
        implements
        VolleyRequest.Callbacks<D> {

    public PullToRefreshScrollView pullToRefreshLayout;
    private boolean isPullToRefresh;
    private Handler mHandler = new Handler();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isEmpty()) {
            setState(STATE_LOADING);
        } else {
            setState(STATE_OK);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData(false);
    }

    @Override
    protected View createContentView() {
        pullToRefreshLayout = (PullToRefreshScrollView) getLayoutInflater(null).inflate(R.layout.layout_base_pull_to_refresh, null);
        pullToRefreshLayout.setPullToRefreshOverScrollEnabled(false);
        if (!refreshable()) {
            pullToRefreshLayout.setMode(PullToRefreshBase.Mode.DISABLED);
        }
        final ScrollView scrollView = (ScrollView) pullToRefreshLayout.findViewById(R.id.scrollview);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                onScroll(scrollView.getScrollY());
            }
        });
        pullToRefreshLayout.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                onRefreshStarted(refreshView);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }
        });
        pullToRefreshLayout.getLoadingLayoutProxy().setLastUpdatedLabel(getString(R.string.last_update_time, getLastUpdatedStr()));


        int contentView = onCreateContentView(scrollView);
        scrollView.addView(getLayoutInflater(null).inflate(contentView, null));
        return pullToRefreshLayout;
    }

    protected abstract int onCreateContentView(ViewGroup viewGroup);

    protected boolean refreshable() {
        return true;
    }


    protected abstract boolean isEmpty();


    protected abstract VolleyRequest<D> createVolleyRequest(boolean refresh);

    protected abstract void onDataGot(D d);


    public void loadData(boolean refresh) {
        if (isEmpty()) {
            setState(STATE_LOADING);
        }
        VolleyRequest volleyRequest = createVolleyRequest(refresh);
        MyApplication.getInstance().addToRequestQueue(volleyRequest.createRequest());

    }

    protected void refresh() {
        loadData(true);
    }


    private void onRefreshComplete() {
        isPullToRefresh = false;
        final long lastUpdated = System.currentTimeMillis();
        saveLastUpdated(lastUpdated);
        pullToRefreshLayout.getLoadingLayoutProxy().setLastUpdatedLabel(getString(R.string.last_update_time, dateFormat.format(new Date(lastUpdated))));
        pullToRefreshLayout.getLoadingLayoutProxy().setRefreshingLabel(getString(R.string.pull_to_refresh_ok_label));

        // TODO
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isAdded()) {
                    pullToRefreshLayout.getLoadingLayoutProxy().setRefreshingLabel(getString(R.string.pull_to_refresh_refreshing_label));
                }
                pullToRefreshLayout.onRefreshComplete();
            }
        }, 500);
    }


    protected void UIReactOnException(VolleyError e, D data) {
        if (isEmpty()) {
            setState(STATE_ERROR);
        }
        Throwable cause = e.getCause();
        if (cause != null) {
            Ln.e(cause.getMessage());
        }
    }

    protected void UIReactOnEmpty() {
        if (isEmpty()) {
            setState(STATE_EMPTY);
        }
//        Utils.showError(getActivity(), "获取数据失败");
    }

    public void onRefreshStarted(View view) {
        if (!isPullToRefresh) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isPullToRefresh = true;
                    refresh();
                }
            }, 500);

        }
    }

    @Override
    public void onResponse(D d) {
        onRefreshComplete();
        if (d != null) {
            setState(STATE_OK);
        } else {
            UIReactOnEmpty();
        }

        onDataGot(d);

    }

    @Override
    public void onError(VolleyError error) {
        onRefreshComplete();
        UIReactOnException(error, null);

    }

    protected void onScroll(int y) {

    }
}
