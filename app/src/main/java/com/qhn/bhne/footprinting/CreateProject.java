package com.qhn.bhne.footprinting;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qhn.bhne.footprinting.db.ProjectDao;
import com.qhn.bhne.footprinting.entries.Project;

import java.util.Date;

import butterknife.BindView;

public class CreateProject extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txt_project_name)
    TextView txtProjectName;
    @BindView(R.id.txt_project_edit_time)
    TextView txtProjectEditTime;
    @BindView(R.id.rec_project)
    RelativeLayout recProjectPhoto;
    @BindView(R.id.et_project_batch)
    AutoCompleteTextView etProjectBatch;
    @BindView(R.id.et_project_num)
    AutoCompleteTextView etProjectNum;
    @BindView(R.id.et_project_des)
    AutoCompleteTextView etProjectDes;
    @BindView(R.id.et_project_remark)
    AutoCompleteTextView etProjectRemark;
    @BindView(R.id.activity_create_project)
    LinearLayout activityCreateProject;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.sp_project_category)
    AppCompatSpinner spProjectCategory;
    private String name;//项目名称

    private String category;//项目类别

    private int definition;//定义号
    private int batch;//批次
    private String remark;//备注
    private String date;//创建日期
    private String describe;
    private Intent intent;

    @Override
    protected void initViews() {
        intent=getIntent();
        name = intent.getStringExtra("PROJECT_NAME");
        txtProjectName.setText(name);
        date = DateFormat.dateFormat(new Date());
        txtProjectEditTime.setText(date);
        initToolBar();
        initFab();
    }

    private void initFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (insertProject()!=0) {
                    Snackbar.make(view, "项目创建成功", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    intent.putExtra("PROJECT_NAME",name);
                    setResult(RESULT_OK,intent);
                    finish();
                }


            }
        });
    }

    private Long insertProject() {
        spProjectCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //选择列表项的操作
                switch (position) {
                    case 1:
                        category = "城网";
                        break;
                    case 2:
                        category = "电网";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //未选中时候的操作
            }
        });

        String strDefinition=etProjectNum.getText().toString();
        String strBatch=etProjectBatch.getText().toString();
        definition= TextUtils.isEmpty(strDefinition)?0:Integer.parseInt(strDefinition);
        batch= TextUtils.isEmpty(strDefinition)?0:Integer.parseInt(strBatch);
        remark=etProjectRemark.getText().toString();
        describe=etProjectDes.getText().toString();
        Project project = new Project(null, name, category, definition, batch, remark, date, describe);
        ProjectDao projectDao=daoSession.getProjectDao();
        return projectDao.insert(project);
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        StatusBarCompat.compat(this, getResources().getColor(R.color.colorPrimaryDark));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_project;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }


}
/*et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 4) {
                    inputLayout.setErrorEnabled(true);
                    inputLayout.setError("姓名长度不能超过4个");
                } else {
                    inputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });*/