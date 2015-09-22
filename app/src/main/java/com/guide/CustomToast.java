package com.guide;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Herbert Dai on 9/5/14.
 */
public class CustomToast extends Toast {

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link android.app.Application}
     *                or {@link android.app.Activity} object.
     */
    public CustomToast(Context context) {
        super(context);
    }

    public static Toast makeText(Context context, String info, int duration) {
        return makeText(context, info, duration, Gravity.CENTER);
    }

    public static Toast makeText(Context context, String info, int duration, int gravity) {
        if (context == null)
            return null;

        CustomToast toast = new CustomToast(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup toastLayout = (ViewGroup) inflater.inflate(R.layout.custom_toast, null);
        TextView contentTxt = ((TextView) toastLayout.getChildAt(0));
        contentTxt.setText(info);
        contentTxt.setGravity(gravity);

        toast.setView(toastLayout);
        toast.setDuration(duration);
        toast.setGravity(gravity, 0, 0);
        return toast;
    }

    public static Toast makeText(Context context, int resId, int duration) {
        if (context == null)
            return null;

        return makeText(context, resId, duration, Gravity.CENTER);
    }

    public static Toast makeText(Context context, int resId, int duration, int gravity) {
        if (context == null)
            return null;

        return makeText(context, context.getResources().getString(resId), duration, gravity);
    }

}
