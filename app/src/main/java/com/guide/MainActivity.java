package com.guide;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.guide.base.BaseActivity;
import com.guide.base.VolleyRequest;
import com.guide.deal.AddDealActivity;
import com.guide.deal.DealDetailActivity;
import com.guide.deal.DealListAdapter;
import com.guide.deal.model.Deal;
import com.guide.group.AddGroupActivity;
import com.guide.group.GroupDetailActivity;
import com.guide.group.GroupListAdapter;
import com.guide.group.GuideDetailListAdapter;
import com.guide.group.model.Group;
import com.guide.group.model.Guide;
import com.guide.group.model.GuideInfo;
import com.guide.guide.GuideDetailActivity;
import com.guide.guide.model.GetGuideDetailRequest;
import com.guide.guide.model.GetGuideDetailResult;
import com.guide.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2/9/15.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final int[] TAB_LAYOUT_IDS = {R.id.guide_tab_1, R.id.guide_tab_2, R.id.guide_tab_3};
    private static final int[] TAB_TXT_IDS = {R.id.guide_tab_txt_1, R.id.guide_tab_txt_2, R.id.guide_tab_txt_3};
    private static final int[] TAB_LINE_IDS = {R.id.guide_tab_line_1, R.id.guide_tab_line_2, R.id.guide_tab_line_3};

    private ImageView ivDownload;
    private ImageView ivEditGuide;
    private ImageView ivGuide;
    private TextView mGuideNameTxt;
    private TextView mGuideCardTxt;
    private MyListView myListView;

    private GuideInfo guideInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ivDownload = (ImageView) findViewById(R.id.iv_download);
        ivEditGuide = (ImageView) findViewById(R.id.iv_edit_guide);
        ivGuide = (ImageView) findViewById(R.id.iv_guide);
        mGuideNameTxt = (TextView) findViewById(R.id.guide_name_txt);
        mGuideCardTxt = (TextView) findViewById(R.id.guide_card_txt);
        myListView = (MyListView) findViewById(R.id.list);

        for (int id : TAB_LAYOUT_IDS) {
            findViewById(id).setOnClickListener(this);
        }

        ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 21/9/15  
                Intent intent = new Intent(MainActivity.this, GuideDetailActivity.class);
                intent.putExtra("guide", guideInfo.getGuide());
                startActivity(intent);
            }
        });

        ivEditGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GuideDetailActivity.class);
                intent.putExtra("guide", guideInfo.getGuide());
                startActivity(intent);
            }
        });
        loadGuideData();
    }

    private void initView() {
        final Guide guide = guideInfo.getGuide();
        if (guide.getGender() == 1) {
            ivGuide.setImageResource(R.drawable.ic_guide_detail_male);
        } else {
            ivGuide.setImageResource(R.drawable.ic_guide_detail_female);
        }
        mGuideNameTxt.setText(guide.getName());
        mGuideCardTxt.setText("导游证：" + guide.getIdCard());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGuideData();
    }

    private void clearLines() {
        for (int id : TAB_LINE_IDS) {
            findViewById(id).setVisibility(View.GONE);
        }
        for (int id : TAB_TXT_IDS) {
            ((TextView) findViewById(id)).setTextColor(getResources().getColor(R.color.black3));
        }
    }

    private void showTab(int index) {
        switch (index) {
            case 1:
                findViewById(R.id.iv_add_deal).setVisibility(View.GONE);
                findViewById(R.id.iv_add_group).setVisibility(View.VISIBLE);
                findViewById(R.id.iv_add_group).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, AddGroupActivity.class));
                    }
                });
                myListView.setAdapter(new GroupListAdapter(MainActivity.this, guideInfo.getGroups()));
                myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Group group = (Group) parent.getAdapter().getItem(position);
                        Intent intent = new Intent(MainActivity.this, GroupDetailActivity.class);
                        intent.putExtra("group", group);
                        startActivity(intent);
                    }
                });
                break;
            case 2:
                findViewById(R.id.iv_add_group).setVisibility(View.GONE);
                findViewById(R.id.iv_add_deal).setVisibility(View.VISIBLE);
                findViewById(R.id.iv_add_deal).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, AddDealActivity.class));
                    }
                });
                myListView.setAdapter(new DealListAdapter(MainActivity.this, guideInfo.getDeals()));
                myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Deal deal = (Deal) parent.getAdapter().getItem(position);
                        Intent intent = new Intent(MainActivity.this, DealDetailActivity.class);
                        intent.putExtra("deal", deal);
                        startActivity(intent);
                    }
                });
                break;
            case 3:
                findViewById(R.id.iv_add_group).setVisibility(View.GONE);
                findViewById(R.id.iv_add_deal).setVisibility(View.GONE);
                List<GuideInfo> list = new ArrayList<>();
                list.add(guideInfo);
                myListView.setAdapter(new GuideDetailListAdapter(MainActivity.this, list));
                myListView.setOnItemClickListener(null);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        clearLines();
        switch (v.getId()) {
            case R.id.guide_tab_1:
                showTab(1);
                findViewById(TAB_LINE_IDS[0]).setVisibility(View.VISIBLE);
                ((TextView) findViewById(TAB_TXT_IDS[0])).setTextColor(getResources().getColor(R.color.blue));
                break;
            case R.id.guide_tab_2:
                showTab(2);
                findViewById(TAB_LINE_IDS[1]).setVisibility(View.VISIBLE);
                ((TextView) findViewById(TAB_TXT_IDS[1])).setTextColor(getResources().getColor(R.color.blue));
                break;
            case R.id.guide_tab_3:
                showTab(3);
                findViewById(TAB_LINE_IDS[2]).setVisibility(View.VISIBLE);
                ((TextView) findViewById(TAB_TXT_IDS[2])).setTextColor(getResources().getColor(R.color.blue));
                break;
        }
    }

    private void loadGuideData() {
        VolleyRequest.Callbacks<GetGuideDetailResult> callbacks = new VolleyRequest.Callbacks<GetGuideDetailResult>() {
            @Override
            public void onResponse(GetGuideDetailResult result) {
                if (result != null && result.isResCodeOK()) {
                    guideInfo = result.getGuideInfo();
                    initView();
                    if (findViewById(R.id.guide_tab_line_1).getVisibility() == View.VISIBLE) {
                        showTab(1);
                    } else if (findViewById(R.id.guide_tab_line_2).getVisibility() == View.VISIBLE) {
                        showTab(2);
                    } else {
                        showTab(3);
                    }
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        };
        GetGuideDetailRequest request = new GetGuideDetailRequest(callbacks);
        MyApplication.getInstance().addToRequestQueue(request.createRequest());
    }

    private void callGuide(final String mobile) {
        final Dialog dialog = new Dialog(MainActivity.this, R.style.MyDialog);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        ((TextView) dialog.findViewById(R.id.dialog_info)).setText("确认拨打" + mobile + "？");

        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                String url = "tel:" + mobile;
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}
