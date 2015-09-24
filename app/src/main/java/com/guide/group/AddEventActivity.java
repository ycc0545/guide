package com.guide.group;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.guide.CustomToast;
import com.guide.MyApplication;
import com.guide.R;
import com.guide.base.BaseActivity;
import com.guide.base.SimpleResult;
import com.guide.base.VolleyRequest;
import com.guide.group.model.AddEventRequest;
import com.guide.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mac on 20/9/15.
 */
public class AddEventActivity extends BaseActivity implements View.OnClickListener {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 HH:mm E");

    private EditText mDateEdit;
    private EditText mLocationEdit;
    private EditText mEventInfoEdit;
    private Button mConfirmBtn;

    private Date date;
    private int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        setDisplayActionRightButtonEnabled(true, "完成");
        mDateEdit = (EditText) findViewById(R.id.date_edit);
        mLocationEdit = (EditText) findViewById(R.id.location_edit);
        mEventInfoEdit = (EditText) findViewById(R.id.event_info_edit);
        mConfirmBtn = (Button) findViewById(R.id.confirm_btn);

        mDateEdit.setOnClickListener(this);
        mLocationEdit.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            groupId = bundle.getInt("groupId");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_edit:
                date = new Date(System.currentTimeMillis());
                mDateEdit.setText(dateFormat.format(date));
                break;
            case R.id.location_edit:
                // TODO: 22/9/15
                mLocationEdit.setText("立方庭");
                break;
            case R.id.confirm_btn:


                addEvent();
                break;
        }
    }

    @Override
    protected void onRightBtnClick() {
        addEvent();
    }

    private void addEvent() {
        VolleyRequest.Callbacks<SimpleResult> callbacks = new VolleyRequest.Callbacks<SimpleResult>() {
            @Override
            public void onResponse(SimpleResult simpleResult) {
                if (simpleResult.isResCodeOK()) {
                    CustomToast.makeText(AddEventActivity.this, "创建成功", Toast.LENGTH_SHORT).show();
                    AddEventActivity.this.finish();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Utils.showVolleyError(AddEventActivity.this, error);
            }
        };
        AddEventRequest request = new AddEventRequest(groupId, String.valueOf(date.getTime()), mLocationEdit.getText().toString(),
                mEventInfoEdit.getText().toString(), callbacks);
        MyApplication.getInstance().addToRequestQueue(request.createRequest());
    }
}
