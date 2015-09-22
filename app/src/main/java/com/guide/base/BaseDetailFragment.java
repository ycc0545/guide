package com.guide.base;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guide.R;

public abstract class BaseDetailFragment extends BaseFragment {
    static final int INTERNAL_PROGRESS_CONTAINER_ID = 0x00ff0002;
    static final int INTERNAL_CONTENT_CONTAINER_ID = 0x00ff0003;
    static final int INTERNAL_DEFAULT_EMPTY_ID = 0x00ff0004;
    static final int INTERNAL_ERROR_EMPTY_ID = 0x00ff0005;

    protected final int STATE_LOADING = 0;
    protected final int STATE_OK = 1;
    protected final int STATE_EMPTY = 2;
    protected final int STATE_ERROR = 3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context context = getActivity();

        FrameLayout root = new FrameLayout(context);

        View pframe = createProgressContainer(context);
        pframe.setId(INTERNAL_PROGRESS_CONTAINER_ID);
        root.addView(pframe, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        View defaultEmptyView = createDefaultEmptyView();
        defaultEmptyView.setId(INTERNAL_DEFAULT_EMPTY_ID);
        root.addView(defaultEmptyView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));

        View errorEmptyView = createErrorEmptyView();
        errorEmptyView.setId(INTERNAL_ERROR_EMPTY_ID);
        root.addView(errorEmptyView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));

        FrameLayout lframe = new FrameLayout(context);
        lframe.setId(INTERNAL_CONTENT_CONTAINER_ID);

        View lv = createContentView();
        lframe.addView(lv, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        root.addView(lframe, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        root.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return root;
    }


    protected View createDefaultEmptyView() {
        TextView view = new TextView(getActivity());
        view.setText(getEmptyText());
        return view;
    }


    protected View createErrorEmptyView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_base_error, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
        return view;
    }

    protected CharSequence getEmptyText() {
        return "没有数据";
    }

    protected View createProgressContainer(Context context) {
        LinearLayout pframe = new LinearLayout(context);
        pframe.setOrientation(LinearLayout.VERTICAL);
        pframe.setGravity(Gravity.CENTER);
        pframe.addView(createProgress(context), new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        return pframe;
    }

    protected View createProgress(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_base_error, null);
//        ImageView img = (ImageView) view.findViewById(R.id.progress_dialog_img);
//        Animation anim = AnimationUtils.loadAnimation(context, R.anim.loading_dialog_progressbar);
//        img.setAnimation(anim);
        return view;
    }

    protected void refresh() {
    }

    protected View createContentView() {
        return createDefaultEmptyView();
    }

    protected void setState(int state) {
        if (getView() == null) {
            return;
        }
        boolean showProgress = false;
        boolean showEmptyView = false;
        boolean showErrorView = false;
        boolean showContent = false;

        switch (state) {
            case STATE_OK:
                showContent = true;
                break;
            case STATE_EMPTY:
                showEmptyView = true;
                break;
            case STATE_ERROR:
                showErrorView = true;
                break;
            case STATE_LOADING:
                showProgress = true;
                break;
        }

        getView().findViewById(INTERNAL_PROGRESS_CONTAINER_ID).setVisibility(showProgress ? View.VISIBLE : View.GONE);
        getView().findViewById(INTERNAL_ERROR_EMPTY_ID).setVisibility(showErrorView ? View.VISIBLE : View.GONE);
        getView().findViewById(INTERNAL_DEFAULT_EMPTY_ID).setVisibility(showEmptyView ? View.VISIBLE : View.GONE);
        getView().findViewById(INTERNAL_CONTENT_CONTAINER_ID).setVisibility(showContent ? View.VISIBLE : View.GONE);
    }
}
