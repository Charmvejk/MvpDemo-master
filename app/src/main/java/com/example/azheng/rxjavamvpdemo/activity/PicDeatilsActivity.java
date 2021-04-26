package com.example.azheng.rxjavamvpdemo.activity;

import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.azheng.rxjavamvpdemo.JBrowseImgActivity;
import com.example.azheng.rxjavamvpdemo.R;
import com.example.azheng.rxjavamvpdemo.base.BaseActivity;
import com.example.azheng.rxjavamvpdemo.util.JImageShowUtil;
import com.example.azheng.rxjavamvpdemo.util.JMatrixUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PicDeatilsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivHead;
    private String[] mPaths = new String[]{
//            "http://119.188.171.201:38080/query_pic",
            "http://119.188.171.201:38080/query_pic",
//            "http://img.hb.aicdn.com/652b269af2818f6f1c468399e00152d73d0a7beb29d1e-2vnLBW_fw658",
//            "http://img.hb.aicdn.com/b8ce046106dc17ebb3782351f2a493b52daf149611f57-YkEgOp_fw658"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_deatils);
        initView();
    }


    private void initView() {
        TextView tvBar = findViewById(R.id.tv_title_toolbar);
        tvBar.setText("个人信息");
        ImageView ivLeft = findViewById(R.id.iv_back_toolbar);

        ivHead = findViewById(R.id.iv_head);
        ivLeft.setOnClickListener(this);
        findViewById(R.id.ll_pic).setOnClickListener(this);
        JImageShowUtil.displayImage(mPaths[0], ivHead);
//        JImageShowUtil.displayImage(mPaths[1], mIvTest1);
//        JImageShowUtil.displayImage(mPaths[2], mIvTest2);
//        JImageShowUtil.displayImage(mPaths[3], mIvTest3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_toolbar:
                finish();
                break;

            case R.id.ll_pic:
                startBrowse(0);
                break;

        }
    }

    private void startBrowse(int pos) {
        List<Rect> rects = new ArrayList<>();
        rects.add(JMatrixUtil.getDrawableBoundsInView(ivHead));
//        rects.add(JMatrixUtil.getDrawableBoundsInView(mIvTest1));
//        rects.add(JMatrixUtil.getDrawableBoundsInView(mIvTest2));
//        rects.add(JMatrixUtil.getDrawableBoundsInView(mIvTest3));
        JBrowseImgActivity.start(this, new ArrayList<>(Arrays.asList(mPaths)), pos, rects);
    }
}
