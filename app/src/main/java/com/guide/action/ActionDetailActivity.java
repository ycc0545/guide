package com.guide.action;

import android.content.Intent;
import android.location.Location;
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
import com.guide.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mac on 2/9/15.
 */
public class ActionDetailActivity extends BaseActivity implements View.OnClickListener {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm E");
    private TextView mContentTxt;
    private TextView mTimeTxt;
    private TextView mAddressTxt;
    private TextView mMoreInfoTxt;
    private Button mCancelBtn;
    private Button mConfirmBtn;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_detail);
        mContentTxt = (TextView) findViewById(R.id.content_txt);
        mTimeTxt = (TextView) findViewById(R.id.time_txt);
        mAddressTxt = (TextView) findViewById(R.id.address_txt);
        mMoreInfoTxt = (TextView) findViewById(R.id.more_info_txt);
        mCancelBtn = (Button) findViewById(R.id.cancel);
        mConfirmBtn = (Button) findViewById(R.id.confirm);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            item = (Item) bundle.get("item");
            switch (item.getType()) {
                case 1:
                    setActionBarTitle("通知详情");
                    mCancelBtn.setVisibility(View.GONE);
                    mConfirmBtn.setVisibility(View.GONE);
                    mMoreInfoTxt.setVisibility(View.GONE);
                    break;
                case 2:
                    setActionBarTitle("点名详情");
                    break;
                case 3:
                    setActionBarTitle("叫早闹钟详情");
                    break;
            }

            mContentTxt.setText(item.getContent());
            Date date = new Date(Long.valueOf(item.getRemindTime()));
            mTimeTxt.setText(dateFormat.format(date));
            mAddressTxt.setText(item.getLocation());
            // TODO: 16/9/15 完成操作文案
            if (item.getStatus() != 1) {
                mCancelBtn.setVisibility(View.GONE);
                mConfirmBtn.setVisibility(View.GONE);
                mMoreInfoTxt.setText("您已完成操作。\n操作时间：" + dateFormat.format(new Date(System.currentTimeMillis())));
            } else {
                mMoreInfoTxt.setText("您还未完成确认操作，请自己阅读内容，知晓后点击确认。");
                mCancelBtn.setOnClickListener(this);
                mConfirmBtn.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                ActionDetailActivity.this.finish();
                break;
            case R.id.confirm:
                confirmAction();
                break;
        }
    }

    private void confirmAction() {
        Location location = Utils.getLocation(this);
        if (location != null) {
            VolleyRequest.Callbacks<GetActionResult> callbacks = new VolleyRequest.Callbacks<GetActionResult>() {
                @Override
                public void onResponse(GetActionResult result) {
                    if (result != null && result.isResCodeOK()) {
                        Log.i("confirm", "success");
                        Intent intent = new Intent(ActionDetailActivity.this, ActionDetailActivity.class);
                        intent.putExtra("item", result.getAction());
                        startActivity(intent);
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    Utils.showVolleyError(ActionDetailActivity.this, error);
                }
            };
            ConfirmActionRequest request = new ConfirmActionRequest(item.getType(), item.getActionId(),
                    location.getLongitude(), location.getLatitude(), callbacks);
            MyApplication.getInstance().addToRequestQueue(request.createRequest());
        }
    }
}
