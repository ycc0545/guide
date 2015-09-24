package com.guide.guide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.guide.CustomToast;
import com.guide.MyApplication;
import com.guide.R;
import com.guide.base.BaseActivity;
import com.guide.base.SimpleResult;
import com.guide.base.VolleyRequest;
import com.guide.group.model.Guide;
import com.guide.guide.model.EditGuideRequest;
import com.guide.guide.model.GetSmsVerifyCodeRequest;
import com.guide.guide.model.SmsCheckRequest;
import com.guide.guide.model.SmsCheckResult;
import com.guide.guide.model.VerifyMobileRegisterRequest;
import com.guide.user.model.UserInfoResult;
import com.guide.utils.Utils;

/**
 * Created by Xingkai on 14/9/15.
 */
public class EditGuideActivity extends BaseActivity {
    private static final int MSG_COUNT_DOWN = 1;
    private EditText mMobileEdit;
    private Button mGetCodeBtn;
    private EditText mVerifyCodeEdit;
    private EditText mOldPwdEdit;
    private EditText mNewPwdEdit;
    private EditText mRePwdEdit;
    private LinearLayout mLayoutMobile;
    private LinearLayout mLayoutPassword;
    private Handler mHandler;
    private int mCountDown;

    private Guide guide;
    private String entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_COUNT_DOWN:
                        if (EditGuideActivity.this == null) {
                            mHandler.removeMessages(MSG_COUNT_DOWN);
                            return;
                        }

                        mGetCodeBtn.setText(getString(R.string.count_down, mCountDown));
                        mCountDown--;
                        if (mCountDown > 0) {
                            mHandler.sendEmptyMessageDelayed(MSG_COUNT_DOWN, 1000);
                        } else {
                            enableVerifyButton();
                        }
                        break;
                }
            }
        };
        setDisplayActionRightButtonEnabled(true, "保存");
        mMobileEdit = (EditText) findViewById(R.id.mobile_edit);
        mGetCodeBtn = (Button) findViewById(R.id.get_code_btn);
        mVerifyCodeEdit = (EditText) findViewById(R.id.verify_code_edit);
        mOldPwdEdit = (EditText) findViewById(R.id.old_pwd_edit);
        mNewPwdEdit = (EditText) findViewById(R.id.new_pwd_edit);
        mRePwdEdit = (EditText) findViewById(R.id.re_pwd_edit);

        mVerifyCodeEdit.setText("123456");
        mLayoutMobile = (LinearLayout) findViewById(R.id.layout_mobile);
        mLayoutPassword = (LinearLayout) findViewById(R.id.layout_password);

        mGetCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyMobile();
            }
        });
        Utils.setBtnEnable(mGetCodeBtn, false);

        mMobileEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) &&
                        11 == s.length()) {
                    Utils.setBtnEnable(mGetCodeBtn, true);
                } else {
                    Utils.setBtnEnable(mGetCodeBtn, false);
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            guide = (Guide) bundle.get("guide");
            entry = bundle.getString("entry");
            switch (entry) {
                case "mobile":
                    setActionBarTitle("修改手机号");
                    mLayoutMobile.setVisibility(View.VISIBLE);
                    break;
                case "password":
                    setActionBarTitle("修改密码");
                    mLayoutPassword.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    protected void onRightBtnClick() {
        String value1;
        switch (entry) {
            case "mobile":
                value1 = mMobileEdit.getText().toString();
                verify(value1, "123456");
                break;
            case "password":
                value1 = mNewPwdEdit.getText().toString();
                loadData(5, "pwdRepeat", "oldPassword", value1, mRePwdEdit.getText().toString(), mOldPwdEdit.getText().toString());
                break;
        }
    }

    private void loadData(int type, String key2, String key3, final String value1, String value2, String value3) {
        VolleyRequest.Callbacks<SimpleResult> callbacks = new VolleyRequest.Callbacks<SimpleResult>() {
            @Override
            public void onResponse(SimpleResult result) {
                if (result != null && result.isResCodeOK()) {
                    CustomToast.makeText(EditGuideActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("entry", entry);
                    intent.putExtra(entry, value1);
                    setResult(RESULT_OK, intent);
                    EditGuideActivity.this.finish();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Utils.showVolleyError(EditGuideActivity.this, error);
            }
        };
        EditGuideRequest editGuideRequest = new EditGuideRequest(type, entry, key2, key3, value1, value2, value3, callbacks);
        MyApplication.getInstance().addToRequestQueue(editGuideRequest.createRequest());
    }

    private void verifyMobile() {
        String phoneNum = String.valueOf(mMobileEdit.getText());
        if (TextUtils.isEmpty(phoneNum) || !TextUtils.isDigitsOnly(phoneNum)) {
            CustomToast.makeText(EditGuideActivity.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }

        VolleyRequest.Callbacks<UserInfoResult> callbacks = new VolleyRequest.Callbacks<UserInfoResult>() {
            @Override
            public void onResponse(UserInfoResult result) {
                if (result.getTourist() != null && result.isResCodeOK()) {
                    CustomToast.makeText(EditGuideActivity.this, "手机号码已注册", Toast.LENGTH_SHORT).show();
                } else {
                    getVerifyCode();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Utils.showVolleyError(EditGuideActivity.this, error);
            }
        };

        VerifyMobileRegisterRequest request = new VerifyMobileRegisterRequest(callbacks, phoneNum);
        MyApplication.getInstance().addToRequestQueue(request.createRequest());

    }

    private void getVerifyCode() {
        String phoneNum = String.valueOf(mMobileEdit.getText());
        if (!TextUtils.isEmpty(phoneNum) && TextUtils.isDigitsOnly(phoneNum)) {


            getVerifyCode(phoneNum);
        } else {
            CustomToast.makeText(this, R.string.string_not_num, Toast.LENGTH_SHORT).show();
        }
    }

    private void getVerifyCode(String phoneNum) {

        VolleyRequest.Callbacks<SimpleResult> getSmsVerifyCodeRequest = new VolleyRequest.Callbacks<SimpleResult>() {
            @Override
            public void onResponse(SimpleResult simpleResult) {
                if (simpleResult != null) {
                    if (simpleResult.isResCodeOK()) {
                        CustomToast.makeText(EditGuideActivity.this, R.string.get_verify_code_ok, Toast.LENGTH_LONG).show();
                        Utils.setBtnEnable(mGetCodeBtn, false);
                        mCountDown = 60;
                        mHandler.sendEmptyMessageDelayed(MSG_COUNT_DOWN, 1000);
                    } else {
                        CustomToast.makeText(EditGuideActivity.this, simpleResult.getErrMsg(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onError(VolleyError error) {
                Utils.showVolleyError(EditGuideActivity.this, error);
            }
        };
        GetSmsVerifyCodeRequest request = new GetSmsVerifyCodeRequest(getSmsVerifyCodeRequest, phoneNum);
        MyApplication.getInstance().addToRequestQueue(request.createRequest());

    }

    private void enableVerifyButton() {
        mHandler.removeMessages(MSG_COUNT_DOWN);
        Utils.setBtnEnable(mGetCodeBtn, true);
        mGetCodeBtn.setText("获取验证码");
    }

    private void verify(final String mobileNum, String verifyCode) {

        VolleyRequest.Callbacks<SmsCheckResult> smsCheckResult = new VolleyRequest.Callbacks<SmsCheckResult>() {
            @Override
            public void onResponse(SmsCheckResult simpleResult) {

                if (simpleResult != null) {
                    if (simpleResult.isResCodeOK()) {
                        loadData(2, "", "", mobileNum, "", "");
                    } else {
                        CustomToast.makeText(EditGuideActivity.this, simpleResult.getErrMsg(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onError(VolleyError error) {
                Utils.showVolleyError(EditGuideActivity.this, error);
            }
        };

        SmsCheckRequest request = new SmsCheckRequest(smsCheckResult, mobileNum, verifyCode);
        MyApplication.getInstance().addToRequestQueue(request.createRequest());
    }
}
