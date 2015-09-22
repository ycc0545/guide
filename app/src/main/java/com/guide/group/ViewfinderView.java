package com.guide.group;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.guide.Consts;
import com.guide.R;
import com.guide.utils.Utils;

public final class ViewfinderView extends View {

    private Context context;

    private static final long ANIMATION_DELAY = 1L;

    private int ScreenRate;

    private static final int CORNER_WIDTH = 8;

    private static final int MIDDLE_LINE_WIDTH = 8;

    private static final int MIDDLE_LINE_PADDING = 20;

    private static final int SPEEN_DISTANCE = 5;

    private static DisplayMetrics dm;

    private Paint paint;

    private int slideTop;

    boolean isFirst;

    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        dm = context.getResources().getDisplayMetrics();
        ScreenRate = (int) (18 * dm.density);

        paint = new Paint();
    }

    @Override
    public void onDraw(Canvas canvas) {

        int leftOffset = (dm.widthPixels - Utils.dp2px(context, Consts.FRAME_WIDTH_DP)) / 2;
        int topOffset = Utils.dp2px(context, 90);

        Rect frame = new Rect(
                leftOffset,
                topOffset,
                leftOffset + Utils.dp2px(context, Consts.FRAME_WIDTH_DP),
                topOffset + Utils.dp2px(context, Consts.FRAME_HEIGHT_DP));

        if (frame == null) {
            return;
        }

        if (!isFirst) {
            isFirst = true;
            slideTop = frame.top;
        }

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        paint.setColor(Color.parseColor("#60000000"));
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1,
                paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        paint.setColor(getResources().getColor(R.color.blue));
        canvas.drawRect(frame.left, frame.top, frame.left + ScreenRate,
                frame.top + CORNER_WIDTH, paint);
        canvas.drawRect(frame.left, frame.top, frame.left + CORNER_WIDTH, frame.top
                + ScreenRate, paint);
        canvas.drawRect(frame.right - ScreenRate, frame.top, frame.right,
                frame.top + CORNER_WIDTH, paint);
        canvas.drawRect(frame.right - CORNER_WIDTH, frame.top, frame.right, frame.top
                + ScreenRate, paint);
        canvas.drawRect(frame.left, frame.bottom - CORNER_WIDTH, frame.left
                + ScreenRate, frame.bottom, paint);
        canvas.drawRect(frame.left, frame.bottom - ScreenRate,
                frame.left + CORNER_WIDTH, frame.bottom, paint);
        canvas.drawRect(frame.right - ScreenRate, frame.bottom - CORNER_WIDTH,
                frame.right, frame.bottom, paint);
        canvas.drawRect(frame.right - CORNER_WIDTH, frame.bottom - ScreenRate,
                frame.right, frame.bottom, paint);


        slideTop += SPEEN_DISTANCE;
        if (slideTop >= frame.bottom) {
            slideTop = frame.top;
        }
//        Path path = new Path();
//        path.moveTo(frame.left + MIDDLE_LINE_PADDING, slideTop);
//        path.lineTo(frame.width()  / 2, slideTop-MIDDLE_LINE_WIDTH);
//        path.lineTo(frame.width() / 2, slideTop + MIDDLE_LINE_WIDTH);
//        path.close();
//        canvas.drawPath(path,paint);
        canvas.drawRect(frame.left + MIDDLE_LINE_PADDING, slideTop,
                frame.right - MIDDLE_LINE_PADDING, slideTop + MIDDLE_LINE_WIDTH, paint);

        postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
                frame.right, frame.bottom);

    }


}
