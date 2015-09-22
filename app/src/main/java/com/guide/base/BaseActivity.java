package com.guide.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.Key;
import com.guide.R;

import java.util.HashMap;
import java.util.Map;

import roboguice.RoboGuice;
import roboguice.activity.event.OnConfigurationChangedEvent;
import roboguice.activity.event.OnContentChangedEvent;
import roboguice.activity.event.OnCreateEvent;
import roboguice.activity.event.OnDestroyEvent;
import roboguice.activity.event.OnNewIntentEvent;
import roboguice.activity.event.OnPauseEvent;
import roboguice.activity.event.OnRestartEvent;
import roboguice.activity.event.OnResumeEvent;
import roboguice.activity.event.OnStartEvent;
import roboguice.activity.event.OnStopEvent;
import roboguice.event.EventManager;
import roboguice.inject.ContentViewListener;
import roboguice.inject.RoboInjector;
import roboguice.util.RoboContext;


public class BaseActivity extends ActionBarActivity implements RoboContext {

    protected static final int REQUEST_LOGIN = 1;
    private static final String TAG = "base";

    protected EventManager eventManager;
    protected HashMap<Key<?>, Object> scopedObjects = new HashMap<Key<?>, Object>();

    @Inject
    ContentViewListener ignored; // BUG find a better place to put this
    private boolean mIsBackground = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final RoboInjector injector = RoboGuice.getInjector(this);
        eventManager = injector.getInstance(EventManager.class);
        injector.injectMembersWithoutViews(this);
        super.onCreate(savedInstanceState);
        eventManager.fire(new OnCreateEvent(savedInstanceState));
        initActionBar();
    }

    protected void initActionBar() {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);

        ((TextView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title)).setText(getTitle());
        getSupportActionBar().getCustomView().findViewById(R.id.action_bar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().getCustomView().findViewById(R.id.action_bar_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlusActionClick();
            }
        });

        getSupportActionBar().getCustomView().findViewById(R.id.action_bar_right_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRightBtnClick();
            }
        });
    }

    public void setActionBarTitle(CharSequence title) {
        ((TextView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title)).setText(title);
    }

    protected void onPlusActionClick() {

    }

    protected void onRightBtnClick() {
    }

    protected void setDisplayHomeAsUpEnabled(boolean enabled) {
        getSupportActionBar().getCustomView().findViewById(R.id.action_bar_back).setVisibility(enabled ? View.VISIBLE : View.GONE);
    }

    protected void setDisplayPlusActionEnabled(boolean enabled, int index) {
        ImageView imageView = (ImageView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_plus);
        imageView.setVisibility(enabled ? View.VISIBLE : View.GONE);
        switch (index) {
            case 0:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_group));
                break;
            case 1:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_white));
                break;
            case 2:
                imageView.setVisibility(View.GONE);
                break;
            case 5:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_refresh));
                break;
        }
    }

    public void setDisplayActionRightButtonEnabled(boolean enabled, String text) {
        getSupportActionBar().getCustomView().findViewById(R.id.action_bar_right_btn).setVisibility(enabled ? View.VISIBLE : View.GONE);
        ((Button) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_right_btn)).setText(text);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        eventManager.fire(new OnRestartEvent());
    }

    @Override
    protected void onStart() {
        super.onStart();
        eventManager.fire(new OnStartEvent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventManager.fire(new OnResumeEvent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        eventManager.fire(new OnPauseEvent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        eventManager.fire(new OnNewIntentEvent());
    }

    @Override
    protected void onStop() {
        try {
            eventManager.fire(new OnStopEvent());
        } finally {
            super.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            eventManager.fire(new OnDestroyEvent());
        } finally {
            try {
                RoboGuice.destroyInjector(this);
            } finally {
                super.onDestroy();
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        final Configuration currentConfig = getResources().getConfiguration();
        super.onConfigurationChanged(newConfig);
        eventManager.fire(new OnConfigurationChangedEvent(currentConfig, newConfig));
    }

    @Override
    public void onSupportContentChanged() {
        super.onSupportContentChanged();
        try {
            RoboGuice.getInjector(this).injectViewMembers(this);
            eventManager.fire(new OnContentChangedEvent());
        } catch (Exception e) {
        }
    }

    @Override
    public Map<Key<?>, Object> getScopedObjectMap() {
        return scopedObjects;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    protected void onFragmentShow(Fragment fragment) {
        if (fragment instanceof ITitle) {
            ITitle title = (ITitle) fragment;
            setActionBarTitle(title.getTitle());
        }
    }
}


