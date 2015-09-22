package com.guide.group.model;

import android.text.TextUtils;

import com.android.volley.Request;
import com.google.gson.JsonElement;
import com.guide.Consts;
import com.guide.base.SimpleResult;
import com.guide.base.VolleyRequestPost;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by mac on 2/9/15.
 */
public class CommentRequest extends VolleyRequestPost<SimpleResult> {
    public CommentRequest(int guideId, int score1, int score2, int score3, int score4, int score5, String comment, Callbacks<SimpleResult> callbacks) {
        super(Request.Method.POST, String.format(Consts.API_URL + "/tourists/guides/%d/comments", guideId), callbacks);

        HashMap<String, String> map = new HashMap<>();
        map.put("score1", String.valueOf(score1));
        map.put("score2", String.valueOf(score2));
        map.put("score3", String.valueOf(score3));
        map.put("score4", String.valueOf(score4));
        map.put("score5", String.valueOf(score5));
        if (!TextUtils.isEmpty(comment)) {
            map.put("comment", comment);
        }
        setParams(map);
    }

    @Override
    protected SimpleResult convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement.getAsJsonObject());
    }
}