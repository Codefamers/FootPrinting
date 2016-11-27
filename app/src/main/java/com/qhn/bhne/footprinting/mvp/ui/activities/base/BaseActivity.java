package com.qhn.bhne.footprinting.mvp.ui.activities.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.Toast;

import com.qhn.bhne.footprinting.R;
import com.qhn.bhne.footprinting.db.DaoSession;
import com.qhn.bhne.footprinting.di.component.ActivityComponent;
import com.qhn.bhne.footprinting.di.component.DaggerActivityComponent;
import com.qhn.bhne.footprinting.di.module.ActivityModule;
import com.qhn.bhne.footprinting.mvp.App;
import com.qhn.bhne.footprinting.mvp.entries.User;
import com.qhn.bhne.footprinting.mvp.presenter.base.BasePresenter;
import com.qhn.bhne.footprinting.utils.StatusBarCompat;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.socks.library.KLog;

import butterknife.ButterKnife;

/**
 * Created by qhn
 * on 2016/10/27 0027.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    protected DaoSession daoSession;
    public User currentUser;
    protected ActivityComponent mActivityComponent;
    protected T mPresenter;

    protected abstract void initViews();

    protected abstract int getLayoutId();

    protected abstract void initInjector();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.i(getClass().getSimpleName());
        isNetworkErrThenShowMsg();//网络错误时显示错误信息
        currentUser = ((App) getApplicationContext()).getUser();
        int layoutId = getLayoutId();
        setContentView(layoutId);
        ButterKnife.bind(this);
        initActivityComponent();
        initInjector();
        daoSession = ((App) getApplication()).getDaoSession();
        initMapView(savedInstanceState);
        initToolBar();
        initViews();

        if (mPresenter!=null) {
            mPresenter.create();
        }
    }

    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent
                .builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((App)getApplication()).getApplicationComponent())
                .build();

    }


    protected void initMapView(Bundle savedInstanceState) {
    }




    //初始化工具栏
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            StatusBarCompat.compat(this, getResources().getColor(R.color.colorPrimaryDark));
        }


    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {

                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNetworkErrThenShowMsg() {
        if (!isNetworkAvailable()) {
            //TODO: 刚启动app Snackbar不起作用，延迟显示也不好使，这是why？
            Toast.makeText(App.getAppContext(), App.getAppContext().getString(R.string.internet_error),
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    // TODO:适配4.4
    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void setStatusBarTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
        }
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    public void setmActivityComponent(ActivityComponent mActivityComponent) {
        this.mActivityComponent = mActivityComponent;
    }

}
