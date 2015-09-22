package com.guide.user;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guide.R;
import com.guide.base.BaseListAdapter;
import com.guide.user.model.Alarm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyAlarmsListAdapter extends BaseListAdapter<Alarm> {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm E");

    private Context context;
    private boolean isAlarmOn = true;

    public MyAlarmsListAdapter(Context context, List<Alarm> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_my_alarms, parent, false);
            holder = new ViewHolder();
            holder.mAlarmTimeTxt = (TextView) convertView.findViewById(R.id.alarm_time_txt);
            holder.mAlarmAddressTxt = (TextView) convertView.findViewById(R.id.alarm_address_txt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Alarm alarm = getItem(position);
        Date date = new Date(Long.valueOf(alarm.getRemindTime()));
        holder.mAlarmTimeTxt.setText(dateFormat.format(date));
        holder.mAlarmAddressTxt.setText(alarm.getLocation());

        return convertView;
    }

    private static class ViewHolder {
        TextView mAlarmTimeTxt;
        TextView mAlarmAddressTxt;
    }
}
