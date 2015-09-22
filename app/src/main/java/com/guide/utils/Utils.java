package com.guide.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.guide.CustomToast;
import com.guide.R;

import java.util.Hashtable;

import roboguice.util.Ln;

/**
 * Created by mac on 7/9/15.
 */
public class Utils {
    private static Location location;

    public static int dp2px(Context context, int dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) (dp * metrics.density);
    }

    public static void setBtnEnable(Button btn, boolean enable) {
        btn.setEnabled(enable);
        if (enable) {
            btn.setBackgroundResource(R.drawable.btn_solid_cornered_blue);
        } else {
            btn.setBackgroundResource(R.drawable.bg_cornered_grey);
        }
    }

    public static void showError(Context context, String errRes) {
        CustomToast.makeText(context, errRes, Toast.LENGTH_SHORT).show();
    }

    public static void showVolleyError(Context context, VolleyError error) {
        Throwable cause = error.getCause();
        if (cause != null) {
            showError(context, cause.getMessage());
            Ln.e(cause.getMessage());
        }
    }

    public static Location getLocation(Context context) {
        if (location == null) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {
                    }

                    public void onProviderDisabled(String provider) {
                    }
                };
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000L, 0.0F, locationListener);
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }

        return location;
    }

    public static Bitmap createQRImage(String url, int height, int width) {
        Bitmap bitmap = null;
        try {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return bitmap;
            }
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, 0);
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, height, width, hints);
            int[] pixels = new int[height * width];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
