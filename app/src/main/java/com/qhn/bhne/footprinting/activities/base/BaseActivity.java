package com.qhn.bhne.footprinting.activities.base;

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

import com.qhn.bhne.footprinting.App;
import com.qhn.bhne.footprinting.R;
import com.qhn.bhne.footprinting.db.DaoSession;
import com.qhn.bhne.footprinting.entries.User;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.socks.library.KLog;

import butterknife.ButterKnife;

/**
 * Created by qhn
 * on 2016/10/27 0027.
 */

public abstract class BaseActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private boolean isToolbar;
    protected DaoSession daoSession;
    public User currentUser;

    public boolean isToolbar() {
        return isToolbar;
    }

    public void setToolbar(boolean toolbar) {
        isToolbar = toolbar;
    }

    protected abstract void initViews();

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.i(getClass().getSimpleName());
        isNetworkErrThenShowMsg();//网络错误时显示错误信息
        currentUser=((App)getApplicationContext()).getUser();
        int layoutId = getLayoutId();
        setContentView(layoutId);
        ButterKnife.bind(this);

       /* if (isToolbar) {
            initToolBar();
            setStatusBarTranslucent();
        }*/
         daoSession=((App)getApplication()).getDaoSession();
        initViews();


    }


   /* private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setOnMenuItemClickListener(this);
        setSupportActionBar(toolbar);
    }
*/

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


}
