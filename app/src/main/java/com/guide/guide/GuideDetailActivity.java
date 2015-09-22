package com.guide.guide;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.guide.MyApplication;
import com.guide.R;
import com.guide.base.BaseActivity;
import com.guide.base.LoginManager;
import com.guide.base.SimpleResult;
import com.guide.base.VolleyRequest;
import com.guide.group.model.Guide;
import com.guide.guide.model.LogoutRequest;
import com.guide.utils.ActivityListUtil;
import com.guide.utils.Utils;

/**
 * Created by Xingkai on 14/9/15.
 */
public class GuideDetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView mMobileTxt;
    private RelativeLayout mLayoutMobile;
    private RelativeLayout mLayoutPassword;

    private Guide guide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_detail);
        mMobileTxt = (TextView) findViewById(R.id.mobile_txt);
        mLayoutMobile = (RelativeLayout) findViewById(R.id.layout_mobile);
        mLayoutPassword = (RelativeLayout) findViewById(R.id.layout_password);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            guide = (Guide) bundle.get("guide");
            mMobileTxt.setText(guide.getMobile());
        }

        mLayoutMobile.setOnClickListener(this);
        mLayoutPassword.setOnClickListener(this);
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(GuideDetailActivity.this, R.style.MyDialog);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();

                ((TextView) dialog.findViewById(R.id.dialog_info)).setText("确定退出登录？");

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
                        logout();
                    }
                });
            }
        });
    }

    private void logout() {
        VolleyRequest.Callbacks<SimpleResult> callbacks = new VolleyRequest.Callbacks<SimpleResult>() {
            @Override
            public void onResponse(SimpleResult result) {
                if (result.isResCodeOK()) {
                    LoginManager.getInstance().setToken("");
                    GuideDetailActivity.this.finish();
                    ActivityListUtil.getInstance().cleanActivityList();
                    startActivity(new Intent(GuideDetailActivity.this, LoginActivity.class));
                }
            }

            @Override
            public void onError(VolleyError error) {
                Utils.showVolleyError(GuideDetailActivity.this, error);
            }
        };
        LogoutRequest request = new LogoutRequest(callbacks);
        MyApplication.getInstance().addToRequestQueue(request.createRequest());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(GuideDetailActivity.this, EditGuideActivity.class);
        intent.putExtra("guide", guide);
        switch (v.getId()) {
            case R.id.layout_mobile:
                intent.putExtra("entry", "mobile");
                break;
            case R.id.layout_password:
                intent.putExtra("entry", "password");
                break;
        }
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String entry = data.getExtras().getString("entry");
            if (entry == "mobile") {
                mMobileTxt.setText(data.getExtras().getString(entry));
            }
        }
    }
}
