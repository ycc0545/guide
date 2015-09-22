/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.handmark.pulltorefresh.library.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;
import com.handmark.pulltorefresh.library.R;

public class RotateLoadingLayout extends LoadingLayout {

    private final Drawable mUpArrow;
    private final Drawable mDownArrow;
    private Animation mAnimationUp = null;
    private Animation mAnimationDown = null;

    public RotateLoadingLayout(Context context, Mode mode, Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);

        mAnimationUp = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_up);
        mAnimationUp.setFillAfter(true);
        mAnimationUp.setFillBefore(false);

        mAnimationDown = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_down);
        mAnimationDown.setFillAfter(true);
        mAnimationDown.setFillBefore(false);


        mUpArrow = getResources().getDrawable(R.drawable.pullup);
        mDownArrow = getResources().getDrawable(R.drawable.pulldown);
    }

    public void onLoadingDrawableSet(Drawable imageDrawable) {

    }

    protected void onPullImpl(float scaleOfLayout) {
//		float angle;
//		if (mRotateDrawableWhilePulling) {
//			angle = scaleOfLayout * 90f;
//		} else {
//			angle = Math.max(0f, Math.min(180f, scaleOfLayout * 360f - 180f));
//		}
//
//		mHeaderImageMatrix.setRotate(angle, mRotationPivotX, mRotationPivotY);
//		mHeaderImage.setImageMatrix(mHeaderImageMatrix);
    }

    @Override
    protected void refreshingImpl() {
        mHeaderImage.setVisibility(View.GONE);
        mHeaderProgress.setVisibility(View.VISIBLE);
    }

    @Override
    protected void resetImpl() {
        mHeaderImage.setVisibility(View.VISIBLE);
        mHeaderProgress.setVisibility(View.GONE);
        mHeaderImage.clearAnimation();
    }

    @Override
    protected void pullToRefreshImpl() {
        if (mMode == Mode.PULL_FROM_END) {
            mAnimationUp.setAnimationListener(mFooterToUpListener);
            mHeaderImage.startAnimation(mAnimationUp);
        } else if (mMode == Mode.PULL_FROM_START) {
            mAnimationDown.setAnimationListener(mHeaderToDownListener);
            mHeaderImage.startAnimation(mAnimationDown);
        }
    }

    @Override
    protected void releaseToRefreshImpl() {
        if (mMode == Mode.PULL_FROM_END) {
            mAnimationDown.setAnimationListener(mFooterToDownListener);
            mHeaderImage.startAnimation(mAnimationDown);
        } else if (mMode == Mode.PULL_FROM_START) {
            mAnimationUp.setAnimationListener(mHeaderToUpListener);
            mHeaderImage.startAnimation(mAnimationUp);
        }
    }

    @Override
    protected int getDefaultDrawableResId(Mode mode) {
        if (mode == Mode.PULL_FROM_START) {
            return R.drawable.pulldown;
        } else {
            return R.drawable.pullup;
        }
    }

    Animation.AnimationListener mHeaderToUpListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mHeaderImage.setImageDrawable(mUpArrow);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    Animation.AnimationListener mHeaderToDownListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mHeaderImage.setImageDrawable(mDownArrow);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    Animation.AnimationListener mFooterToDownListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mHeaderImage.setImageDrawable(mDownArrow);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    Animation.AnimationListener mFooterToUpListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mHeaderImage.setImageDrawable(mUpArrow);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
}
