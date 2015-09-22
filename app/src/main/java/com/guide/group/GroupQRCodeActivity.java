package com.guide.group;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.guide.R;
import com.guide.base.BaseActivity;
import com.guide.group.model.Group;
import com.guide.utils.Utils;

/**
 * Created by Xingkai on 22/9/15.
 */
public class GroupQRCodeActivity extends BaseActivity {
    private TextView mGroupCodeTxt;
    private ImageView ivGroupQR;
    private TextView mGroupCodeInfoTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_qr);
        mGroupCodeTxt = (TextView) findViewById(R.id.group_code_txt);
        ivGroupQR = (ImageView) findViewById(R.id.iv_group_qr);
        mGroupCodeInfoTxt = (TextView) findViewById(R.id.group_code_info_txt);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Group group = (Group) bundle.get("group");
            setActionBarTitle(group.getName());
            mGroupCodeTxt.setText(group.getGroupCode());
            Bitmap bitmap = Utils.createQRImage(group.getGroupCode(), 740, 740);
            ivGroupQR.setImageBitmap(bitmap);
            mGroupCodeInfoTxt.setText("2.输入4位团号‘" + group.getGroupCode() + "’即可入团");
        }
    }
}
