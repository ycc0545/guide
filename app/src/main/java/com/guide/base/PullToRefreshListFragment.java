package com.guide.base;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.guide.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.Date;

public class PullToRefreshListFragment extends BaseFragment implements IRefresh {
    private static final String TAG = "pulltorefresh";
    protected View mLoadingProgress;
    private PullToRefreshListView mPullToRefreshLayout;
    private ListAdapter mListAdapter;
    private Handler mHandler;

    protected PullToRefreshListView getListView() {
        return mPullToRefreshLayout;
    }

    protected ListAdapter getListAdapter() {
        return mListAdapter;
    }

    protected void setListAdapter(ListAdapter adapter) {
        mListAdapter = adapter;
        mPullToRefreshLayout.setAdapter(mListAdapter);
    }

    protected PullToRefreshListView getmPullToRefreshLayout() {
        return mPullToRefreshLayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pull_to_refresh, null);
        mPullToRefreshLayout = (PullToRefreshListView) view.findViewById(R.id.list);
//        mLoadingProgress = view.findViewById(R.id.progress_bar);
//        Utils.createProgressAnimation(getActivity(), mLoadingProgress);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHandler = new Handler();

        // Set a listener to be invoked when the list should be refreshed.
        mPullToRefreshLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick((ListView) parent, view, position, id);
            }
        });
        mPullToRefreshLayout.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                onRefreshStarted();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });


        getmPullToRefreshLayout().getLoadingLayoutProxy().setLastUpdatedLabel(getString(R.string.last_update_time, getLastUpdatedStr()));

    }

    public void onListItemClick(ListView l, View v, int position, long id) {
    }


    @Override
    public void onRefreshStopped() {
//        mLoadingProgress.setVisibility(View.GONE);

        final long lastUpdated = System.currentTimeMillis();
        saveLastUpdated(lastUpdated);
        Date date = new Date(lastUpdated);
        getmPullToRefreshLayout().getLoadingLayoutProxy().setLastUpdatedLabel(getString(R.string.last_update_time, dateFormat.format(date)));
        getmPullToRefreshLayout().getLoadingLayoutProxy().setRefreshingLabel(getString(R.string.pull_to_refresh_ok_label));

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isAdded()) {
                    getmPullToRefreshLayout().getLoadingLayoutProxy().setRefreshingLabel(getString(R.string.pull_to_refresh_refreshing_label));
                }
                getmPullToRefreshLayout().onRefreshComplete();
            }
        }, 500);
    }

    protected void refresh() {

    }

    @Override
    public void onRefreshStarted() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        }, 500);

    }

    @Override
    public void onRefreshFailed() {
//        Utils.setProgressInfo(mLoadingProgress, R.string.network_err);
        getmPullToRefreshLayout().onRefreshComplete();
    }
}
