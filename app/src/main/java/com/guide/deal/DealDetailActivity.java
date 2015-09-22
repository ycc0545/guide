package com.guide.deal;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.guide.MyApplication;
import com.guide.R;
import com.guide.base.BaseActivity;
import com.guide.base.VolleyRequest;
import com.guide.deal.model.Deal;
import com.guide.group.model.GetGuideDetailByGuideIdRequest;
import com.guide.group.model.GetGuideDetailByGuideIdResult;
import com.guide.view.AutoScrollViewPager;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by mac on 2/9/15.
 */
public class DealDetailActivity extends BaseActivity {
    private AutoScrollViewPager viewPager;
    private CirclePageIndicator pageIndicator;
    private TextView mDealTitleTxt;
    private TextView yuan;
    private TextView dealPriceInt;
    private TextView dealPriceDec;
    private Button mContactGuideBtn;
    private TextView mDealDetailTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_detail);
        viewPager = (AutoScrollViewPager) findViewById(R.id.pager);
        pageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mDealTitleTxt = (TextView) findViewById(R.id.deal_title_txt);
        yuan = (TextView) findViewById(R.id.yuan);
        dealPriceInt = (TextView) findViewById(R.id.deal_price_int);
        dealPriceDec = (TextView) findViewById(R.id.deal_price_dec);
        mContactGuideBtn = (Button) findViewById(R.id.contact_guide_btn);
        mDealDetailTxt = (TextView) findViewById(R.id.deal_detail_txt);

        viewPager.setInterval(3000);
        viewPager.startAutoScroll();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            final Deal deal = (Deal) bundle.get("deal");
            if (deal.getIconUrl() != null) {
                viewPager.startAutoScroll();
                String[] urls = deal.getIconUrl().split(";");
                viewPager.setAdapter(new DealPicPagerAdapter(getSupportFragmentManager(), urls));
                pageIndicator.setViewPager(viewPager);
            }
//            Picasso.with(this).load(deal.getIconUrl()).into(ivDealDetail);
            mDealTitleTxt.setText(deal.getDealName());
            yuan.setText("￥");
            dealPriceInt.setText((String.valueOf(deal.getPrice()).split("\\.")[0]));
            dealPriceDec.setText("." + String.valueOf(deal.getPrice()).split("\\.")[1]);

            mContactGuideBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadGuideData(deal.getGuideId());
                }
            });
            mDealDetailTxt.setText(deal.getDesc());
        }
    }

    private void callGuide(final String mobile) {
        final Dialog dialog = new Dialog(DealDetailActivity.this, R.style.MyDialog);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.show();

        ((TextView) dialog.findViewById(R.id.dialog_info)).setText("确认拨打" + mobile + "？");

        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                String url = "tel:" + mobile;
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    private void loadGuideData(int guideId) {
        VolleyRequest.Callbacks<GetGuideDetailByGuideIdResult> callbacks = new VolleyRequest.Callbacks<GetGuideDetailByGuideIdResult>() {
            @Override
            public void onResponse(GetGuideDetailByGuideIdResult result) {
                if (result != null && result.isResCodeOK()) {
                    callGuide(result.getGuideInfo().getGuide().getMobile());
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("error", error.getCause().getMessage());
            }
        };
        GetGuideDetailByGuideIdRequest getGuideDetailByGuideIdRequest = new GetGuideDetailByGuideIdRequest(guideId, callbacks);
        MyApplication.getInstance().addToRequestQueue(getGuideDetailByGuideIdRequest.createRequest());
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPager.startAutoScroll();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewPager.stopAutoScroll();
    }
}
