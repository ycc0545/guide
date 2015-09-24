package com.guide.base;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.guide.Consts;
import com.guide.action.Alarm;

import java.util.List;

public class Keeper {

    private static SharedPreferences sp = null;

    public static void init(Context c) {
        sp = c.getSharedPreferences(Consts.KEEP_XML, Context.MODE_PRIVATE);
    }

    public static void keepToken(String token) {
        sp.edit().putString(Consts.KEY_TOKEN, token)
                .commit();
    }

    public static String readToken() {
        return sp.getString(Consts.KEY_TOKEN, "");
    }

    public static void keepIsAlarmOn(boolean isAlarmOn) {
        sp.edit().putBoolean(Consts.KEY_IS_ALARM_ON, isAlarmOn).commit();
    }

    public static boolean isAlarmOn() {
        return sp.getBoolean(Consts.KEY_IS_ALARM_ON, true);
    }

    public static void keepAlarms(List<Alarm> alarms) {
        Gson gson = new Gson();
        String alarmsString = gson.toJson(alarms);
        sp.edit().putString(Consts.KEY_ALARMS, alarmsString).commit();

    }

    public static String readAlarms() {
        return sp.getString(Consts.KEY_ALARMS, "");
    }
}
