package com.guide.user;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import com.guide.guide.model.RegisterRequest;
import com.guide.utils.Utils;

import cn.jpush.android.api.JPushInterface;

public class RegisterFragment2 extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "sign2";
    private EditText mUserPwdEdit;
    private EditText mRePwdEdit;
    private String uuid;
    private String realName;
    private String mobile;
    private String mPassword;
    private String mRePwd;
    private Button mConfirmBtn;
    private CheckBox mCheckBox;

    public static RegisterFragment2 newInstance() {
        return new RegisterFragment2();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Bundle bundle = getArguments();
        if (bundle != null) {

            uuid = getArguments().getString("uuid");
            realName = getArguments().getString("realName");
            mobile = getArguments().getString("mobile");
            if (uuid == null) {
                Log.e(TAG, "uuid error");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_2, container, false);
        mConfirmBtn = (Button) view.findViewById(R.id.confirm_btn);
        mConfirmBtn.setOnClickListener(this);
        setBtnEnable(false);

        mCheckBox = (CheckBox) view.findViewById(R.id.user_agreement_checkbox);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !TextUtils.isEmpty(mUserPwdEdit.getText()) &&
                        !TextUtils.isEmpty(mRePwdEdit.getText())) {
                    setBtnEnable(true);
                } else {
                    setBtnEnable(false);
                }
            }
        });


        mUserPwdEdit = (EditText) view.findViewById(R.id.pwd_edit);
        mRePwdEdit = (EditText) view.findViewById(R.id.re_pwd_edit);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mCheckBox.isChecked() && !TextUtils.isEmpty(mUserPwdEdit.getText()) && !(TextUtils.isEmpty(mRePwdEdit.getText()))) {
                    setBtnEnable(true);
                } else {
                    setBtnEnable(false);
                }
            }
        };
        mUserPwdEdit.addTextChangedListener(watcher);
        mRePwdEdit.addTextChangedListener(watcher);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        setPassword();
    }

    private void setPassword() {
        mPassword = mUserPwdEdit.getText().toString();
        mRePwd = mRePwdEdit.getText().toString();

        if (TextUtils.isEmpty(mPassword)) {
            CustomToast.makeText(getActivity(), "密码为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mRePwd)) {
            CustomToast.makeText(getActivity(), "密码为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mPassword.equals(mRePwd)) {
            VolleyRequest.Callbacks<SimpleResult> callbacks = new VolleyRequest.Callbacks<SimpleResult>() {
                @Override
                public void onResponse(SimpleResult simpleResult) {
                    if (simpleResult != null) {
                        if (simpleResult.isResCodeOK()) {
                            CustomToast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
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

            RegisterRequest registerRequest = new RegisterRequest(uuid, mobile, realName, mobile, mPassword, mRePwd,
                    JPushInterface.getRegistrationID(getActivity()), callbacks);
            MyApplication.getInstance().addToRequestQueue(registerRequest.createRequest());
        } else {
            CustomToast.makeText(getActivity(), "密码输入有误", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public CharSequence getTitle() {
        return "用户注册(2/2)";
    }

    private void setBtnEnable(boolean enable) {
        if (enable) {
            mConfirmBtn.setBackgroundResource(R.drawable.btn_register_blue);
        } else {
            mConfirmBtn.setBackgroundResource(R.drawable.btn_register_light_blue);
        }
        mConfirmBtn.setEnabled(enable);
    }
}
