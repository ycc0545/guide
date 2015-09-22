package com.guide.user;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guide.R;
import com.guide.base.BaseListAdapter;
import com.guide.group.model.Guide;
import com.guide.user.model.Follow;

import java.util.List;

public class MyFollowsListAdapter extends BaseListAdapter<Follow> {

    private OnUnFollowGuideListener listener;
    private Context context;

    public MyFollowsListAdapter(Context context, List<Follow> data) {
        super(context, data);
        this.context = context;
    }

    public void setOnUnFollowGuideListener(OnUnFollowGuideListener listener) {
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_my_follows, parent, false);
            holder = new ViewHolder();
            holder.ivGuide = (ImageView) convertView.findViewById(R.id.iv_guide);
            holder.mGuideNameTxt = (TextView) convertView.findViewById(R.id.guide_name_txt);
            holder.ivAverageComment = (ImageView) convertView.findViewById(R.id.iv_average_comment);
            holder.ivCancel = (ImageView) convertView.findViewById(R.id.iv_cancel);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Follow follow = getItem(position);
        final Guide guide = follow.getGuide();
        if (1 == guide.getGender()) {
            holder.ivGuide.setImageResource(R.drawable.ic_guide_male_small);
        } else {
            holder.ivGuide.setImageResource(R.drawable.ic_guide_female_small);
        }
        holder.mGuideNameTxt.setText(guide.getName());
        float averageScore = ((follow.getRateInfoVo().getScore1() + follow.getRateInfoVo().getScore2() +
                follow.getRateInfoVo().getScore3() + follow.getRateInfoVo().getScore4()
                + follow.getRateInfoVo().getScore5())) / 5;
        getAverageScore(averageScore, holder.ivAverageComment);
        holder.ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context, R.style.MyDialog);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();

                ((TextView) dialog.findViewById(R.id.dialog_info)).setText("您确定不再关注TA吗？");

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
                        listener.unFollowGuide(guide.getGuideId());
                    }
                });
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        ImageView ivGuide;
        TextView mGuideNameTxt;
        ImageView ivAverageComment;
        ImageView ivCancel;
    }

    interface OnUnFollowGuideListener {
        void unFollowGuide(int guideId);
    }

    private void getAverageScore(float averageScore, ImageView imageView) {
        int offset = 9 * (2 * (int) Math.floor(averageScore) + 1) > 90 ?
                90 : 9 * (2 * (int) Math.floor(averageScore) + 1);
        int newWidth = (int) (285 * averageScore / 5) + offset;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_star_small_on);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, newWidth, 72);
        imageView.setImageBitmap(newBitmap);
    }
}
