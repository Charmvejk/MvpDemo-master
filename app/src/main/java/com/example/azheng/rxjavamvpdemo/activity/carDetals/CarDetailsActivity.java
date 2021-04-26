package com.example.azheng.rxjavamvpdemo.activity.carDetals;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.azheng.rxjavamvpdemo.R;


public class CarDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView webView;
    private ImageView ivLeft;
    private String url1;
    private ProgressBar proWebView; // 进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        webView = findViewById(R.id.webview);
        TextView tvTitle = findViewById(R.id.tv_title_toolbar);
        tvTitle.setText(getIntent().getStringExtra("mListtitle"));
        ivLeft = findViewById(R.id.iv_back_toolbar);
        proWebView = (ProgressBar) findViewById(R.id.pro_webview);
        ivLeft.setOnClickListener(this);
        url1 = getIntent().getStringExtra("mList");
        WebSettings webSettings = webView.getSettings();
//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可


//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(webChromeClient);
        webView.loadUrl(url1);
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);
                //返回true
                return true;
            }
        });

    }
    // 获取加载进度
    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                proWebView.setVisibility(View.GONE);
            } else {
                if (proWebView.getVisibility() == View.GONE) {
                    proWebView.setVisibility(View.VISIBLE);
                }
                proWebView.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    };
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_toolbar:
                finish();
                break;
        }
    }
}
