package com.guide.user;

import android.os.Bundle;

import com.guide.R;
import com.guide.base.BaseActivity;

/**
 * Created by mac on 9/9/15.
 */
public class RegisterActivity extends BaseActivity implements RegisterFragment1.OnMobilePhoneVerifyListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        if (savedInstanceState == null) {
            RegisterFragment1 fragment = RegisterFragment1.newInstance();
            onFragmentShow(fragment);
            getSupportFragmentManager().
                    beginTransaction().
                    add(R.id.content, fragment).
                    commit();
        }
    }

    @Override
    public void onMobilePhoneVerify(Bundle bundle) {
        RegisterFragment2 fragment = RegisterFragment2.newInstance();
        fragment.setArguments(bundle);
        onFragmentShow(fragment);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.content, fragment).
                addToBackStack(null).
                commitAllowingStateLoss();

    }
}
