package com.guide.group;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guide.R;
import com.guide.base.BaseListAdapter;
import com.guide.group.model.Group;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GroupListAdapter extends BaseListAdapter<Group> {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 E");

    public GroupListAdapter(Context context, List<Group> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_group, parent, false);
            holder = new ViewHolder();
            if (0 == position) {
                convertView.findViewById(R.id.header_view).setVisibility(View.VISIBLE);
            }
            holder.groupTitle = (TextView) convertView.findViewById(R.id.group_title);
            holder.company = (TextView) convertView.findViewById(R.id.company);
            holder.startDate = (TextView) convertView.findViewById(R.id.startDate);
            holder.status = (ImageView) convertView.findViewById(R.id.iv_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Group group = getItem(position);
        holder.groupTitle.setText(group.getName() + " " + group.getDaysNights());
        holder.company.setText(group.getCompany());
        Date startDate = new Date(Long.valueOf(group.getStartDate()));
        holder.startDate.setText(dateFormat.format(startDate));
        switch (group.getStatus()) {
            case 1:
                holder.status.setImageResource(R.drawable.ic_not_beginning);
                break;
            case 2:
                holder.status.setImageResource(R.drawable.ic_progressing);
                break;
            case 3:
                holder.status.setImageResource(R.drawable.ic_ended);
                break;
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView groupTitle;
        TextView company;
        TextView startDate;
        ImageView status;
    }
}
