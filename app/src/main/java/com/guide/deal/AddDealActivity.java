package com.guide.deal;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.guide.Consts;
import com.guide.CustomToast;
import com.guide.R;
import com.guide.base.BaseActivity;
import com.guide.base.GsonProvider;
import com.guide.base.SimpleResult;
import com.guide.utils.HttpPostUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by mac on 20/9/15.
 */
public class AddDealActivity extends BaseActivity implements View.OnClickListener {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 HH:mm E");

    private EditText mDealNameEdit;
    private TextView mType1;
    private TextView mType2;
    private TextView mType3;
    private TextView mType4;
    private EditText mDealPriceEdit;
    private EditText mDealInfoEdit;
    private ImageView ivDealPic1;
    private ImageView ivDealPic2;
    private ImageView ivDealPic3;
    private Button mConfirmBtn;

    private PopupWindow popupWindow;

    private JSONArray types = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deal);
        setDisplayActionRightButtonEnabled(true, "确定");
        mDealNameEdit = (EditText) findViewById(R.id.deal_name_edit);
        mType1 = (TextView) findViewById(R.id.type_1);
        mType2 = (TextView) findViewById(R.id.type_2);
        mType3 = (TextView) findViewById(R.id.type_3);
        mType4 = (TextView) findViewById(R.id.type_4);
        mDealPriceEdit = (EditText) findViewById(R.id.deal_price_edit);
        mDealInfoEdit = (EditText) findViewById(R.id.deal_info_edit);
        ivDealPic1 = (ImageView) findViewById(R.id.iv_deal_pic_1);
        ivDealPic2 = (ImageView) findViewById(R.id.iv_deal_pic_2);
        ivDealPic3 = (ImageView) findViewById(R.id.iv_deal_pic_3);
        mConfirmBtn = (Button) findViewById(R.id.confirm_btn);

        mType1.setOnClickListener(this);
        mType2.setOnClickListener(this);
        mType3.setOnClickListener(this);
        mType4.setOnClickListener(this);

        ivDealPic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });

        mConfirmBtn.setOnClickListener(this);
    }

    protected void showPopupWindow() {

        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_popup, null);
        TextView mTakePhoto = (TextView) contentView.findViewById(R.id.take_photo);
        TextView mChoosePhoto = (TextView) contentView.findViewById(R.id.choose_photo);
        TextView mCancel = (TextView) contentView.findViewById(R.id.cancel);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePopupWindow();
            }
        });

        popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        backgroundAlpha(0.5f);
        popupWindow.setOnDismissListener(new poponDismissListener());
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.popup_anim_style);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

    protected void hidePopupWindow() {
        backgroundAlpha(1f);
        popupWindow.dismiss();
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            backgroundAlpha(1f);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.type_1:
                if (!mType1.isSelected()) {
                    mType1.setSelected(true);
                    mType1.setTextColor(getResources().getColor(R.color.blue));
                } else {
                    mType1.setSelected(false);
                    mType1.setTextColor(getResources().getColor(R.color.black4));
                }
                break;
            case R.id.type_2:
                if (!mType2.isSelected()) {
                    mType2.setSelected(true);
                    mType2.setTextColor(getResources().getColor(R.color.blue));
                } else {
                    mType2.setSelected(false);
                    mType2.setTextColor(getResources().getColor(R.color.black4));
                }
                break;
            case R.id.type_3:
                if (!mType3.isSelected()) {
                    mType3.setSelected(true);
                    mType3.setTextColor(getResources().getColor(R.color.blue));
                } else {
                    mType3.setSelected(false);
                    mType3.setTextColor(getResources().getColor(R.color.black4));
                }
                break;
            case R.id.type_4:
                if (!mType4.isSelected()) {
                    mType4.setSelected(true);
                    mType4.setTextColor(getResources().getColor(R.color.blue));
                } else {
                    mType4.setSelected(false);
                    mType4.setTextColor(getResources().getColor(R.color.black4));
                }
                break;
            case R.id.confirm_btn:
                if (mType1.isSelected()) {
                    types.put(1);
                }
                if (mType2.isSelected()) {
                    types.put(2);
                }
                if (mType3.isSelected()) {
                    types.put(3);
                }
                if (mType4.isSelected()) {
                    types.put(99);
                }
                new Thread(runnable).start();
                break;
        }
    }

    @Override
    protected void onRightBtnClick() {
        if (mType1.isSelected()) {
            types.put(1);
        }
        if (mType2.isSelected()) {
            types.put(2);
        }
        if (mType3.isSelected()) {
            types.put(3);
        }
        if (mType4.isSelected()) {
            types.put(99);
        }
        new Thread(runnable).start();
    }

    private SimpleResult confirmAddDeal() {
        JSONObject object = new JSONObject();
        try {
            object.put("dealName", mDealNameEdit.getText().toString());
            object.put("price", Double.valueOf(mDealPriceEdit.getText().toString()));
            object.put("types", types);
            if (!TextUtils.isEmpty(mDealInfoEdit.getText())) {
                object.put("desc", mDealInfoEdit.getText());
            }
            object.put("iconUrl", "http://i2.szhomeimg.com/o/2014/12/17/1217204145369473.JPG;http://i2.szhomeimg.com/o/2014/12/17/12172019489875005.JPG;http://static3.orstatic.com/userphoto/photo/7/5LJ/013T4I0691CE4FC242F51Blv.jpg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObject jsonObject = HttpPostUtil.httpPostData(Consts.API_URL + "/deals", object);
        Gson gson = GsonProvider.getInstance().get();
        SimpleResult result = gson.fromJson(jsonObject, new TypeToken<SimpleResult>() {
        }.getType());
        return result;

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            SimpleResult result = (SimpleResult) bundle.get("result");
            if (result.isResCodeOK()) {
                AddDealActivity.this.finish();
                CustomToast.makeText(AddDealActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            } else {
                CustomToast.makeText(AddDealActivity.this, result.getErrMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putSerializable("result", confirmAddDeal());
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    };
}
