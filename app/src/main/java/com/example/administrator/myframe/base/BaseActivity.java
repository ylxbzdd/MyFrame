package com.example.administrator.myframe.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 13:58.
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    public BaseActivity context;
    private PermissionsChecker mPermissionsChecker;

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
//            Manifest.permission.READ_PHONE_STATE,
//            Manifest.permission.INTERNET,
//            Manifest.permission.CAMERA,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.BLUETOOTH,
//            Manifest.permission.BLUETOOTH_ADMIN,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAG,
    };
    private static final int REQUEST_CODE = 0; // 请求码
    private List<BaseActivity> activities = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        requestWindowFeature(Window.FEATURE_NO_TITLE); //去掉标题


        //更改状态栏的背景颜色和字体颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 设置状态栏底色白色
//             getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置状态栏背景颜色
            getWindow().setStatusBarColor(Color.RED);
            // 设置状态栏字体黑色
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // 设置状态栏字体白色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }


        initview();
        initdata();
        if (!activities.contains(this)) {
            activities.add(this);
        }
        mPermissionsChecker = new PermissionsChecker(this);
    }

    public abstract void initview();

    public abstract void initdata();


    @Override
    protected void onResume() {
        super.onResume();

        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        synchronized ("a") {
//            activities.remove(this);
//        }
    }

    /**
     * 设置点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.ll_fanhui:
//                finish();
//                break;
            default:
                onClickListener(v);
                break;
        }
    }

    /**
     * 重写点击事件，重写时删除suoer；
     */
    protected void onClickListener(View view) {
        throw new UnsupportedOperationException("请重写onClickListener(View view)");
    }

    //    /**
//     * 退出所有界面
//     */
    public void exit() {
//        for (BaseActivity ac : activities) {
//            ac.finish();
//        }
//        android.os.Process.killProcess(android.os.Process.myPid());
    }


}
