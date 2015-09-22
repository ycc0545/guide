package com.guide.group;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guide.R;
import com.guide.action.Item;
import com.guide.base.BaseListAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ActionListAdapter extends BaseListAdapter<Item> {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

    public ActionListAdapter(Context context, List<Item> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_action, parent, false);
            holder = new ViewHolder();
            holder.layoutAction = (LinearLayout) convertView.findViewById(R.id.layout_action);
            holder.ivAction = (ImageView) convertView.findViewById(R.id.iv_action);
            holder.actionTime = (TextView) convertView.findViewById(R.id.action_time_txt);
            holder.actionInfo = (TextView) convertView.findViewById(R.id.action_info_txt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Item item = getItem(position);

        holder.actionInfo.setText(item.getContent());

        if (item.getEventId() != 0) {
            Date scheduleTime = new Date(Long.valueOf(item.getScheduleTime()));
            holder.actionTime.setText(dateFormat.format(scheduleTime));
            //已完成的event
            if (scheduleTime.before(new Date(System.currentTimeMillis()))) {
                holder.layoutAction.setBackgroundColor(convertView.getResources().getColor(R.color.grey5));
                holder.ivAction.setImageResource(R.drawable.ic_event_finish);
            } else {
                holder.ivAction.setImageResource(R.drawable.ic_event);
            }
        } else {
            Date remindTime = new Date(Long.valueOf(item.getRemindTime()));
            holder.actionTime.setText(dateFormat.format(remindTime));
            //已完成的action
            if (remindTime.before(new Date(System.currentTimeMillis()))) {
                holder.layoutAction.setBackgroundColor(convertView.getResources().getColor(R.color.grey5));
                switch (item.getType()) {
                    case 1:
                        holder.ivAction.setImageResource(R.drawable.ic_notification_finish);
                        break;
                    case 2:
                        holder.ivAction.setImageResource(R.drawable.ic_named_finish);
                        break;
                    case 3:
                        holder.ivAction.setImageResource(R.drawable.ic_alarm_finish);
                        break;
                }
            } else {
                switch (item.getType()) {
                    case 1:
                        holder.ivAction.setImageResource(R.drawable.ic_notification);
                        break;
                    case 2:
                        holder.ivAction.setImageResource(R.drawable.ic_named);
                        break;
                    case 3:
                        holder.ivAction.setImageResource(R.drawable.ic_alarm);
                        break;
                }
            }
        }
        return convertView;
    }

    private static class ViewHolder {
        LinearLayout layoutAction;
        ImageView ivAction;
        TextView actionTime;
        TextView actionInfo;
    }
}
