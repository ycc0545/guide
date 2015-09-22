package com.guide.group;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guide.R;
import com.guide.base.BaseListAdapter;
import com.guide.group.model.GuideInfo;

import java.util.List;

public class GuideDetailListAdapter extends BaseListAdapter<GuideInfo> {
    private Context context;

    public GuideDetailListAdapter(Context context, List<GuideInfo> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_guide_reputation, parent, false);
            holder = new ViewHolder();
            holder.ivAverageComment = (ImageView) convertView.findViewById(R.id.iv_average_comment);
            holder.mAverageScoreTxt = (TextView) convertView.findViewById(R.id.average_score_txt);
            holder.ivService = (ImageView) convertView.findViewById(R.id.iv_service);
            holder.ivLevel = (ImageView) convertView.findViewById(R.id.iv_level);
            holder.ivSpeaking = (ImageView) convertView.findViewById(R.id.iv_speaking);
            holder.ivCoordinate = (ImageView) convertView.findViewById(R.id.iv_coordinate);
            holder.ivCulture = (ImageView) convertView.findViewById(R.id.iv_culture);
            holder.commentNumTxt = (TextView) convertView.findViewById(R.id.comment_num_txt);
            holder.touristNumTxt = (TextView) convertView.findViewById(R.id.tourist_num_txt);
            holder.groupNumTxt = (TextView) convertView.findViewById(R.id.group_num_txt);
            holder.registerDateTxt = (TextView) convertView.findViewById(R.id.register_date_txt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final GuideInfo guideInfo = getItem(position);
        float averageScore = guideInfo.getRateInfoVo().getScore();
        getAverageScore(averageScore, holder.ivAverageComment);
        holder.mAverageScoreTxt.setText(String.valueOf(averageScore));
        getAverageScore(guideInfo.getRateInfoVo().getScore1(), holder.ivService);
        getAverageScore(guideInfo.getRateInfoVo().getScore2(), holder.ivLevel);
        getAverageScore(guideInfo.getRateInfoVo().getScore3(), holder.ivSpeaking);
        getAverageScore(guideInfo.getRateInfoVo().getScore4(), holder.ivCoordinate);
        getAverageScore(guideInfo.getRateInfoVo().getScore5(), holder.ivCulture);
        holder.commentNumTxt.setText("共有" + guideInfo.getRateInfoVo().getRatingCount() + "人为该导游进行了评价");
        holder.touristNumTxt.setText("该导游共服务了" + guideInfo.getServiceTouristCounts() + "名游客");
        holder.groupNumTxt.setText("该导游共服务了" + guideInfo.getServiceGroupCounts() + "个旅游团");

        return convertView;
    }

    private static class ViewHolder {
        ImageView ivAverageComment;
        TextView mAverageScoreTxt;
        ImageView ivService;
        ImageView ivLevel;
        ImageView ivSpeaking;
        ImageView ivCoordinate;
        ImageView ivCulture;
        TextView commentNumTxt;
        TextView touristNumTxt;
        TextView groupNumTxt;
        TextView registerDateTxt;
    }

    private void getAverageScore(float averageScore, ImageView imageView) {
        int offset = 18 * (2 * (int) Math.floor(averageScore) + 1) > 180 ?
                180 : 18 * (2 * (int) Math.floor(averageScore) + 1);
        int newWidth = (int) (415 * averageScore / 5) + offset;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_star_on);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, newWidth, 116);
        imageView.setImageBitmap(newBitmap);
    }
}
