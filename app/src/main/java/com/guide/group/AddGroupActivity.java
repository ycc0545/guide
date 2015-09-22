package com.guide.group;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.guide.Consts;
import com.guide.CustomToast;
import com.guide.MyApplication;
import com.guide.R;
import com.guide.base.BaseActivity;
import com.guide.base.GsonProvider;
import com.guide.base.VolleyRequest;
import com.guide.group.model.AddGroupResult;
import com.guide.guide.model.GetGuideByCodeRequest;
import com.guide.guide.model.GetGuideByCodeResult;
import com.guide.utils.HttpPostUtil;
import com.guide.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mac on 20/9/15.
 */
public class AddGroupActivity extends BaseActivity implements View.OnClickListener {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 HH:mm E");

    private EditText mGroupNameEdit;
    private EditText mCompanyNameEdit;
    private EditText mStartDateEdit;
    private EditText mEndDateEdit;
    private EditText mMyCodeEdit;
    private LinearLayout mAddGuideBtn;
    private EditText mSecondCodeEdit;
    private Button mRemoveSecondBtn;
    private EditText mThirdCodeEdit;
    private Button mRemoveThirdBtn;
    private Button mConfirmBtn;

    private Date startDate;
    private Date endDate;

    private int guideCount = 1;
    private JSONArray guides = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        setDisplayActionRightButtonEnabled(true, "确定");
        mGroupNameEdit = (EditText) findViewById(R.id.group_name_edit);
        mCompanyNameEdit = (EditText) findViewById(R.id.company_name_edit);
        mStartDateEdit = (EditText) findViewById(R.id.start_date);
        mEndDateEdit = (EditText) findViewById(R.id.end_date);
        mMyCodeEdit = (EditText) findViewById(R.id.my_code_edit);
        mSecondCodeEdit = (EditText) findViewById(R.id.second_code_edit);
        mThirdCodeEdit = (EditText) findViewById(R.id.third_code_edit);
        mAddGuideBtn = (LinearLayout) findViewById(R.id.add_guide_btn);
        mRemoveSecondBtn = (Button) findViewById(R.id.remove_second_btn);
        mRemoveThirdBtn = (Button) findViewById(R.id.remove_third_btn);
        mConfirmBtn = (Button) findViewById(R.id.confirm_btn);

        mStartDateEdit.setOnClickListener(this);
        mEndDateEdit.setOnClickListener(this);
        mAddGuideBtn.setOnClickListener(this);
        mRemoveSecondBtn.setOnClickListener(this);
        mRemoveThirdBtn.setOnClickListener(this);

        mConfirmBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_date:
                startDate = new Date(System.currentTimeMillis());
                mStartDateEdit.setText(dateFormat.format(startDate));
                break;
            case R.id.end_date:
                endDate = new Date(System.currentTimeMillis());
                mEndDateEdit.setText(dateFormat.format(endDate));
                break;
            case R.id.add_guide_btn:
                if (findViewById(R.id.layout_second_code).getVisibility() == View.GONE) {
                    guideCount++;
                    findViewById(R.id.layout_second_code).setVisibility(View.VISIBLE);
                } else if (findViewById(R.id.layout_third_code).getVisibility() == View.GONE) {
                    guideCount++;
                    findViewById(R.id.layout_third_code).setVisibility(View.VISIBLE);
                }
                break;
            case R.id.remove_second_btn:
                guideCount--;
                findViewById(R.id.layout_second_code).setVisibility(View.GONE);
                break;
            case R.id.remove_third_btn:
                guideCount--;
                findViewById(R.id.layout_third_code).setVisibility(View.GONE);
                break;
            case R.id.confirm_btn:
                for (int i = 0; i < guideCount; ++i) {
                    getGuideByCode(i);
                }
                break;
        }
    }

    @Override
    protected void onRightBtnClick() {
        for (int i = 0; i < guideCount; ++i) {
            getGuideByCode(i);
        }
    }

    private void getGuideByCode(final int i) {
        String code = "";
        switch (i) {
            case 0:
                code = mMyCodeEdit.getText().toString();
                break;
            case 1:
                code = mSecondCodeEdit.getText().toString();
                break;
            case 2:
                code = mThirdCodeEdit.getText().toString();
                break;
        }
        VolleyRequest.Callbacks<GetGuideByCodeResult> callbacks = new VolleyRequest.Callbacks<GetGuideByCodeResult>() {
            @Override
            public void onResponse(GetGuideByCodeResult result) {
                if (result.isResCodeOK()) {
                    if (result.getGuide() != null) {
                        try {
                            guides.put(i, result.getGuide().getGuideId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (i == guideCount - 1) {
                            new Thread(runnable).start();
                        }
                    }
                }
            }

            @Override
            public void onError(VolleyError error) {
                Utils.showVolleyError(AddGroupActivity.this, error);
            }
        };
        GetGuideByCodeRequest request = new GetGuideByCodeRequest(code, callbacks);
        MyApplication.getInstance().addToRequestQueue(request.createRequest());
    }

    private AddGroupResult confirmAddGroup() {
        JSONObject object = new JSONObject();
        try {
            object.put("name", mGroupNameEdit.getText().toString());
            object.put("company", mCompanyNameEdit.getText().toString());
            object.put("startDate", String.valueOf(startDate.getTime()));
            object.put("endDate", String.valueOf(endDate.getTime()));
            object.put("guides", guides);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObject jsonObject = HttpPostUtil.httpPostData(Consts.API_URL + "/groups", object);
        Gson gson = GsonProvider.getInstance().get();
        AddGroupResult result = gson.fromJson(jsonObject, new TypeToken<AddGroupResult>() {
        }.getType());
        return result;

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            AddGroupResult result = (AddGroupResult) bundle.get("result");
            if (result.isResCodeOK()) {
                AddGroupActivity.this.finish();
                CustomToast.makeText(AddGroupActivity.this, "创建成功", Toast.LENGTH_SHORT).show();
            } else {
                CustomToast.makeText(AddGroupActivity.this, result.getErrMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putSerializable("result", confirmAddGroup());
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    };
}
