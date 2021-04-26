package com.example.azheng.rxjavamvpdemo.activity.ft;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azheng.rxjavamvpdemo.R;
import com.example.azheng.rxjavamvpdemo.activity.take.FullyGridLayoutManager;
import com.example.azheng.rxjavamvpdemo.activity.take.GridImageAdapter;
import com.example.azheng.rxjavamvpdemo.net.RetrofitClient;
import com.example.azheng.rxjavamvpdemo.util.TimeUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SendPostActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivLeft;
    private RecyclerView recyclerView;
    private GridImageAdapter mAdapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int themeId;
    private int chooseMode = PictureMimeType.ofAll();
    private ArrayList<File> list = new ArrayList<File>();
    private EditText edtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_post);
        TextView tvTitle = findViewById(R.id.tv_title_toolbar);
        tvTitle.setText("心动时刻");
        TextView tvContent = findViewById(R.id.tv_right);
        tvContent.setVisibility(View.VISIBLE);
        tvContent.setText("发表");
        ivLeft = findViewById(R.id.iv_back_toolbar);
        edtTitle = findViewById(R.id.edt_title);
        ivLeft.setOnClickListener(this);
        tvContent.setOnClickListener(this);
        themeId = R.style.picture_default_style;
        recyclerView = findViewById(R.id.recycler);
        initTakePhoto(recyclerView);
    }

    private void initTakePhoto(RecyclerView recyclerView) {

        FullyGridLayoutManager manager = new FullyGridLayoutManager(SendPostActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        mAdapter = new GridImageAdapter(SendPostActivity.this, onAddPicClickListener);
        mAdapter.setList(selectList);
        mAdapter.setSelectMax(6);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(SendPostActivity.this).themeStyle(themeId).openExternalPreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(SendPostActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(SendPostActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(SendPostActivity.this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(SendPostActivity.this);
                } else {
                    Toast.makeText(SendPostActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_toolbar:
                finish();
                break;
            case R.id.tv_right:
                if (list.size() == 0) {
                    Toast.makeText(SendPostActivity.this, "至少选择一张照片！", Toast.LENGTH_SHORT).show();
                    return;
                }
                String edtName = edtTitle.getText().toString().trim();

                upload(edtName, TimeUtils.getTime());
                break;
        }
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(SendPostActivity.this)
                    .openGallery(chooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(6)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选, PictureConfig.SINGLE
                    .previewImage(true)// 是否可预览图片
                    .previewVideo(true)// 是否可预览视频
                    .enablePreviewAudio(true) // 是否可播放音频
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                    .enableCrop(false)// 是否裁剪
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    //.compressSavePath(getPath())//压缩图片保存地址
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .isGif(true)// 是否显示gif图片
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(false)// 是否圆形裁剪
                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .openClickSound(false)// 是否开启点击声音
                    .selectionMedia(selectList)// 是否传入已选图片
                    .withAspectRatio(3, 2)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                    .isDragFrame(false)// 是否可拖动裁剪框(固定)
//                    .videoMaxSecond(15)
//                    .videoMinSecond(10)
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    //.rotateEnabled(true) // 裁剪是否可旋转图片
                    //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                    //.videoQuality()// 视频录制质量 0 or 1
                    //.videoSecond()//显示多少秒以内的视频or音频也可适用
                    //.recordVideoSecond()//录制视频秒数 默认60s
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    list.clear();
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                        File file = new File(media.getPath());
                        list.add(file);
                    }
                    mAdapter.setList(selectList);

                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }


    private void upload(String edtName,String time) {

        List<MultipartBody.Part> parts = new ArrayList<>(list.size());
        for (File file : list) {

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
            parts.add(part);
        }
        RequestBody description = RequestBody.create( MediaType.parse("multipart/form-data"), edtName);
        RequestBody requestTime= RequestBody.create(MediaType.parse("multipart/form-data"), time);


//        执行请求
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().uploadFiles(parts, description,requestTime);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Toast.makeText(SendPostActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                Toast.makeText(SendPostActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


}
