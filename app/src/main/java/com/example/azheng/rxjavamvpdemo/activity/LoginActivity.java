package com.example.azheng.rxjavamvpdemo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.example.azheng.rxjavamvpdemo.MainActivity;
import com.example.azheng.rxjavamvpdemo.R;
import com.example.azheng.rxjavamvpdemo.base.BaseMvpActivity;
import com.example.azheng.rxjavamvpdemo.bean.CarBean;
import com.example.azheng.rxjavamvpdemo.bean.LoginBean;
import com.example.azheng.rxjavamvpdemo.contract.LoginContract;
import com.example.azheng.rxjavamvpdemo.presenter.LoginPresenter;
import com.example.azheng.rxjavamvpdemo.util.Mobile;
import com.example.azheng.rxjavamvpdemo.util.SrcScrollFrameLayout;


public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View, View.OnClickListener {
    private TextView edtPhone;
    private Button btnStart;
    LoadingDailog.Builder loadBuilder;
    LoadingDailog dialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        edtPhone = findViewById(R.id.edt_phone);
        ((SrcScrollFrameLayout) this.findViewById(R.id.fl_main)).startScroll();
        btnStart = findViewById(R.id.btn_start);
//        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((SrcScrollFrameLayout) findViewById(R.id.fl_main)).startScroll();
//            }
//        });
//        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((SrcScrollFrameLayout) findViewById(R.id.fl_main)).stopScroll();
//            }
//        });
        btnStart.setOnClickListener(this);

        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);


        // 权限申请
        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
        ) {
            // 权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{Manifest.permission.READ_PHONE_STATE}, 300);
        } else {
            // 权限已经申请，直接拍照
//            String mPhone = Mobile.mobilePhoneReplace(Mobile.getString(Mobile.mobilePhone(this), "+86"));
//
//            edtPhone.setText(Mobile.mobilePhoneReplace(mPhone));
        }


    }

    @Override
    public LoginPresenter createPresent() {
        return new LoginPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
//                mPresenter.loginPersonInfo(edtPhone.getText().toString());
                mPresenter.loginPersonInfo("15194113739");
                break;
        }
    }

    @Override
    public void onSuccess(LoginBean bean) {
        if (bean.getMessage().indexOf("成功") != -1) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("name", "" + edtPhone.getText().toString());
            startActivity(intent);

            finish();

        } else {
            Toast.makeText(LoginActivity.this, "该手机号未注册此app，请先注册后使用", Toast.LENGTH_SHORT).show();
        }

    }

    // 请求权限后会在这里回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 300:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String mPhone = Mobile.mobilePhoneReplace(Mobile.getString(Mobile.mobilePhone(this), "+86"));

                    edtPhone.setText(Mobile.mobilePhoneReplace(mPhone));
                } else {
                    finish();
                }
                break;
        }

    }

    @Override
    public void showLoading() {
        loadBuilder = new LoadingDailog.Builder(LoginActivity.this)
                .setMessage("登录中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog = loadBuilder.create();
        dialog.show();

    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void onError(Throwable throwable) {

    }
}
