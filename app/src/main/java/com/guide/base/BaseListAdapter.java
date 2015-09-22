package com.guide.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mData;
    protected LayoutInflater mInflater;

    public BaseListAdapter(Context context) {
        this(context, null);
    }

    public BaseListAdapter(Context context, List<T> data) {
        mContext = context;
        if (data != null) {
            mData = new ArrayList<T>(data);
        }
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public List<T> getData() {
        return mData;
    }

    /**
     * 设置数据
     *
     * @param data
     */
    public void setData(List<T> data) {
        if (data == null) {
            mData = null;
        } else {
            mData = new ArrayList<T>(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param data
     */
    public void appendData(List<T> data) {
        if (mData == null || mData.size() == 0) {
            setData(data);
        } else {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
