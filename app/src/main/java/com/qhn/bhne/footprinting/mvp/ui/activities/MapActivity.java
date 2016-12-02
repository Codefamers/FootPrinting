package com.qhn.bhne.footprinting.mvp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.qhn.bhne.footprinting.R;
import com.qhn.bhne.footprinting.db.SpotDao;
import com.qhn.bhne.footprinting.interfaces.Constants;
import com.qhn.bhne.footprinting.mvp.entries.Spot;
import com.qhn.bhne.footprinting.mvp.ui.activities.base.BaseActivity;
import com.qhn.bhne.footprinting.mvp.view.MapActivityView;
import com.qhn.bhne.footprinting.utils.MyUtils;
import com.socks.library.KLog;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.amap.api.maps.AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM;


public class MapActivity extends BaseActivity implements LocationSource, AMapLocationListener, MapActivityView, AMap.OnMarkerClickListener,
        AMap.OnInfoWindowClickListener, AMap.OnMarkerDragListener, AMap.OnMapLoadedListener,
        View.OnClickListener, AMap.InfoWindowAdapter {

    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    //地图显示
    private AMap aMap;//地图对象

    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private OnLocationChangedListener locationListener = null;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;

    private UiSettings mUiSettings;//控制地图上的icon

    private static final String TAG = "MapActivity";
    private LatLng latLng;

    private Long parentId;

    private float distance;//与上一个点之间的间距
    private static final int REQUEST_CODE = 573;
    private AMapLocation aMapLocation;
    private LatLng locationLatLng;
    private ArrayList<Spot> spotList;
    private long itemId;
    private  Marker editdMark;

    @Override
    protected void initMapView(Bundle savedInstanceState) {
        super.initMapView(savedInstanceState);

        parentId = getIntent().getLongExtra("CHILD_ID", -1);
        itemId=getIntent().getLongExtra("ITEM_ID",-1);

        mapView.onCreate(savedInstanceState);
        setUpMap();
        //开始定位
        initLoc();

    }

    private void setUpMap() {

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
        aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
        //addMarkersToMap();// 往地图上添加marker
    }

    @Override
    public void dragMaker() {

    }

    @Override
    public void editSpotComplete() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showErrorMessage(String errorMessage) {

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
        getActivityComponent().inject(this);
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
        markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        markerOption.draggable(true);
        markerOption.setFlat(true);
        //设置多少帧刷新一次图片资源

        markerOption.period(60);

        markerOption.position(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
        StringBuffer buffer = new StringBuffer();
        buffer.append(amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
        //标题
        markerOption.title(buffer.toString());
        //子标题
       /* markerOption.snippet();*/

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
        aMapLocation = amapLocation;
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
                locationLatLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                mLocationClient.addGeoFenceAlert("fenceId",
                        amapLocation.getLatitude(), amapLocation.getLongitude(),
                        10, -1, null);
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
    public void onClick(View v) {

    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override//是否需要添加所有marker
    public void onMapLoaded() {
        spotList=queryList(parentId);
        for (Spot spot : spotList) {
            LatLng latLng=new LatLng(spot.getLatitude(),spot.getLongitude());

             aMap.addMarker(new MarkerOptions()
                     .position(latLng)
                    .title(spot.getLocation())
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .draggable(false));
        }

    }

    private ArrayList<Spot> queryList(long itemId) {
        QueryBuilder<Spot> queryBuilder=daoSession.getSpotDao().queryBuilder().where(SpotDao.Properties.ParentID.eq(parentId));

        return (ArrayList<Spot>) queryBuilder.list();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override//开始拖动
    public void onMarkerDragStart(Marker marker) {

    }

    @Override//拖到中
    public void onMarkerDrag(Marker marker) {

    }

    @Override//拖到结束
    public void onMarkerDragEnd(Marker marker) {
        LatLng markerLatLng = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);

        KLog.d("des"+marker.getPosition().describeContents());
        distance = AMapUtils.calculateLineDistance(locationLatLng, markerLatLng);
        if (distance > 200) {
            Toast.makeText(this, "勘测点的位置不能超过定位位置200m,当前距离" + distance + "m", Toast.LENGTH_SHORT).show();
            marker.remove();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode==RESULT_OK) {
                Toast.makeText(this, "成功返回i", Toast.LENGTH_SHORT).show();
                editdMark.setDraggable(false);
            }

        }
    }

    /**
     * 监听自定义infowindow窗口的infowindow事件回调
     */
    @Override
    public View getInfoWindow(Marker marker) {

        View infoWindow = getLayoutInflater().inflate(
                R.layout.custom_info_window, null);

        render(marker, infoWindow);
        return infoWindow;
    }

    /**
     * 自定义infowinfow窗口
     */
    public void render(final Marker marker, View view) {

        String title = marker.getTitle();
        TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (title != null) {
            titleUi.setText(title);
        } else {
            titleUi.setText("");
        }
        Button btnEdit = (Button) view.findViewById(R.id.btn_confirm);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (distance > 200)
                    Toast.makeText(MapActivity.this, "勘测点的位置不能超过定位位置200m,当前距离" + distance + "m", Toast.LENGTH_SHORT).show();
                else
                    startEditSpot(marker);

            }
        });
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marker.remove();
            }
        });
    }

    private void startEditSpot(Marker marker) {
        editdMark=marker;
        Intent intent = MyUtils.buildCreateProjectIntent(MapActivity.this, parentId, 0L, ShowProjectActivity.CREATE_SPOT, marker.getId());
        int batch=Integer.valueOf(marker.getId().substring(6,marker.getId().length()))-1;
        intent.putExtra("BATCH", batch);
        intent.putExtra("LATITUDE", marker.getPosition().latitude);
        intent.putExtra("LONGITUDE", marker.getPosition().longitude);
        intent.putExtra("DISTANCE", distance);
        intent.putExtra("LOCATION",marker.getTitle());
        startActivityForResult(intent, REQUEST_CODE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_collect:
                Toast.makeText(this, "详情", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MapActivity.this,SpotDetailActivity.class);
                intent.putExtra("PARENT_ID",parentId);
                startActivity(intent);
                break;
            case R.id.action_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_edit:
                aMap.addMarker(getMarkerOptions(aMapLocation));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

}
