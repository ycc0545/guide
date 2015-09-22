package com.guide.group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.guide.base.BaseListAdapter;
import com.guide.base.PagingListFragment;
import com.guide.base.VolleyRequest;
import com.guide.group.model.Group;
import com.guide.group.model.GroupListRequest;
import com.guide.utils.Utils;

import java.util.List;

/**
 * Created by mac on 2/9/15.
 */
public class GroupListFragment extends PagingListFragment<Group> {
    public static GroupListFragment newInstance() {
        return new GroupListFragment();
    }

    @Override
    public CharSequence getTitle() {
        return "好团";
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final int paddingTop = Utils.dp2px(getActivity(), 8);
        view.setPadding(0, paddingTop, 0, 0);
    }

    @Override
    protected VolleyRequest<List<Group>> createRequest(int offset) {
        GroupListRequest request = new GroupListRequest(this);
        request.setLimit(getLimit());
        request.setOffset(offset);
        return request;
    }

    @Override
    protected BaseListAdapter createListAdapter(List<Group> data) {
        return new GroupListAdapter(getActivity(), data);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Group group = (Group) getListAdapter().getItem(position - 1);
        Intent intent = new Intent(getActivity(), GroupDetailActivity.class);
        intent.putExtra("group", group);
        startActivity(intent);
    }
}
