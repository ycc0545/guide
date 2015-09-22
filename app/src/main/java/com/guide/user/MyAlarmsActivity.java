package com.guide.user;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guide.CustomToast;
import com.guide.MyApplication;
import com.guide.R;
import com.guide.base.BaseActivity;
import com.guide.base.Keeper;
import com.guide.base.VolleyRequest;
import com.guide.user.model.Alarm;
import com.guide.user.model.MyAlarmsListRequest;
import com.guide.view.AlarmReceiver;
import com.guide.view.MyListView;
import com.guide.view.SwitchView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2/9/15.
 */
public class MyAlarmsActivity extends BaseActivity {
    private LinearLayout mLayoutAlarmStatus;
    private MyListView myListView;
    private SwitchView switchView;
    private List<Alarm> alarmList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_alarms);
        mLayoutAlarmStatus = (LinearLayout) findViewById(R.id.layout_alarm_status);
        switchView = new SwitchView(this);
        mLayoutAlarmStatus.addView(switchView);
        myListView = (MyListView) findViewById(R.id.list);

        loadData();

        switchView.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean isChecked) {
                if (isChecked) {
                    Keeper.keepIsAlarmOn(true);
                    openAlarms();
                    myListView.setVisibility(View.VISIBLE);
                    Type listType = new TypeToken<List<Alarm>>() {
                    }.getType();
                    List<Alarm> alarms = new Gson().fromJson(Keeper.readAlarms(), listType);
                    myListView.setAdapter(new MyAlarmsListAdapter(MyAlarmsActivity.this, alarms));
                } else {
                    closeAlarms();
                    Keeper.keepIsAlarmOn(false);
                    myListView.setVisibility(View.GONE);
                    myListView.setAdapter(null);
                }
            }
        });
        mLayoutAlarmStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchView.onClick(switchView);
            }
        });

        if (Keeper.isAlarmOn()) {
            switchView.setChecked(true);
        } else {
            switchView.setChecked(false);
        }

    }

    private void loadData() {
        VolleyRequest.Callbacks<List<Alarm>> callbacks = new VolleyRequest.Callbacks<List<Alarm>>() {
            @Override
            public void onResponse(List<Alarm> alarms) {
                if (alarms != null) {
                    alarmList = alarms;
                    if (Keeper.isAlarmOn()) {
                        openAlarms();
                    } else {
                        closeAlarms();
                    }
                    Keeper.keepAlarms(alarms);
                    myListView.setAdapter(new MyAlarmsListAdapter(MyAlarmsActivity.this, alarms));
                }
            }

            @Override
            public void onError(VolleyError error) {
                CustomToast.makeText(MyAlarmsActivity.this, error.getCause().getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
        MyAlarmsListRequest request = new MyAlarmsListRequest(callbacks);
        MyApplication.getInstance().addToRequestQueue(request.createRequest());
    }

    private void closeAlarms() {
        for (Alarm alarm : alarmList) {
            Intent intent = new Intent(MyAlarmsActivity.this, AlarmReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(
                    MyAlarmsActivity.this, alarm.getActionId(), intent, 0);

            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            am.cancel(sender);
            CustomToast.makeText(this, "闹钟关" + alarm.getActionId(), Toast.LENGTH_SHORT).show();
        }
    }

    private void openAlarms() {
        for (Alarm alarm : alarmList) {
            Intent intent = new Intent(MyAlarmsActivity.this, AlarmReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(
                    MyAlarmsActivity.this, alarm.getActionId(), intent, 0);

            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, Long.valueOf(alarm.getRemindTime()), sender);
            CustomToast.makeText(this, "闹钟开" + alarm.getActionId(), Toast.LENGTH_SHORT).show();
        }
    }
}
