package com.guide.group;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.guide.CustomToast;
import com.guide.MyApplication;
import com.guide.R;
import com.guide.base.BaseActivity;
import com.guide.base.SimpleResult;
import com.guide.base.VolleyRequest;
import com.guide.group.model.CommentRequest;
import com.guide.group.model.Guide;
import com.guide.utils.Utils;

/**
 * Created by mac on 2/9/15.
 */
public class CommentActivity extends BaseActivity {
    private LinearLayout mServiceLayout;
    private LinearLayout mLevelLayout;
    private LinearLayout mSpeakingLayout;
    private LinearLayout mCoordinateLayout;
    private LinearLayout mCultureLayout;
    private LinearLayout mLayoutAverageComment;
    private ImageView ivAverageComment;
    private TextView mAverageScoreTxt;
    private EditText mCommentInfoEdit;
    private Button mSubmitCommentBtn;
    private int score1 = 0;
    private int score2 = 0;
    private int score3 = 0;
    private int score4 = 0;
    private int score5 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        mServiceLayout = (LinearLayout) findViewById(R.id.service_layout);
        mLevelLayout = (LinearLayout) findViewById(R.id.level_layout);
        mSpeakingLayout = (LinearLayout) findViewById(R.id.speaking_layout);
        mCoordinateLayout = (LinearLayout) findViewById(R.id.coordinate_layout);
        mCultureLayout = (LinearLayout) findViewById(R.id.culture_layout);
        mLayoutAverageComment = (LinearLayout) findViewById(R.id.layout_average_comment);
        ivAverageComment = (ImageView) findViewById(R.id.iv_average_comment);
        mAverageScoreTxt = (TextView) findViewById(R.id.average_score_txt);
        mCommentInfoEdit = (EditText) findViewById(R.id.comment_info_edit);
        mSubmitCommentBtn = (Button) findViewById(R.id.submit_comment_btn);

        Utils.setBtnEnable(mSubmitCommentBtn, false);

        for (int i = 0; i < 5; ++i) {
            final int finalI = i;
            mServiceLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearStars(mServiceLayout);
                    lightUpStars(finalI + 1, mServiceLayout);
                    score1 = finalI + 1;
                    checkScores();
                }
            });
            mLevelLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearStars(mLevelLayout);
                    lightUpStars(finalI + 1, mLevelLayout);
                    score2 = finalI + 1;
                    checkScores();
                }
            });
            mSpeakingLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearStars(mSpeakingLayout);
                    lightUpStars(finalI + 1, mSpeakingLayout);
                    score3 = finalI + 1;
                    checkScores();
                }
            });
            mCoordinateLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearStars(mCoordinateLayout);
                    lightUpStars(finalI + 1, mCoordinateLayout);
                    score4 = finalI + 1;
                    checkScores();
                }
            });
            mCultureLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearStars(mCultureLayout);
                    lightUpStars(finalI + 1, mCultureLayout);
                    score5 = finalI + 1;
                    checkScores();
                }
            });
        }


        Bundle bundle = getIntent().getExtras();
        if (bundle != null)

        {
            final Guide guide = (Guide) bundle.get("guide");
            mSubmitCommentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VolleyRequest.Callbacks<SimpleResult> callbacks = new VolleyRequest.Callbacks<SimpleResult>() {
                        @Override
                        public void onResponse(SimpleResult result) {
                            if (result != null && result.isResCodeOK()) {
                                CustomToast.makeText(CommentActivity.this, "评价成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                CustomToast.makeText(CommentActivity.this, "评价失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {
                            CustomToast.makeText(CommentActivity.this, error.getCause().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    };
                    CommentRequest commentRequest = new CommentRequest(guide.getGuideId(), score1, score2, score3, score4,
                            score5, mCommentInfoEdit.getText().toString(), callbacks);
                    MyApplication.getInstance().addToRequestQueue(commentRequest.createRequest());
                }
            });
        }
    }

    private void clearStars(LinearLayout layout) {
        for (int i = 0; i < 5; ++i) {
            layout.getChildAt(i).setSelected(false);
        }
    }

    private void lightUpStars(int max, LinearLayout layout) {
        for (int i = 0; i < max; ++i) {
            layout.getChildAt(i).setSelected(true);
        }
    }

    private void checkScores() {
        if (score1 > 0 && score2 > 0 && score3 > 0 && score4 > 0 && score5 > 0) {
            mLayoutAverageComment.setVisibility(View.VISIBLE);
            float averageScore = ((float) (score1 + score2 + score3 + score4 + score5)) / 5;
            mAverageScoreTxt.setText(String.valueOf(averageScore));
            int offset = 18 * (2 * (int) Math.floor(averageScore) + 1) > 180 ?
                    180 : 18 * (2 * (int) Math.floor(averageScore) + 1);
            int newWidth = (int) (575 * averageScore / 5) + offset;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_star_big_on);
            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, newWidth, 146);
            ivAverageComment.setImageBitmap(newBitmap);
            Utils.setBtnEnable(mSubmitCommentBtn, true);
        } else {
            mLayoutAverageComment.setVisibility(View.GONE);
            Utils.setBtnEnable(mSubmitCommentBtn, false);
        }
    }
}
