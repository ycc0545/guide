package com.guide.base;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.text.SimpleDateFormat;
import java.util.Date;

import roboguice.fragment.RoboFragment;

public class BaseFragment extends RoboFragment implements ITitle {
    protected static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    protected static final int REQUEST_LOGIN = 1;

    @Inject
    @Named("ptr_time")
    protected SharedPreferences ptrTimePref;

    @Override
    public void onResume() {
        super.onResume();
        //Update action bar title
//        updateActionBarTitle();

    }

    protected String getLastUpdatedStr() {
        long lastUpdated = ptrTimePref.getLong(ptrTimeKey(), -1);
        String lastUpdatedStr;
        if (lastUpdated == -1) {
            lastUpdatedStr = "无";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            lastUpdatedStr = dateFormat.format(new Date(lastUpdated));
        }

        return lastUpdatedStr;

    }

    protected void saveLastUpdated(long lastUpdated) {
        ptrTimePref.edit().putLong(ptrTimeKey(), lastUpdated).commit();

    }
//
//    protected void updateActionBarTitle() {
//        // MainActivity的fragment包含子fragment，这些子fragment可能没有重写getTitle方法，导致标题为空
//        if (getActivity() instanceof MainActivity) {
//            return;
//        }
//        if (getActivity() instanceof BaseActivity) {
//            BaseActivity activity = (BaseActivity) getActivity();
//            if (activity != null) {
//                activity.setActionBarTitle(getTitle());
//            }
//        }
//
//    }

    @Override
    public CharSequence getTitle() {
        return getActivity().getTitle();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == Activity.RESULT_OK) {
                onLoginOK();
            } else {
                onLoginFail();
            }
        }

    }

    protected void onLoginOK() {

    }

    protected void onLoginFail() {

    }

    protected String ptrTimeKey() {
        return "last_updated_" + getClass().getName();
    }
}
