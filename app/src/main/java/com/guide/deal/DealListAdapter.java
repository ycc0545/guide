package com.guide.deal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guide.R;
import com.guide.base.BaseListAdapter;
import com.guide.deal.model.Deal;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DealListAdapter extends BaseListAdapter<Deal> {

    public DealListAdapter(Context context, List<Deal> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_deal, parent, false);
            holder = new ViewHolder();
            holder.ivDeal = (ImageView) convertView.findViewById(R.id.iv_deal);
            holder.dealTitle = (TextView) convertView.findViewById(R.id.deal_title);
            holder.yuan = (TextView) convertView.findViewById(R.id.yuan);
            holder.dealPriceInt = (TextView) convertView.findViewById(R.id.deal_price_int);
            holder.dealPriceDec = (TextView) convertView.findViewById(R.id.deal_price_dec);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Deal deal = getItem(position);
        if (deal.getIconUrl() != null) {
            String[] urls = deal.getIconUrl().split(";");
            Picasso.with(convertView.getContext()).load(urls[0]).into(holder.ivDeal);
        }
        holder.dealTitle.setText(deal.getDealName());
        holder.yuan.setText("￥");
        holder.dealPriceInt.setText((String.valueOf(deal.getPrice()).split("\\.")[0]));
        holder.dealPriceDec.setText("." + String.valueOf(deal.getPrice()).split("\\.")[1]);
        return convertView;
    }

    private static class ViewHolder {
        ImageView ivDeal;
        TextView dealTitle;
        TextView yuan;
        TextView dealPriceInt;
        TextView dealPriceDec;
    }
}
