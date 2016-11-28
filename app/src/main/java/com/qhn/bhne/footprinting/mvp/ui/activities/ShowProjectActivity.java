package com.qhn.bhne.footprinting.mvp.ui.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qhn.bhne.footprinting.R;
import com.qhn.bhne.footprinting.interfaces.Constants;
import com.qhn.bhne.footprinting.interfaces.PopClickItemCallBack;
import com.qhn.bhne.footprinting.mvp.entries.FileContent;
import com.qhn.bhne.footprinting.mvp.entries.Project;
import com.qhn.bhne.footprinting.mvp.presenter.impl.ShowProjectPI;
import com.qhn.bhne.footprinting.mvp.ui.activities.base.BaseActivity;
import com.qhn.bhne.footprinting.mvp.ui.adapter.ExpandProjectListView;
import com.qhn.bhne.footprinting.mvp.view.ShowProjectView;
import com.qhn.bhne.footprinting.utils.DateFormat;
import com.qhn.bhne.footprinting.utils.MyUtils;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ShowProjectActivity extends BaseActivity
        implements ShowProjectView, NavigationView.OnNavigationItemSelectedListener, PopClickItemCallBack {
    public static final int CREATE_PROJECT = 1;
    public static final int CREATE_CONSTRUCTION = 2;
    public static final int CREATE_FILE = 3;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content_show_project)
    RelativeLayout contentShowProject;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.exl_project)
    ExpandableListView exlProject;
    private EditText editText;
    private ImageView imageHint;
    private Button btnCancel;
    private Button btnConfirm;
    private View dialogView;
    private ExpandProjectListView expandableListAdapter;
    private List<Project> projectList;
    private long parentID;
    private AlertDialog dialog;

    @Inject
    ShowProjectPI showProjectPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_project;
    }

    @Override
    protected void initInjector() {

        getActivityComponent().inject(this);

    }

    @Override
    protected void initViews() {
        showProjectPresenter.attachView(this);
        showProjectPresenter.create();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(CREATE_PROJECT, 0);
            }
        });
        initDrawerLayout();
        initExpandableListView();

    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initExpandableListView() {

        projectList = showProjectPresenter.queryProjectList();
        expandableListAdapter = new ExpandProjectListView(projectList, daoSession, this);
        exlProject.setAdapter(expandableListAdapter);
    }


    public void createDialog(int createCategory, long parentId) {
        initCustomDialog();
        initAlertDialog(createCategory, parentId);
    }

    private void initAlertDialog(final int createCategory, final long parentId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
         dialog = builder.create();
        dialog.setTitle("新建项目/工程");
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strProjectName = editText.getText().toString();
                Boolean isPass = showProjectPresenter.verifyRepeatName(createCategory, strProjectName, parentId);
                if (isPass) {
                    dialog.dismiss();
                    Intent intent = MyUtils.buildCreateProjectIntent(ShowProjectActivity.this, parentId, 0L, createCategory, strProjectName);
                    startActivityForResult(intent, 100);
                }


            }


        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setView(dialogView);
        dialog.setCancelable(false);
        dialog.show();
    }


    private void showHintIcon(Boolean showWhichIcon) {
        if (showWhichIcon) {
            imageHint.setImageResource(R.mipmap.ic_done_white_18dp);
            imageTint(imageHint, 0xFF, 0x40, 0xC4, 0xFF);

        } else {
            imageHint.setImageResource(R.mipmap.ic_info_outline_white_18dp);
            imageTint(imageHint, 0xFF, 0xC5, 0x11, 0x62);

        }
    }

    private void imageTint(ImageView imageView, int alpha, int red, int green, int blue) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setImageTintList(ColorStateList.valueOf(Color.argb(alpha, red, green, blue)));
        } else
            imageView.setColorFilter(Color.argb(alpha, red, green, blue));
    }

    private void initCustomDialog() {
        LayoutInflater inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.item_new_project, (ViewGroup) findViewById(R.id.dialog));
        editText = (EditText) dialogView.findViewById(R.id.ed_project);
        imageHint = (ImageView) dialogView.findViewById(R.id.img_hint);
        btnConfirm = (Button) dialogView.findViewById(R.id.btn_confirm);
        btnCancel = (Button) dialogView.findViewById(R.id.btn_cancel);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_camera:
                break;
            case R.id.nav_gallery:
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_send:
                break;
            case R.id.nav_slideshow:
                break;
            case R.id.nav_view:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public void refreshView(List<Project> list) {
        daoSession.clear();
        projectList.clear();
        projectList.addAll(list);
        expandableListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRepeatIcon(Boolean isShowIcon) {
        showHintIcon(isShowIcon);
    }

    @Override
    public void createdFile(String fileName) {
        FileContent fileContent=new FileContent(null,parentID,null,fileName,currentUser.getName(), DateFormat.dateFormat(new Date()));
        fileContent.setFileMax(Constants.FILE_MAX);
        showProjectPresenter.createFile(fileContent);
    }

    @Override
    public void cancelDialog() {
        if (dialog!=null) {
            dialog.cancel();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode==RESULT_OK) {
                daoSession.clear();
                showProjectPresenter.refreshData();
            }
        }
    }


    @Override
    public void clickItem(MenuItem menuItem, int createProject, long parentID, long itemID) {
        this.parentID = parentID;
        switch (menuItem.getItemId()) {
            case R.id.add_construction:
                createDialog(createProject, parentID);
                break;
            case R.id.look_info:
                Intent intent = MyUtils.buildCreateProjectIntent(ShowProjectActivity.this, parentID, itemID, createProject - 1, null);
                startActivityForResult(intent, 100);
                break;
            case R.id.delete_project:
                daoSession.clear();
                showProjectPresenter.deleteItem(createProject,parentID, itemID);
                showProjectPresenter.refreshData();
                break;
        }
    }

}
