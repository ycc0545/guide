package com.guide.group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.guide.CustomToast;
import com.guide.MyApplication;
import com.guide.R;
import com.guide.action.ActionDetailActivity;
import com.guide.action.Item;
import com.guide.action.Items;
import com.guide.base.BaseActivity;
import com.guide.base.VolleyRequest;
import com.guide.group.model.Group;
import com.guide.group.model.GroupDetailRequest;
import com.guide.group.model.GroupDetailResult;
import com.guide.group.model.Guide;
import com.guide.view.MyListView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mac on 2/9/15.
 */
public class GroupDetailActivity extends BaseActivity {
    private ScrollView scrollView;
    private TextView mGroupTitleTxt;
    private TextView mCompanyTxt;
    private RelativeLayout mGuideMainLayout;
    private RelativeLayout mGuideViceLayout;
    private ImageView ivAddBtn;
    private LinearLayout mLayoutAddAction;
    private LinearLayout mLayoutNotification;
    private LinearLayout mLayoutNamed;
    private LinearLayout mLayoutAlarm;
    private LinearLayout mLayoutLocation;
    private ImageView ivGuideMain;
    private ImageView ivGuideVice;
    private TextView mGuideMainNameTxt;
    private TextView mGuideViceNameTxt;
    private RelativeLayout mMySchedule;
    private ImageView ivSchedule;
    private MyListView listView;

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        setDisplayPlusActionEnabled(true, 5);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        mGroupTitleTxt = (TextView) findViewById(R.id.group_title);
        mCompanyTxt = (TextView) findViewById(R.id.company_txt);
        mGuideMainLayout = (RelativeLayout) findViewById(R.id.layout_guide_main);
        mGuideViceLayout = (RelativeLayout) findViewById(R.id.layout_guide_vice);
        ivGuideMain = (ImageView) findViewById(R.id.iv_guide_main);
        ivGuideVice = (ImageView) findViewById(R.id.iv_guide_vice);
        ivAddBtn = (ImageView) findViewById(R.id.iv_add_btn);
        mLayoutAddAction = (LinearLayout) findViewById(R.id.layout_add_action);
        mLayoutNotification = (LinearLayout) findViewById(R.id.layout_notification);
        mLayoutNamed = (LinearLayout) findViewById(R.id.layout_named);
        mLayoutAlarm = (LinearLayout) findViewById(R.id.layout_alarm);
        mLayoutLocation = (LinearLayout) findViewById(R.id.layout_location);
        mGuideMainNameTxt = (TextView) findViewById(R.id.guide_main_name_txt);
        mGuideViceNameTxt = (TextView) findViewById(R.id.guide_vice_name_txt);
        mMySchedule = (RelativeLayout) findViewById(R.id.my_schedule);
        ivSchedule = (ImageView) findViewById(R.id.iv_schedule);
        listView = (MyListView) findViewById(R.id.action_list);

        bundle = getIntent().getExtras();

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    /**
     * 设置屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private void loadData() {
        if (bundle != null) {
            Group group = ((Group) bundle.get("group"));
            VolleyRequest.Callbacks<GroupDetailResult> callbacks = new VolleyRequest.Callbacks<GroupDetailResult>() {
                @Override
                public void onResponse(GroupDetailResult result) {
                    if (result != null && result.isResCodeOK()) {
                        final Group thisGroup = result.getGroup();
                        mGroupTitleTxt.setText(thisGroup.getName() + " " + thisGroup.getDaysNights());
                        mCompanyTxt.setText(thisGroup.getCompany());
                        findViewById(R.id.layout_qr).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(GroupDetailActivity.this, GroupQRCodeActivity.class);
                                intent.putExtra("group", thisGroup);
                                startActivity(intent);
                            }
                        });
                        findViewById(R.id.layout_contact).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(GroupDetailActivity.this, GroupContactsActivity.class);
                                intent.putExtra("group", thisGroup);
                                startActivity(intent);
                            }
                        });
                        if (thisGroup.getScheduleTable() != null) {
                            Picasso.with(GroupDetailActivity.this).load(thisGroup.getScheduleTable().split(";")[0]).into(ivSchedule);
                            mMySchedule.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(GroupDetailActivity.this, MyScheduleActivity.class);
                                    intent.putExtra("schedule", thisGroup.getScheduleTable());
                                    startActivity(intent);
                                }
                            });
                        } else {
                            mMySchedule.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CustomToast.makeText(GroupDetailActivity.this, "您还没有行程单", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        //添加行程
                        findViewById(R.id.add_event_btn).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(GroupDetailActivity.this, AddEventActivity.class);
                                intent.putExtra("groupId", thisGroup.getGroupId());
                                startActivity(intent);
                            }
                        });
                        //添加action
                        ivAddBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mLayoutAddAction.getVisibility() == View.GONE) {
                                    backgroundAlpha(0.5f);
                                    mLayoutAddAction.setVisibility(View.VISIBLE);
                                    ivAddBtn.setImageResource(R.drawable.ic_add_pressed);

                                    View.OnClickListener onClickListener = new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            switch (v.getId()) {
                                                // TODO: 25/9/15
                                                case R.id.layout_notification:
                                                    Intent intent1 = new Intent(GroupDetailActivity.this, AddEventActivity.class);
                                                    intent1.putExtra("groupId", thisGroup.getGroupId());
                                                    startActivity(intent1);
                                                    break;
                                                case R.id.layout_named:
                                                    Intent intent2 = new Intent(GroupDetailActivity.this, AddEventActivity.class);
                                                    intent2.putExtra("groupId", thisGroup.getGroupId());
                                                    startActivity(intent2);
                                                    break;
                                                case R.id.layout_alarm:
                                                    Intent intent3 = new Intent(GroupDetailActivity.this, AddEventActivity.class);
                                                    intent3.putExtra("groupId", thisGroup.getGroupId());
                                                    startActivity(intent3);
                                                    break;
                                                case R.id.layout_location:
                                                    Intent intent4 = new Intent(GroupDetailActivity.this, AddEventActivity.class);
                                                    intent4.putExtra("groupId", thisGroup.getGroupId());
                                                    startActivity(intent4);
                                                    break;
                                            }
                                        }
                                    };
//                                    final Dialog dialog = new Dialog(GroupDetailActivity.this, R.style.MyDialog);
//                                    dialog.setContentView(R.layout.layout_add_actions);
//                                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                                    dialog.show();
//                                    ((ImageView) dialog.findViewById(R.id.iv_add_btn)).setImageResource(R.drawable.ic_add_pressed);
//
//                                    dialog.findViewById(R.id.layout_notification).setOnClickListener(onClickListener);
//                                    dialog.findViewById(R.id.layout_named).setOnClickListener(onClickListener);
//                                    dialog.findViewById(R.id.layout_alarm).setOnClickListener(onClickListener);
//                                    dialog.findViewById(R.id.layout_location).setOnClickListener(onClickListener);
                                    mLayoutNotification.setOnClickListener(onClickListener);
                                    mLayoutNamed.setOnClickListener(onClickListener);
                                    mLayoutAlarm.setOnClickListener(onClickListener);
                                    mLayoutLocation.setOnClickListener(onClickListener);
                                } else {
                                    backgroundAlpha(1f);
                                    ivAddBtn.setImageResource(R.drawable.ic_add_normal);
                                    mLayoutAddAction.setVisibility(View.GONE);
                                }
                            }
                        });
                        final List<Guide> guideList = thisGroup.getGuides();

                        switch (guideList.size()) {
                            case 0:
                                mGuideMainLayout.setVisibility(View.GONE);
                                mGuideViceLayout.setVisibility(View.GONE);
                                break;
                            case 1:
                                mGuideViceLayout.setVisibility(View.GONE);
                                if (guideList.get(0).getGender() == 1) {
                                    ivGuideMain.setImageResource(R.drawable.ic_guide_male);
                                } else {
                                    ivGuideMain.setImageResource(R.drawable.ic_guide_female);
                                }
                                mGuideMainNameTxt.setText(guideList.get(0).getName());
                                break;
                            case 2:
                                if (guideList.get(0).getGender() == 1) {
                                    ivGuideMain.setImageResource(R.drawable.ic_guide_male);
                                } else {
                                    ivGuideMain.setImageResource(R.drawable.ic_guide_female);
                                }
                                mGuideMainNameTxt.setText(guideList.get(0).getName());

                                if (guideList.get(1).getGender() == 1) {
                                    ivGuideVice.setImageResource(R.drawable.ic_guide_male);
                                } else {
                                    ivGuideVice.setImageResource(R.drawable.ic_guide_female);
                                }
                                mGuideViceNameTxt.setText(guideList.get(1).getName());
                                break;
                        }
                        Items items = result.getItems();
                        List<Item> itemsList = items.getItemsList();
                        listView.setAdapter(new ActionListAdapter(GroupDetailActivity.this, itemsList));
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Item item = (Item) parent.getAdapter().getItem(position);
                                if (item.getActionId() != 0) {
                                    Intent intent = new Intent(GroupDetailActivity.this, ActionDetailActivity.class);
                                    intent.putExtra("item", item);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }

                @Override
                public void onError(VolleyError error) {

                }
            };
            GroupDetailRequest groupDetailRequest = new GroupDetailRequest(group.getGroupId(), callbacks);
            MyApplication.getInstance().addToRequestQueue(groupDetailRequest.createRequest());
        }
    }

    @Override
    protected void onPlusActionClick() {
        loadData();
        scrollView.fullScroll(View.FOCUS_UP);
    }
}
