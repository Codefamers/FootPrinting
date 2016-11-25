package com.qhn.bhne.footprinting.mvp.ui.activities;

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

import com.qhn.bhne.footprinting.R;
import com.qhn.bhne.footprinting.mvp.ui.activities.base.BaseActivity;
import com.qhn.bhne.footprinting.db.ConstructionDao;
import com.qhn.bhne.footprinting.db.ProjectDao;
import com.qhn.bhne.footprinting.mvp.entries.Construction;
import com.qhn.bhne.footprinting.mvp.entries.Project;
import com.qhn.bhne.footprinting.utils.DateFormat;
import com.qhn.bhne.footprinting.utils.StatusBarCompat;
import com.socks.library.KLog;

import org.greenrobot.greendao.query.Query;

import java.util.Arrays;
import java.util.Date;

import butterknife.BindView;

import static android.view.View.GONE;

public class CreateProjectActivity extends BaseActivity {
    public static final int RESULT_CREATE_CONST = 201;
    public static final int RESULT_UPDATE_CONST = 202;
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
    @BindView(R.id.ll_const_info)
    LinearLayout llConstInfo;
    @BindView(R.id.ll_project_info)
    LinearLayout llProjectInfo;
    @BindView(R.id.sp_const_profession_category)
    AppCompatSpinner spConstProfessionCategory;
    @BindView(R.id.sp_const_category)
    AppCompatSpinner spConstCategory;
    @BindView(R.id.sp_press_level)
    AppCompatSpinner spPressLevel;
    private int createCategory;//创建的是工程还是项目
    private String name;//项目或者工程的名称
    private String date;//创建日期


    private String category;//项目类别
    private int definition;//定义号
    private int batch;//批次
    private String remark;//备注
    private String describe;
    private Intent intent;
    private String strProfession;
    private String strVoltageClass;
    private long parentID;
    private long itemID;
    private ProjectDao projectDao;
    private ConstructionDao constDao;

    private Construction construction;
    private Project project;

    @Override
    protected void initViews() {
        initData();

        if (createCategory == ShowProjectActivity.CREATE_PROJECT) {
            llConstInfo.setVisibility(GONE);
            spProjectCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //选择列表项的操作
                    if (position > 0) {
                        category = getResources().getStringArray(R.array.project_category)[position];
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //未选中时候的操作
                }
            });

        } else {
            llProjectInfo.setVisibility(GONE);
            //工程类别
            spConstCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    KLog.d(position);
                    //选择列表项的操作
                    if (position > 0) {
                        category = getResources().getStringArray(R.array.const_category)[position];
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //未选中时候的操作
                }
            });
            //专业类别
            spConstProfessionCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //选择列表项的操作
                    if (position > 0) {
                        strProfession = getResources().getStringArray(R.array.profession_category)[position];
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //未选中时候的操作
                }
            });
            //电压等级
            spPressLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //选择列表项的操作
                    if (position > 0) {
                        strVoltageClass = getResources().getStringArray(R.array.press_level)[position];
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //未选中时候的操作
                }
            });
        }

        initToolBar();
        initFab();
    }

    private void initData() {
        projectDao = daoSession.getProjectDao();
        constDao = daoSession.getConstructionDao();
        intent = getIntent();
        name = intent.getStringExtra("PROJECT_NAME");
        createCategory = intent.getIntExtra("EVENT_CATEGORY", 0);
        parentID = intent.getLongExtra("PROJECT_ID", -1);
        itemID = intent.getLongExtra("ITEM_ID", -1);
        if (itemID!=-1) //查看信息
            lookInfo();
        else //创建信息
            editInfo();

        txtProjectEditTime.setText(date);
        txtProjectName.setText(name);
    }

    private void editInfo() {
        date = DateFormat.dateFormat(new Date());
    }

    private void lookInfo() {

        if (createCategory==ShowProjectActivity.CREATE_PROJECT) {//查看项目信息
            project = queryProjectUnique(itemID);
            name = project.getName();
            initProjectData();
        } else {
            construction = queryConstUnique(itemID);
            name = construction.getName();
            initConstData();
        }
    }


    private void initProjectData() {
        if (project.getBatch() != 0) {
            etProjectBatch.setText(String.valueOf(project.getBatch()));
        }
        if (TextUtils.isEmpty(project.getCategory())) {
            String[] array = getResources().getStringArray(R.array.project_category);
            spProjectCategory.setSelection(Arrays.asList(array).indexOf(project.getCategory()));
        }
        if (project.getDefinition() != 0) {
            etProjectNum.setText(String.valueOf(project.getDefinition()));
        }
        if (TextUtils.isEmpty(project.getDescribe())) {
            etProjectDes.setText(project.getDescribe());
        }
        if (TextUtils.isEmpty(project.getRemark())) {
            etProjectRemark.setText(project.getRemark());
        }
        date = project.getDate();
    }

    private void initConstData() {
        name = construction.getName();
        date = construction.getDate();

        String[] array = getResources().getStringArray(R.array.const_category);

        spConstCategory.setSelection(Arrays.asList(array).indexOf(construction.getCategory()));

        String[] arrayTwo = getResources().getStringArray(R.array.profession_category);

        spConstProfessionCategory.setSelection(Arrays.asList(arrayTwo).indexOf(construction.getProfession()));


        String[] arrayThree = getResources().getStringArray(R.array.press_level);

        spPressLevel.setSelection(Arrays.asList(arrayThree).indexOf(construction.getVoltageClass()));
    }

    private Construction queryConstUnique(long itemID) {
        Query<Construction> constructionQuery = constDao
                .queryBuilder()
                .where(ConstructionDao.Properties.ConstructionId.eq(itemID))
                .build();
        return constructionQuery.unique();

    }

    private Project queryProjectUnique(Long id) {
        Query<Project> projectQuery = projectDao
                .queryBuilder()
                .where(ProjectDao.Properties.UserName.eq(currentUser.getName()), ProjectDao.Properties.ProjectId.eq(id))
                .build();

        return projectQuery.unique();
    }

    private void initFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemID==-1) {//创建信息
                    if (createCategory == ShowProjectActivity.CREATE_PROJECT)
                        createProject(view);
                    else
                        createCons(view);
                }else {
                    if (createCategory == ShowProjectActivity.CREATE_PROJECT)
                        updateProject();
                    else
                        updateCons();
                    setResult(RESULT_UPDATE_CONST, intent);
                    finish();
                }

            }


        });
    }

    private void createCons(View view) {
        if (insertConst() != 0) {
            intent.putExtra("PROJECT_NAME", name);
            setResult(RESULT_CREATE_CONST, intent);
            finish();
        } else
            Snackbar.make(view, "创建失败", Snackbar.LENGTH_SHORT).show();
    }

    private long insertConst() {
        Construction construction = new Construction(null, parentID, name, category, currentUser.getName(), strProfession, strVoltageClass, null, date);
        ConstructionDao constructionDao = daoSession.getConstructionDao();
        return constructionDao.insert(construction);

    }

    private void createProject(View view) {
        if (insertProject() != 0) {
            intent.putExtra("PROJECT_NAME", name);
            setResult(RESULT_OK, intent);
            finish();
        } else
            Snackbar.make(view, "创建失败", Snackbar.LENGTH_SHORT).show();
    }

    private Long insertProject() {
        getViewProjectData();
        Project project = new Project(null, name, currentUser.getName(), category, definition, batch, remark, date, describe);
        return projectDao.insert(project);
    }
    private void updateProject(){
        getViewProjectData();
        project.setDefinition(definition);
        project.setBatch(batch);
        project.setRemark(remark);
        project.setCategory(category);
        project.setDescribe(describe);
        projectDao.update(project);
    }
    private void updateCons() {

        construction.setCategory(category);
        construction.setProfession(strProfession);
        construction.setVoltageClass(strVoltageClass);
        constDao.update(construction);
    }
    private void getViewProjectData() {
        String strDefinition = etProjectNum.getText().toString();
        String strBatch = etProjectBatch.getText().toString();
        definition = TextUtils.isEmpty(strDefinition) ? 0 : Integer.parseInt(strDefinition);
        batch = TextUtils.isEmpty(strDefinition) ? 0 : Integer.parseInt(strBatch);
        remark = etProjectRemark.getText().toString();
        describe = etProjectDes.getText().toString();
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
