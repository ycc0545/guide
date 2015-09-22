package com.guide.deal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.guide.R;
import com.guide.base.BaseFragment;
import com.squareup.picasso.Picasso;

import roboguice.inject.InjectView;

public class DealPicFragment extends BaseFragment {
    private String url;

    @InjectView(R.id.image)
    private ImageView imageView;

    public static DealPicFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);
        DealPicFragment fragment = new DealPicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_deal_pic, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Picasso.with(getActivity()).load(url).into(imageView);
    }
}
