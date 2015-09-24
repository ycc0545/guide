package com.guide.group;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.android.volley.VolleyError;
import com.guide.CustomToast;
import com.guide.MyApplication;
import com.guide.R;
import com.guide.action.Tourist;
import com.guide.base.BaseActivity;
import com.guide.base.VolleyRequest;
import com.guide.group.model.GetTouristDetailRequest;
import com.guide.group.model.GetTouristDetailResult;
import com.guide.utils.AMapUtil;
import com.guide.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mac on 2/9/15.
 */
public class TouristDetailActivity extends BaseActivity implements GeocodeSearch.OnGeocodeSearchListener {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

    private TextView mTouristInfoTxt;
    private TextView mIdCardTxt;
    private TextView mTouristMobileTxt;
    private TextView mLastDateTxt;

    private GeocodeSearch geocoderSearch;
    private MapView mapView;
    private AMap aMap;
    private Marker geoMarker;
    private LatLonPoint latLonPoint;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_detail);
        mTouristInfoTxt = (TextView) findViewById(R.id.tourist_info_txt);
        mIdCardTxt = (TextView) findViewById(R.id.tourist_id_card_txt);
        mTouristMobileTxt = (TextView) findViewById(R.id.tourist_mobile_txt);
        mLastDateTxt = (TextView) findViewById(R.id.last_date_txt);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Tourist tourist = (Tourist) bundle.get("tourist");
            int groupId = bundle.getInt("groupId");
            setActionBarTitle(tourist.getName());
            mTouristInfoTxt.setText(tourist.getName() + "  " +
                    (tourist.getGender() == 1 ? "男" : "女"));
            mIdCardTxt.setText(tourist.getIdCard() == null ? "身份证：未认证" : "身份证：" + tourist.getIdCard());
            mTouristMobileTxt.setText("手机号：" + tourist.getMobile());
            // TODO: 23/9/15 最近地理位置
            mapView = (MapView) findViewById(R.id.amap);
            mapView.onCreate(savedInstanceState);
            init();
            loadData(groupId, tourist.getTouristId());
        }
    }

    private void loadData(int groupId, int touristId) {
        VolleyRequest.Callbacks<GetTouristDetailResult> callbacks = new VolleyRequest.Callbacks<GetTouristDetailResult>() {
            @Override
            public void onResponse(GetTouristDetailResult result) {
                if (result.isResCodeOK()) {
                    if (result.getTouristInfo() != null) {
                        Tourist touristInfo = result.getTouristInfo();
                        latLonPoint = new LatLonPoint(touristInfo.getLatitude(), touristInfo.getLongitude());
                        getAddress(latLonPoint);
                    }
                }
            }

            @Override
            public void onError(VolleyError error) {
                Utils.showVolleyError(TouristDetailActivity.this, error);
            }
        };
        GetTouristDetailRequest request = new GetTouristDetailRequest(groupId, touristId, callbacks);
        MyApplication.getInstance().addToRequestQueue(request.createRequest());
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            geoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 10,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == 0) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        AMapUtil.convertToLatLng(latLonPoint), 15));
                aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
            } else {
                mapView.setVisibility(View.GONE);
                mLastDateTxt.setText("没有数据");
            }
        } else if (rCode == 27) {
            CustomToast.makeText(TouristDetailActivity.this, "网络错误", Toast.LENGTH_LONG).show();
        } else if (rCode == 32) {
            CustomToast.makeText(TouristDetailActivity.this, "key错误", Toast.LENGTH_LONG).show();
        } else {
            CustomToast.makeText(TouristDetailActivity.this, "其他错误" + rCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
