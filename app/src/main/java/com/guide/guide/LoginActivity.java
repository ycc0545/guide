package com.guide.guide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.guide.MainActivity;
import com.guide.MyApplication;
import com.guide.R;
import com.guide.base.BaseActivity;
import com.guide.base.LoginManager;
import com.guide.base.VolleyRequest;
import com.guide.guide.model.LoginRequest;
import com.guide.guide.model.LoginResult;
import com.guide.user.RegisterActivity;
import com.guide.utils.Utils;

/**
 * Created by mac on 9/9/15.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mPhoneEdit;
    private EditText mPasswordEdit;
    private Button mLoginBtn;
    private TextView mFastRegisterTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        setDisplayHomeAsUpEnabled(false);
        if (LoginManager.getInstance().isLogin()) {
            this.finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        mPhoneEdit = (EditText) findViewById(R.id.user_phone_edit);
        mPasswordEdit = (EditText) findViewById(R.id.password_edit);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mFastRegisterTxt = (TextView) findViewById(R.id.fast_register_txt);

        mLoginBtn.setOnClickListener(this);
        mFastRegisterTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                VolleyRequest.Callbacks<LoginResult> callbacks = new VolleyRequest.Callbacks<LoginResult>() {
                    @Override
                    public void onResponse(LoginResult loginResult) {
                        if (loginResult != null && loginResult.isResCodeOK()) {
                            LoginManager.getInstance().setToken(loginResult.getToken());
                            LoginActivity.this.finish();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        Utils.showVolleyError(LoginActivity.this, error);
                    }
                };
                LoginRequest loginRequest = new LoginRequest(
                        mPhoneEdit.getText().toString(),
                        mPasswordEdit.getText().toString(),
                        callbacks);
                MyApplication.getInstance().addToRequestQueue(loginRequest.createRequest());
                break;
            case R.id.fast_register_txt:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }

    }
}
