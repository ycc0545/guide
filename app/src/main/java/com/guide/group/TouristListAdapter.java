package com.guide.group;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guide.R;
import com.guide.base.BaseListAdapter;
import com.guide.user.model.Tourist;

import java.util.List;

public class TouristListAdapter extends BaseListAdapter<Tourist> {
    private Context context;

    public TouristListAdapter(Context context, List<Tourist> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_tourist, parent, false);
            holder = new ViewHolder();
            holder.mToutistNameTxt = (TextView) convertView.findViewById(R.id.tourist_name_txt);
            holder.mToutistMobileTxt = (TextView) convertView.findViewById(R.id.tourist_mobile_txt);
            holder.mLayoutCallTourist = (LinearLayout) convertView.findViewById(R.id.layout_call_tourist);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Tourist tourist = getItem(position);

        holder.mToutistNameTxt.setText(tourist.getName());
        holder.mToutistMobileTxt.setText(tourist.getMobile());
        holder.mLayoutCallTourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context, R.style.MyDialog);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();

                ((TextView) dialog.findViewById(R.id.dialog_info)).setText("确认拨打" + tourist.getMobile() + "？");

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
                        String url = "tel:" + tourist.getMobile();
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
                        context.startActivity(intent);
                    }
                });
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        TextView mToutistNameTxt;
        TextView mToutistMobileTxt;
        LinearLayout mLayoutCallTourist;
    }
}
