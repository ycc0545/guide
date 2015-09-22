package com.guide.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.guide.MyApplication;
import com.guide.R;
import com.guide.base.BaseFragment;
import com.guide.base.VolleyRequest;
import com.guide.guide.GuideDetailActivity;
import com.guide.user.model.Tourist;
import com.guide.user.model.UserInfoResult;
import com.guide.user.model.UserRequest;

/**
 * Created by mac on 2/9/15.
 */
public class UserFragment extends BaseFragment implements View.OnClickListener {
    private RelativeLayout mLayoutUserDetail;
    private ImageView ivUser;
    private TextView mUserNameTxt;
    private TextView mIdCardTxt;
    private TextView mUserPhoneTxt;
    private RelativeLayout mLayoutMyFollows;
    private RelativeLayout mLayoutMyAlarms;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLayoutUserDetail = (RelativeLayout) view.findViewById(R.id.layout_user_detail);
        ivUser = (ImageView) view.findViewById(R.id.iv_user);
        mUserNameTxt = (TextView) view.findViewById(R.id.user_name_txt);
        mIdCardTxt = (TextView) view.findViewById(R.id.user_id_card_txt);
        mUserPhoneTxt = (TextView) view.findViewById(R.id.user_phone_txt);
        mLayoutMyFollows = (RelativeLayout) view.findViewById(R.id.layout_my_follows);
        mLayoutMyAlarms = (RelativeLayout) view.findViewById(R.id.layout_my_alarms);

        mLayoutUserDetail.setOnClickListener(this);
        mLayoutMyFollows.setOnClickListener(this);
        mLayoutMyAlarms.setOnClickListener(this);

        loadData();
    }

    private void loadData() {
        VolleyRequest.Callbacks<UserInfoResult> callbacks = new VolleyRequest.Callbacks<UserInfoResult>() {
            @Override
            public void onResponse(UserInfoResult userInfoResult) {
                if (userInfoResult != null && userInfoResult.isResCodeOK()) {
                    final Tourist tourist = userInfoResult.getTourist();
                    mUserNameTxt.setText(tourist.getName());
                    if (tourist.getIdCard() == null) {
                        mIdCardTxt.setText("身份证：未认证");
                    } else {
                        mIdCardTxt.setText("身份证：" + tourist.getIdCard());
                    }
                    mUserPhoneTxt.setText("手机号：" + tourist.getMobile());
                    mLayoutUserDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), GuideDetailActivity.class);
                            intent.putExtra("tourist", tourist);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        };
        UserRequest userRequest = new UserRequest(callbacks);
        MyApplication.getInstance().addToRequestQueue(userRequest.createRequest());
    }

    @Override
    public CharSequence getTitle() {
        return "我的";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_my_follows:
                startActivity(new Intent(getActivity(), MyFollowsActivity.class));
                break;
            case R.id.layout_my_alarms:
                startActivity(new Intent(getActivity(), MyAlarmsActivity.class));
                break;
        }
    }
}
