package com.guide.view;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.guide.CustomToast;
import com.guide.R;
import com.guide.base.BaseActivity;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Xingkai on 15/9/15.
 */

public class ShowAlarmActivity extends BaseActivity {
    private PowerManager.WakeLock mWakelock;
    private SoundPool soundPool;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.setContentView(R.layout.action_dialog);
        dialog.show();

        ((ImageView) dialog.findViewById(R.id.iv_action_dialog)).setImageResource(R.drawable.bg_alarm_dialog);
        ((TextView) dialog.findViewById(R.id.action_info_txt)).setText("该起床了！！！");
        ((TextView) dialog.findViewById(R.id.cancel)).setText("15分钟后再响");


        dialog.findViewById(R.id.action_address_txt).setVisibility(View.GONE);

        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                soundPool.stop(1);
                soundPool.release();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date(System.currentTimeMillis()));
                calendar.add(Calendar.MINUTE, 15);
                ShowAlarmActivity.this.finish();
                Intent intent = new Intent(ShowAlarmActivity.this, AlarmReceiver.class);
                PendingIntent sender = PendingIntent.getBroadcast(
                        ShowAlarmActivity.this, 0, intent, 0);

                // Schedule the alarm!
                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                CustomToast.makeText(ShowAlarmActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                soundPool.stop(1);
                soundPool.release();
                ShowAlarmActivity.this.finish();
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.tourist");
                startActivity(launchIntent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, this.getComponentName().getShortClassName());
        if (mWakelock != null) mWakelock.acquire();
        playSound();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWakelock != null) {
            mWakelock.release();
        }
    }

    private void playSound() {
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(this, R.raw.alarm, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId, 1, 1, 0, -1, 1);
            }
        });
    }
}