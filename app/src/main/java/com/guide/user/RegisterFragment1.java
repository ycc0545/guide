package com.guide.user;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.guide.CustomToast;
import com.guide.MyApplication;
import com.guide.R;
import com.guide.base.BaseFragment;
import com.guide.base.SimpleResult;
import com.guide.base.VolleyRequest;
import com.guide.guide.model.GetSmsVerifyCodeRequest;
import com.guide.guide.model.SmsCheckRequest;
import com.guide.guide.model.SmsCheckResult;
import com.guide.user.model.UserInfoResult;
import com.guide.user.model.VerifyMobileRegisterRequest;
import com.guide.utils.Utils;

public class RegisterFragment1 extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "SignUpVerify";
    private static final int MSG_COUNT_DOWN = 1;
    private Button mGetVerifyCodeBtn;
    private EditText mVerifyCodeEdit;
    private EditText mRealNameTxt;
    private EditText mPhoneNumEdit;
    private Handler mHandler;
    private int mCountDown;
    private Button mNextBtn;
    private CheckBox mCheckBox;

    private OnMobilePhoneVerifyListener listener;

    public static RegisterFragment1 newInstance() {
        return new RegisterFragment1();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OnMobilePhoneVerifyListener) activity;
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_COUNT_DOWN:
                        if (getActivity() == null) {
                            mHandler.removeMessages(MSG_COUNT_DOWN);
                            return;
                        }

                        mGetVerifyCodeBtn.setText(getString(R.string.count_down, mCountDown));
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

    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_1, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {

        mNextBtn = (Button) view.findViewById(R.id.next_btn);
        mNextBtn.setOnClickListener(this);
        setBtnEnable(false);

        mCheckBox = (CheckBox) view.findViewById(R.id.user_agreement_checkbox);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !TextUtils.isEmpty(mRealNameTxt.getText()) &&
                        !TextUtils.isEmpty(mPhoneNumEdit.getText())
                        && !TextUtils.isEmpty(mVerifyCodeEdit.getText())) {
                    setBtnEnable(true);
                } else {
                    setBtnEnable(false);
                }
            }
        });

        mGetVerifyCodeBtn = (Button) view.findViewById(R.id.get_code_btn);
        mGetVerifyCodeBtn.setOnClickListener(this);
        Utils.setBtnEnable(mGetVerifyCodeBtn, false);

        mRealNameTxt = (EditText) view.findViewById(R.id.real_name_edit);
        mPhoneNumEdit = (EditText) view.findViewById(R.id.user_phone_edit);
        mVerifyCodeEdit = (EditText) view.findViewById(R.id.verify_code_edit);

        mRealNameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) &&
                        !TextUtils.isEmpty(mPhoneNumEdit.getText())) {
                    Utils.setBtnEnable(mGetVerifyCodeBtn, true);
                } else {
                    Utils.setBtnEnable(mGetVerifyCodeBtn, false);
                }

                if (mCheckBox.isChecked() && !TextUtils.isEmpty(s) &&
                        !TextUtils.isEmpty(mVerifyCodeEdit.getText())) {
                    setBtnEnable(true);
                } else {
                    setBtnEnable(false);
                }

            }
        });

        mPhoneNumEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) &&
                        !TextUtils.isEmpty(mRealNameTxt.getText()) &&
                        11 == s.length()) {
                    Utils.setBtnEnable(mGetVerifyCodeBtn, true);
                } else {
                    Utils.setBtnEnable(mGetVerifyCodeBtn, false);
                }

                if (mCheckBox.isChecked() && !TextUtils.isEmpty(s) &&
                        !TextUtils.isEmpty(mVerifyCodeEdit.getText()) &&
                        !TextUtils.isEmpty(mRealNameTxt.getText())) {
                    setBtnEnable(true);
                } else {
                    setBtnEnable(false);
                }

            }
        });

        mVerifyCodeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mCheckBox.isChecked() && !TextUtils.isEmpty(s) &&
                        !TextUtils.isEmpty(mPhoneNumEdit.getText()) &&
                        !TextUtils.isEmpty(mRealNameTxt.getText())) {
                    setBtnEnable(true);
                } else {
                    setBtnEnable(false);
                }
            }
        });

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_btn:
                verify();
                break;
            case R.id.get_code_btn:
                verifyMobile();
                break;
        }
    }

    private void verifyMobile() {
        String phoneNum = String.valueOf(mPhoneNumEdit.getText());
        if (TextUtils.isEmpty(phoneNum) || !TextUtils.isDigitsOnly(phoneNum)) {
            CustomToast.makeText(getActivity(), "手机号码格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }

        VolleyRequest.Callbacks<UserInfoResult> callbacks = new VolleyRequest.Callbacks<UserInfoResult>() {
            @Override
            public void onResponse(UserInfoResult result) {
                if (result.getTourist() != null && result.isResCodeOK()) {
                    CustomToast.makeText(getActivity(), "手机号码已注册", Toast.LENGTH_SHORT).show();
                } else {
                    getVerifyCode();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Utils.showVolleyError(getActivity(), error);
            }
        };

        VerifyMobileRegisterRequest request = new VerifyMobileRegisterRequest(callbacks, phoneNum);
        MyApplication.getInstance().addToRequestQueue(request.createRequest());

    }

    private void getVerifyCode() {
        String phoneNum = String.valueOf(mPhoneNumEdit.getText());
        if (!TextUtils.isEmpty(phoneNum) && TextUtils.isDigitsOnly(phoneNum)) {


            getVerifyCode(phoneNum);
        } else {
            CustomToast.makeText(getActivity(), R.string.string_not_num, Toast.LENGTH_SHORT).show();
        }
    }

    private void verify() {
        String phoneNum = String.valueOf(mPhoneNumEdit.getText());
        String verifyNum = String.valueOf(mVerifyCodeEdit.getText());

        if (TextUtils.isEmpty(phoneNum) || !TextUtils.isDigitsOnly(phoneNum)) {
            CustomToast.makeText(getActivity(), R.string.string_not_num, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(verifyNum) || !TextUtils.isDigitsOnly(verifyNum)) {
            CustomToast.makeText(getActivity(), R.string.sms_code_not_num, Toast.LENGTH_SHORT).show();
            return;
        } else {
            verify(phoneNum, verifyNum);
        }

    }

    private void getVerifyCode(String phoneNum) {

        VolleyRequest.Callbacks<SimpleResult> getSmsVerifyCodeRequest = new VolleyRequest.Callbacks<SimpleResult>() {
            @Override
            public void onResponse(SimpleResult simpleResult) {
                if (simpleResult != null) {
                    if (simpleResult.isResCodeOK()) {
                        CustomToast.makeText(getActivity(), R.string.get_verify_code_ok, Toast.LENGTH_LONG).show();
                        Utils.setBtnEnable(mGetVerifyCodeBtn, false);
                        mCountDown = 60;
                        mHandler.sendEmptyMessageDelayed(MSG_COUNT_DOWN, 1000);
                    } else {
                        CustomToast.makeText(getActivity(), simpleResult.getErrMsg(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onError(VolleyError error) {
                Utils.showVolleyError(getActivity(), error);
            }
        };
        GetSmsVerifyCodeRequest request = new GetSmsVerifyCodeRequest(getSmsVerifyCodeRequest, phoneNum);
        MyApplication.getInstance().addToRequestQueue(request.createRequest());

    }

    private void enableVerifyButton() {
        mHandler.removeMessages(MSG_COUNT_DOWN);
        Utils.setBtnEnable(mGetVerifyCodeBtn, true);
        mGetVerifyCodeBtn.setText("获取验证码");
    }

    /**
     * verify code from server
     *
     * @param mobileNum
     * @param verifyCode
     */
    private void verify(final String mobileNum, String verifyCode) {

        VolleyRequest.Callbacks<SmsCheckResult> smsCheckResult = new VolleyRequest.Callbacks<SmsCheckResult>() {
            @Override
            public void onResponse(SmsCheckResult simpleResult) {

                if (simpleResult != null) {
                    if (simpleResult.isResCodeOK()) {
                        String uuid = simpleResult.getUUID();
                        if (listener != null) {
                            Bundle bundle = new Bundle();
                            bundle.putString("mobile", mobileNum);
                            bundle.putString("realName", mRealNameTxt.getText().toString());
                            bundle.putString("uuid", uuid);
                            listener.onMobilePhoneVerify(bundle);
                        }
                    } else {
                        CustomToast.makeText(getActivity(), simpleResult.getErrMsg(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onError(VolleyError error) {
                Utils.showVolleyError(getActivity(), error);
            }
        };

        SmsCheckRequest request = new SmsCheckRequest(smsCheckResult, mobileNum, verifyCode);
        MyApplication.getInstance().addToRequestQueue(request.createRequest());
    }

    @Override
    public CharSequence getTitle() {
        return "用户注册(1/2)";
    }

    public interface OnMobilePhoneVerifyListener {
        void onMobilePhoneVerify(Bundle bundle);
    }

    private void setBtnEnable(boolean enable) {
        if (enable) {
            mNextBtn.setBackgroundResource(R.drawable.btn_register_blue);
        } else {
            mNextBtn.setBackgroundResource(R.drawable.btn_register_light_blue);
        }
        mNextBtn.setEnabled(enable);
    }
}
