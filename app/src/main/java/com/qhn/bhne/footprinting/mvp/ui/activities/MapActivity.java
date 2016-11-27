package com.qhn.bhne.footprinting.mvp.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.qhn.bhne.footprinting.R;
import com.qhn.bhne.footprinting.mvp.ui.activities.base.BaseActivity;
import com.qhn.bhne.footprinting.utils.StatusBarCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.amap.api.maps.AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM;


public class MapActivity extends BaseActivity implements LocationSource, AMapLocationListener {


    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_map)
    RelativeLayout activityMap;

    //地图显示
    private AMap aMap;//地图对象
    private MapView mapView;//地图控件

    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private OnLocationChangedListener locationListener = null;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;

    private UiSettings mUiSettings;//控制地图上的icon

    private static final String TAG = "MapActivity";
    private LatLng latLng;

    @Override
    protected void initMapView(Bundle savedInstanceState) {
        super.initMapView(savedInstanceState);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        mUiSettings = aMap.getUiSettings();
        //设置点击定位按钮后的回调
        aMap.setLocationSource(this);
        //是否显示定位按钮
        mUiSettings.setMyLocationButtonEnabled(true);
        //是否可触发定位并显示定位层
        aMap.setMyLocationEnabled(true);

        //设置层级控制按钮的位置
        mUiSettings.setZoomPosition(ZOOM_POSITION_RIGHT_BUTTOM);

        mUiSettings.setScaleControlsEnabled(true);

        //定位的小图标 默认是蓝
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        myLocationStyle.radiusFillColor(android.R.color.transparent);
        myLocationStyle.strokeColor(android.R.color.transparent);
        aMap.setMyLocationStyle(myLocationStyle);


        //开始定位
        initLoc();


        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.setSnippet("我被点击了");
                return false;
            }
        });
        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                finish();
            }
        });

    }

    @Override
    protected void initViews() {
       /* setSupportActionBar(toolbar);
        StatusBarCompat.compat(this, getResources().getColor(R.color.colorPrimaryDark));*/
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected void initInjector() {

    }


    private void initLoc() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationOption = new AMapLocationClientOption();

        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode
                .Hight_Accuracy);//设置定位模式
        mLocationOption.setNeedAddress(true);//是否需要返回地址信息
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        mLocationOption.setInterval(200000);//设置连续定位 setOnceLocation(true);一次定位
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        mLocationClient.setLocationOption(mLocationOption);//设置定位参数
        mLocationClient.startLocation();


    }

    private MarkerOptions getMarkerOptions(AMapLocation amapLocation) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
        markerOption.draggable(true);

        StringBuffer buffer = new StringBuffer();
        buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity()
                + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
        markerOption.setFlat(true);
        //标题
        markerOption.title(buffer.toString());
        //子标题
        markerOption.snippet("这里好火");
        //设置多少帧刷新一次图片资源
        markerOption.period(60);
        return markerOption;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mLocationClient.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        mLocationClient.stopLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        mLocationClient.startLocation();
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        locationListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        locationListener = null;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {

        // Log.d(TAG, "onLocationChanged:  \"定位回调\"");
        if (amapLocation != null) {

            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息
                amapLocation.getCity();//城市信息
                amapLocation.getDistrict();//城区信息
                amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码
                aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                Log.d(TAG, "onLocationChanged: " + amapLocation.getLongitude() + "\n" + amapLocation.getLatitude());
                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {


                    //设置缩放级别

                    //定位地址
                    latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));


                    //点击定位按钮 能够将地图的中心移动到定位点
                    locationListener.onLocationChanged(amapLocation);

                    //添加图钉
                    //aMap.addMarker(getMarkerOptions(amapLocation));
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getProvince() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
                    Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                    isFirstLoc = false;
                }
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

       /* //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/
        switch (id) {
            case R.id.action_collect:
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
/* public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result=hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
* */