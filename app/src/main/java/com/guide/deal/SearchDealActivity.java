package com.guide.deal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.guide.MyApplication;
import com.guide.R;
import com.guide.base.BaseActivity;
import com.guide.base.VolleyRequest;
import com.guide.deal.model.Deal;
import com.guide.deal.model.DealListResult;
import com.guide.deal.model.SearchDealListRequest;
import com.guide.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 11/9/15.
 */
public class SearchDealActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivBack;
    private EditText mSearchEdit;
    private TextView mGoSearch;
    private RelativeLayout mSearchKeyLayout;
    private TextView mSearchTxt;
    private LinearLayout mOtherLayout;
    private RelativeLayout mHotDealLayout;
    private List<TextView> textViewList;
    private List<RelativeLayout.LayoutParams> layoutParamsList;
    private MyListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_search_deal);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        mSearchEdit = (EditText) findViewById(R.id.search_edit);
        mGoSearch = (TextView) findViewById(R.id.go_search);
        mSearchKeyLayout = (RelativeLayout) findViewById(R.id.search_key_layout);
        mSearchTxt = (TextView) findViewById(R.id.search_txt);
        mOtherLayout = (LinearLayout) findViewById(R.id.other_layout);
        mHotDealLayout = (RelativeLayout) findViewById(R.id.hot_deal_layout);

        mOtherLayout.setVisibility(View.GONE);
        textViewList = new ArrayList<>();
//        layoutParamsList = new ArrayList<>();
//        for (int i = 0; i < 8; ++i) {
//            textViewList.add(newTextView(i, "热门" + i));
//            layoutParamsList.add(new RelativeLayout.LayoutParams(200, 90));
//            if (i < 4) {
//                layoutParamsList.get(i).leftMargin = i * 220;
//            } else {
//                layoutParamsList.get(i).leftMargin = (i - 4) * 220;
//                layoutParamsList.get(i).topMargin = 110;
//            }
//
//            textViewList.get(i).setLayoutParams(layoutParamsList.get(i));
//            textViewList.get(i).setOnClickListener(this);
//        }
//        for (TextView textView : textViewList) {
//            mHotDealLayout.addView(textView);
//        }

        myListView = (MyListView) findViewById(R.id.find_deal_list);

        ivBack.setOnClickListener(this);
        mSearchKeyLayout.setOnClickListener(this);
        mGoSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.search_key_layout:
                mSearchKeyLayout.setVisibility(View.GONE);
                mSearchEdit.setText("");
                break;
            case R.id.go_search:
                if (!TextUtils.isEmpty(mSearchEdit.getText())) {
                    mSearchKeyLayout.setVisibility(View.VISIBLE);
                    mSearchTxt.setText(mSearchEdit.getText());
                    mOtherLayout.setVisibility(View.GONE);
                    loadData();
                }
                break;
            default:
                mSearchEdit.setText(((TextView) v).getText());
                mSearchKeyLayout.setVisibility(View.VISIBLE);
                mSearchTxt.setText(mSearchEdit.getText());
                mOtherLayout.setVisibility(View.GONE);
                loadData();
                break;
        }
    }

    private void loadData() {
        VolleyRequest.Callbacks<DealListResult> callbacks = new VolleyRequest.Callbacks<DealListResult>() {
            @Override
            public void onResponse(final DealListResult dealListResult) {
                if (dealListResult != null && dealListResult.isResCodeOK()) {
                    myListView.setAdapter(new DealListAdapter(SearchDealActivity.this, dealListResult.getDealList()));
                    myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Deal deal = (Deal) parent.getAdapter().getItem(position);
                            Intent intent = new Intent(SearchDealActivity.this, DealDetailActivity.class);
                            intent.putExtra("deal", deal);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("error", error.getCause().getMessage());
            }
        };
        SearchDealListRequest searchDealListRequest = new SearchDealListRequest(mSearchEdit.getText().toString(), callbacks);
        MyApplication.getInstance().addToRequestQueue(searchDealListRequest.createRequest());
    }

//    private TextView newTextView(int tag, String text) {
//        TextView textView = new TextView(this);
//        textView.setTag("hot" + tag);
//        textView.setText(text);
//        textView.setTextColor(Color.parseColor("#787878"));
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextSize(11);
//        textView.setBackgroundResource(R.drawable.bg_cornered_gray_stroke);
//        return textView;
//    }
}
