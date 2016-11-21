package com.qhn.bhne.footprinting.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.qhn.bhne.footprinting.App;
import com.qhn.bhne.footprinting.R;
import com.qhn.bhne.footprinting.activities.base.BaseActivity;
import com.qhn.bhne.footprinting.db.UserDao;
import com.qhn.bhne.footprinting.entries.User;
import com.qhn.bhne.footprinting.utils.DateFormat;
import com.socks.library.KLog;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via userName/password.
 */
public class LoginActivity extends BaseActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.email)
    AutoCompleteTextView mEmailView;
    @BindView(R.id.password)
    EditText mPasswordView;
    @BindView(R.id.cb_remember_password)
    AppCompatCheckBox cbRememberPassword;
    @BindView(R.id.email_sign_in_button)
    Button mEmailSignInButton;
    @BindView(R.id.email_login_form)
    LinearLayout emailLoginForm;
    @BindView(R.id.login_form)
    ScrollView mLoginFormView;
    @BindView(R.id.txt_forget_password)
    TextView txtForgetPassword;
    @BindView(R.id.btn_create_account)
    Button btnCreateAccount;
    @BindView(R.id.rec_refer_password)
    RelativeLayout recReferPassword;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private Boolean loginOrRegister = false;
    private String userName;
    private String password;
    private UserDao userDao;
    private SharedPreferences sharedPre;
    private SharedPreferences.Editor editor;

    @Override
    protected void initViews() {
        userDao = daoSession.getUserDao();
        sharedPre=((App)getApplication()).getSharedPre();
        editor=sharedPre.edit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        userName=sharedPre.getString("USER_NAME",null);
        if (sharedPre.getBoolean("IS_REMEMBER_PASSWORD",false)&&userName!=null) {
            login();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.email_sign_in_button, R.id.btn_create_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.email_sign_in_button:
                if (loginOrRegister)
                    attemptRegister();//注册
                else
                    attemptLogin();//登录
                break;
            case R.id.btn_create_account:
                if (loginOrRegister) //返回登录
                    returnLogin();
                else //注册账号
                    registerAccount();
                break;
        }
    }

    private void attemptLogin() {
        verifyInfo();
        login();

    }

    private void login() {
        User user = isRegister(userName);
        if (user != null) {
            ((App) getApplicationContext()).setUser(user);
            startActivity(new Intent(this, ShowProjectActivity.class));
            finish();
        } else {
            Snackbar.make(root, "登录出错请检查用户名与密码是否正确", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void attemptRegister() {
        verifyInfo();
        User user = new User(null, userName, password, "23131231", DateFormat.dateFormat(new Date()));
        try {
            userDao.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(root, "此用户名已被注册", Snackbar.LENGTH_SHORT).show();
        }


    }

    private User isRegister(String userName) {
        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(UserDao.Properties.Name.eq(userName));
        Query<User> query = qb.build();
        return query.unique();
    }
    //验证并记录view上的数据
    private void verifyInfo() {
        // Reset errors.输入错误提示
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        userName = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid userName address.
        if (TextUtils.isEmpty(userName)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }

        editor.putBoolean("IS_REMEMBER_PASSWORD",cbRememberPassword.isChecked());
        if (cbRememberPassword.isChecked()) {
            editor.putString("USER_NAME",userName);
            editor.putString("USER_PASSWORD",password);
        }
        editor.commit();
        KLog.d("是否记住密码"+cbRememberPassword.isChecked());
    }


    private void registerAccount() {
        loginOrRegister = true;
        btnCreateAccount.setText("返回登录");
        txtForgetPassword.setVisibility(View.INVISIBLE);
        cbRememberPassword.setVisibility(View.INVISIBLE);
        mEmailSignInButton.setText("注册");
    }

    private void returnLogin() {
        btnCreateAccount.setText("创建账户");
        mEmailSignInButton.setText("登录");
        txtForgetPassword.setVisibility(View.VISIBLE);
        cbRememberPassword.setVisibility(View.VISIBLE);
        loginOrRegister = false;
    }


    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }


}

