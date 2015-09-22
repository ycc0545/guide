package com.guide.view;

import android.os.Bundle;
import android.widget.ImageView;

import com.guide.R;
import com.guide.base.BaseActivity;

/**
 * Created by Xingkai on 22/9/15.
 */
public class DownloadActivity extends BaseActivity {
    private ImageView ivDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ivDownload = (ImageView) findViewById(R.id.iv_download_qr);
        // TODO: 22/9/15 下载二维码
    }
}
