package com.guide.view;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.guide.CustomToast;
import com.guide.MyApplication;
import com.guide.R;
import com.guide.action.ActionDetailActivity;
import com.guide.action.ConfirmActionRequest;
import com.guide.action.GetActionByNotificationIdRequest;
import com.guide.action.GetActionResult;
import com.guide.action.Item;
import com.guide.base.BaseActivity;
import com.guide.base.VolleyRequest;
import com.guide.utils.Utils;

/**
 * Created by Xingkai on 15/9/15.
 */

public class ShowNotificationActivity extends BaseActivity {
    private PowerManager.WakeLock mWakelock;
    private Item item;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        final Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            loadData(bundle.getString("id"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, this.getComponentName().getShortClassName());
        if (mWakelock != null) mWakelock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWakelock != null) {
            mWakelock.release();
        }
    }

    //通过NotificationId获取action
    private void loadData(final String msgId) {
        VolleyRequest.Callbacks<GetActionResult> callbacks = new VolleyRequest.Callbacks<GetActionResult>() {
            @Override
            public void onResponse(GetActionResult result) {
                if (result.isResCodeOK()) {
                    if (result.getAction() != null) {
                        item = result.getAction();
                        showDialog();
                    } else {
                        CustomToast.makeText(ShowNotificationActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(VolleyError error) {
                Utils.showVolleyError(ShowNotificationActivity.this, error);
            }
        };
        GetActionByNotificationIdRequest request = new GetActionByNotificationIdRequest(msgId, callbacks);
        MyApplication.getInstance().addToRequestQueue(request.createRequest());
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.setContentView(R.layout.action_dialog);
        dialog.show();

        switch (item.getType()) {
            case 1://通知
                ((ImageView) dialog.findViewById(R.id.iv_action_dialog)).setImageResource(R.drawable.bg_notification_dialog);
                ((TextView) dialog.findViewById(R.id.action_info_txt)).setText(item.getContent());
                ((TextView) dialog.findViewById(R.id.action_address_txt)).setText(item.getLocation());
                break;
            case 2://点名
                ((ImageView) dialog.findViewById(R.id.iv_action_dialog)).setImageResource(R.drawable.bg_named_dialog);
                ((TextView) dialog.findViewById(R.id.action_info_txt)).setText(item.getContent());
                ((TextView) dialog.findViewById(R.id.action_address_txt)).setText(item.getLocation());
                break;
            case 3://叫早
                ((ImageView) dialog.findViewById(R.id.iv_action_dialog)).setImageResource(R.drawable.bg_alarm_dialog);
                dialog.findViewById(R.id.cancel).setVisibility(View.GONE);
                dialog.findViewById(R.id.action_address_txt).setVisibility(View.GONE);

                ViewGroup.LayoutParams params = dialog.findViewById(R.id.confirm).getLayoutParams();
                params.width = Utils.dp2px(ShowNotificationActivity.this, 240);
                dialog.findViewById(R.id.confirm).setLayoutParams(params);
                ((TextView) dialog.findViewById(R.id.action_info_txt)).setText(item.getContent());
                break;
        }


        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                if (item.getType() == 4) {//再响
                    ShowNotificationActivity.this.finish();
                    Intent intent = new Intent(ShowNotificationActivity.this, AlarmReceiver.class);
                    PendingIntent sender = PendingIntent.getBroadcast(
                            ShowNotificationActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    // Schedule the alarm!
                    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                    am.set(AlarmManager.RTC_WAKEUP, Long.valueOf(item.getRemindTime()) + 10 * 1000, sender);
                    CustomToast.makeText(ShowNotificationActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                } else {
                    ShowNotificationActivity.this.finish();
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.tourist");
                    startActivity(launchIntent);
                }
            }
        });

        dialog.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

                confirmAction();
                if (item.getType() == 3) {//设置闹钟
                    ShowNotificationActivity.this.finish();
                    Intent intent = new Intent(ShowNotificationActivity.this, AlarmReceiver.class);
                    PendingIntent sender = PendingIntent.getBroadcast(
                            ShowNotificationActivity.this, 0, intent, 0);

                    // Schedule the alarm!
                    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                    am.set(AlarmManager.RTC_WAKEUP, Long.valueOf(item.getRemindTime()), sender);
                    CustomToast.makeText(ShowNotificationActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                } else {
                    ShowNotificationActivity.this.finish();
                    Intent intent = new Intent(ShowNotificationActivity.this, ActionDetailActivity.class);
                    intent.putExtra("item", item);
                    startActivity(intent);
                }
            }
        });
    }

    private void confirmAction() {
        Location location = Utils.getLocation(this);
        if (location != null) {
            VolleyRequest.Callbacks<GetActionResult> callbacks = new VolleyRequest.Callbacks<GetActionResult>() {
                @Override
                public void onResponse(GetActionResult result) {
                    if (result != null && result.isResCodeOK()) {
                        Log.i("confirm", "success");
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    Utils.showVolleyError(ShowNotificationActivity.this, error);
                }
            };
            ConfirmActionRequest request = new ConfirmActionRequest(item.getType(), item.getActionId(),
                    location.getLongitude(), location.getLatitude(), callbacks);
            MyApplication.getInstance().addToRequestQueue(request.createRequest());
        }
    }
}
