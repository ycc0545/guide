package com.guide.group;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.guide.MyApplication;
import com.guide.R;
import com.guide.base.BaseActivity;
import com.guide.base.VolleyRequest;
import com.guide.group.model.GetTouristByGroupIdRequest;
import com.guide.group.model.GetTouristByGroupIdResult;
import com.guide.group.model.Group;
import com.guide.view.MyListView;

/**
 * Created by mac on 2/9/15.
 */
public class GroupContactsActivity extends BaseActivity {
    private EditText mSearchContactEdit;
    private ImageView ivSearchContact;
    private MyListView listView;

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_contacts);
        listView = (MyListView) findViewById(R.id.tourist_list);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            Group group = (Group) bundle.get("group");
            loadData(group.getGroupId());
        }
    }

    private void loadData(int groupId) {
        VolleyRequest.Callbacks<GetTouristByGroupIdResult> callbacks = new VolleyRequest.Callbacks<GetTouristByGroupIdResult>() {
            @Override
            public void onResponse(GetTouristByGroupIdResult result) {
                if (result.isResCodeOK()) {
                    listView.setAdapter(new TouristListAdapter(GroupContactsActivity.this, result.getTourists()));
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        };
        GetTouristByGroupIdRequest request = new GetTouristByGroupIdRequest(groupId, callbacks);
        MyApplication.getInstance().addToRequestQueue(request.createRequest());
    }
}
