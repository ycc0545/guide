package com.guide.group;

import android.os.Bundle;
import android.widget.TextView;

import com.guide.R;
import com.guide.base.BaseActivity;
import com.guide.user.model.Tourist;

/**
 * Created by mac on 2/9/15.
 */
public class TouristDetailActivity extends BaseActivity {
    private TextView mTouristInfoTxt;
    private TextView mIdCardTxt;
    private TextView mTouristMobileTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_detail);
        mTouristInfoTxt = (TextView) findViewById(R.id.tourist_info_txt);
        mIdCardTxt = (TextView) findViewById(R.id.tourist_id_card_txt);
        mTouristMobileTxt = (TextView) findViewById(R.id.tourist_mobile_txt);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Tourist tourist = (Tourist) bundle.get("tourist");
            setActionBarTitle(tourist.getName());
            mTouristInfoTxt.setText(tourist.getName() + "  " +
                    (tourist.getGender() == 1 ? "男" : "女"));
            mIdCardTxt.setText(tourist.getIdCard() == null ? "身份证：未认证" : "身份证：" + tourist.getIdCard());
            mTouristMobileTxt.setText("手机号：" + tourist.getMobile());
            // TODO: 23/9/15 最近地理位置
        }
    }
}
