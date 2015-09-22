package com.guide.user;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.guide.CustomToast;
import com.guide.MyApplication;
import com.guide.R;
import com.guide.base.BaseActivity;
import com.guide.base.SimpleResult;
import com.guide.base.VolleyRequest;
import com.guide.group.model.UnFollowGuideRequest;
import com.guide.user.model.Follow;
import com.guide.user.model.MyFollowsListRequest;
import com.guide.view.MyListView;

import java.util.List;

/**
 * Created by mac on 2/9/15.
 */
public class MyFollowsActivity extends BaseActivity implements MyFollowsListAdapter.OnUnFollowGuideListener {
    private MyListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_follows);
        myListView = (MyListView) findViewById(R.id.list);

        loadData();
    }

    private void loadData() {
        VolleyRequest.Callbacks<List<Follow>> callbacks = new VolleyRequest.Callbacks<List<Follow>>() {
            @Override
            public void onResponse(List<Follow> followList) {
                if (followList != null) {
                    MyFollowsListAdapter listAdapter = new MyFollowsListAdapter(MyFollowsActivity.this, followList);
                    myListView.setAdapter(listAdapter);
                    if (MyFollowsActivity.this instanceof MyFollowsListAdapter.OnUnFollowGuideListener) {
                        listAdapter.setOnUnFollowGuideListener(MyFollowsActivity.this);
                    }
//                    myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            final Follow follow = (Follow) parent.getAdapter().getItem(position);
//                            final Guide guide = follow.getGuide();
//                            Intent intent = new Intent(MyFollowsActivity.this, GuideDetailActivity.class);
//                            intent.putExtra("guide", guide);
//                            startActivity(intent);
//                        }
//                    });
                }
            }

            @Override
            public void onError(VolleyError error) {
                CustomToast.makeText(MyFollowsActivity.this, error.getCause().getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
        MyFollowsListRequest myFollowsListRequest = new MyFollowsListRequest(callbacks);
        MyApplication.getInstance().addToRequestQueue(myFollowsListRequest.createRequest());
    }

    @Override
    public void unFollowGuide(int guideId) {
        VolleyRequest.Callbacks<SimpleResult> callbacks = new VolleyRequest.Callbacks<SimpleResult>() {
            @Override
            public void onResponse(SimpleResult result) {
                if (result != null && result.isResCodeOK()) {
                    CustomToast.makeText(MyFollowsActivity.this, "取消成功！", Toast.LENGTH_LONG).show();
                    loadData();
                }
            }

            @Override
            public void onError(VolleyError error) {
                CustomToast.makeText(MyFollowsActivity.this, error.getCause().getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
        UnFollowGuideRequest unFollowGuideRequest = new UnFollowGuideRequest(guideId, callbacks);
        MyApplication.getInstance().addToRequestQueue(unFollowGuideRequest.createRequest());
    }
}
