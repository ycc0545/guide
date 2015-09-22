package com.guide.group;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.guide.MyApplication;
import com.guide.R;
import com.guide.base.BaseActivity;
import com.guide.base.VolleyRequest;
import com.guide.group.model.GetTouristByGroupIdRequest;
import com.guide.group.model.GetTouristResult;
import com.guide.group.model.Group;
import com.guide.user.model.Tourist;
import com.guide.utils.Utils;
import com.guide.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2/9/15.
 */
public class GroupContactsActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private EditText mSearchContactEdit;
    private ImageView ivSearchContact;
    private MyListView listView;

    private Bundle bundle;

    private List<Tourist> touristList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_contacts);
        mSearchContactEdit = (EditText) findViewById(R.id.search_contact_edit);
        ivSearchContact = (ImageView) findViewById(R.id.iv_search_contact);
        listView = (MyListView) findViewById(R.id.tourist_list);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            final Group group = (Group) bundle.get("group");
            loadData(group.getGroupId());
            mSearchContactEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        loadData(group.getGroupId());
                    } else {
                        listView.setAdapter(new TouristListAdapter(GroupContactsActivity.this, searchTourist()));
                        listView.setOnItemClickListener(GroupContactsActivity.this);
                    }
                }
            });
            ivSearchContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listView.setAdapter(new TouristListAdapter(GroupContactsActivity.this, searchTourist()));
                }
            });
        }
    }

    private void loadData(int groupId) {
        VolleyRequest.Callbacks<GetTouristResult> callbacks = new VolleyRequest.Callbacks<GetTouristResult>() {
            @Override
            public void onResponse(GetTouristResult result) {
                if (result.isResCodeOK()) {
                    touristList = result.getTourists();
                    setActionBarTitle("团通讯录（共" + touristList.size() + "人）");
                    listView.setAdapter(new TouristListAdapter(GroupContactsActivity.this, result.getTourists()));
                    listView.setOnItemClickListener(GroupContactsActivity.this);
                }
            }

            @Override
            public void onError(VolleyError error) {
                Utils.showVolleyError(GroupContactsActivity.this, error);
            }
        };
        GetTouristByGroupIdRequest request = new GetTouristByGroupIdRequest(groupId, callbacks);
        MyApplication.getInstance().addToRequestQueue(request.createRequest());
    }

    private List<Tourist> searchTourist() {
        List<Tourist> newTourists = new ArrayList<>();
        for (Tourist tourist : touristList) {
            if (tourist.getName().contains(mSearchContactEdit.getText().toString())) {
                newTourists.add(tourist);
            }
        }
        return newTourists;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Tourist tourist = (Tourist) parent.getAdapter().getItem(position);
        Intent intent = new Intent(GroupContactsActivity.this, TouristDetailActivity.class);
        intent.putExtra("tourist", tourist);
        startActivity(intent);
    }
}
