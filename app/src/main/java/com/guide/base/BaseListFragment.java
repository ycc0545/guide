package com.guide.base;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.guide.R;

import roboguice.fragment.RoboListFragment;

public class BaseListFragment extends RoboListFragment implements ITitle {

    static final int EMPTY_VIEW_ID = 0x00ff0001;
    final private AdapterView.OnItemLongClickListener mOnItemLongClickListenr = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            final int headerViewsCount = getListView().getHeaderViewsCount();
            int realPosition = position - headerViewsCount;
            if (getListAdapter() != null && realPosition < getListAdapter().getCount()) {
                return onListItemLongClick((ListView) adapterView, view, position - headerViewsCount, id);
            }
            return false;
        }

    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getListView().setOnItemLongClickListener(mOnItemLongClickListenr);
        ((TextView) view.findViewById(EMPTY_VIEW_ID)).setTextColor(getResources().getColor(R.color.blue));
    }

    private void setEmptyText(String txt) {
        //todo
    }

    public boolean onListItemLongClick(ListView l, View view, int position, long id) {
        return false;
    }


    @Override
    public CharSequence getTitle() {
        return null;
    }

}


