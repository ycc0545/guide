package com.guide.deal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.guide.MyApplication;
import com.guide.R;
import com.guide.base.BaseFragment;
import com.guide.base.IRefresh;
import com.guide.base.VolleyRequest;
import com.guide.deal.model.Deal;
import com.guide.deal.model.DealListRequest;
import com.guide.deal.model.DealListResult;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.Date;

public class DealListFragment extends BaseFragment implements IRefresh, View.OnClickListener {

    private static final int[] TAB_LAYOUT_IDS = {R.id.deal_tab_1, R.id.deal_tab_2, R.id.deal_tab_3, R.id.deal_tab_4};
    private static final int[] TAB_LINE_IDS = {R.id.deal_tab_line_1, R.id.deal_tab_line_2, R.id.deal_tab_line_3, R.id.deal_tab_line_4};

    private PullToRefreshListView mPullToRefreshLayout;
    private Handler mHandler;
    private boolean isRefreshing;
    private int dealType;

    public static DealListFragment newInstance() {
        return new DealListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final long lastUpdated = System.currentTimeMillis();
        saveLastUpdated(lastUpdated);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deal, container, false);
        mPullToRefreshLayout = (PullToRefreshListView) view.findViewById(R.id.list);
//        mLoadingProgress = view.findViewById(R.id.progress_bar);
//        Utils.createProgressAnimation(getActivity(), mLoadingProgress);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dealType = 1;
        isRefreshing = false;
        refresh();
        mHandler = new Handler();

        for (int id : TAB_LAYOUT_IDS) {
            view.findViewById(id).setOnClickListener(this);
        }

        // Set a listener to be invoked when the list should be refreshed.
        mPullToRefreshLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Deal deal = (Deal) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getActivity(), DealDetailActivity.class);
                intent.putExtra("deal", deal);
                startActivity(intent);
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


        mPullToRefreshLayout.getLoadingLayoutProxy().setLastUpdatedLabel(getString(R.string.last_update_time, getLastUpdatedStr()));

    }

    private void loadData() {
        VolleyRequest.Callbacks<DealListResult> callbacks = new VolleyRequest.Callbacks<DealListResult>() {
            @Override
            public void onResponse(final DealListResult dealListResult) {
                if (isRefreshing) {
                    isRefreshing = false;
                    onRefreshStopped();
                    if (dealListResult != null && dealListResult.isResCodeOK()) {
                        mPullToRefreshLayout.setAdapter(new DealListAdapter(getActivity(), dealListResult.getDealList()));
                    }
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("error", error.getCause().getMessage());
            }
        };
        DealListRequest dealListRequest = new DealListRequest(dealType, callbacks);
        MyApplication.getInstance().addToRequestQueue(dealListRequest.createRequest());
    }

    private void refresh() {
        if (!isRefreshing) {
            isRefreshing = true;
            loadData();
        }
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
    public void onRefreshStopped() {
        final long lastUpdated = System.currentTimeMillis();
        saveLastUpdated(lastUpdated);
        Date date = new Date(lastUpdated);
        mPullToRefreshLayout.getLoadingLayoutProxy().setLastUpdatedLabel(getString(R.string.last_update_time, dateFormat.format(date)));
        mPullToRefreshLayout.getLoadingLayoutProxy().setRefreshingLabel(getString(R.string.pull_to_refresh_ok_label));

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isAdded()) {
                    mPullToRefreshLayout.getLoadingLayoutProxy().setRefreshingLabel(getString(R.string.pull_to_refresh_refreshing_label));
                }
                mPullToRefreshLayout.onRefreshComplete();
            }
        }, 500);
    }

    @Override
    public void onRefreshFailed() {
        mPullToRefreshLayout.onRefreshComplete();
    }

    private void clearLines() {
        for (int id : TAB_LINE_IDS) {
            getView().findViewById(id).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        clearLines();
        switch (v.getId()) {
            case R.id.deal_tab_1:
                dealType = 1;
                refresh();
                getView().findViewById(TAB_LINE_IDS[0]).setVisibility(View.VISIBLE);
                break;
            case R.id.deal_tab_2:
                dealType = 2;
                refresh();
                getView().findViewById(TAB_LINE_IDS[1]).setVisibility(View.VISIBLE);
                break;
            case R.id.deal_tab_3:
                dealType = 3;
                refresh();
                getView().findViewById(TAB_LINE_IDS[2]).setVisibility(View.VISIBLE);
                break;
            case R.id.deal_tab_4:
                dealType = 4;
                refresh();
                getView().findViewById(TAB_LINE_IDS[3]).setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public CharSequence getTitle() {
        return "好品";
    }
}
