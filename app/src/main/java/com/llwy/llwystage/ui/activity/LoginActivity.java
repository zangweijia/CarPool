package com.llwy.llwystage.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.sdk.model.Location;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.llwy.llwystage.R;
import com.llwy.llwystage.base.BaseActivity;
import com.llwy.llwystage.base.Constants;
import com.llwy.llwystage.base.ResultResponse;
import com.llwy.llwystage.model.News;
import com.llwy.llwystage.model.UserBean;
import com.llwy.llwystage.presenter.BaseDetailPresenter;
import com.llwy.llwystage.presenter.LoginPresenter;
import com.llwy.llwystage.utils.SignUtil;
import com.llwy.llwystage.view.IBaseDetailsView;
import com.llwy.llwystage.view.ILoginView;

import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZWJ on 2018/3/13.
 */

public class LoginActivity extends BaseActivity implements ILoginView, IBaseDetailsView {

    private LoginPresenter mPresenter;
    private BaseDetailPresenter baseDetailPresenter;

    private ImageView image;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        mPresenter = new LoginPresenter(this);

        baseDetailPresenter = new BaseDetailPresenter(this);
        image = (ImageView) findViewById(R.id.image);

    }

    private AlertDialog.Builder alertDialog;

    @Override
    protected void initData() {
//        Map<String, String> map = new HashMap<>();
//        map.put("UserName", "154632121");
//        map.put("PSW", "111111");
//        map.put("Sign",SignUtil.GetSignNew(map));
//
//
//        mPresenter.Login(map);
//        baseDetailPresenter.getNews("");

        OCR.getInstance().initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
            }

            @Override
            public void onError(OCRError ocrError) {
                ocrError.printStackTrace();
                Toast.makeText(ct, "licence方式获取token失败", Toast.LENGTH_SHORT).show();
            }
        }, ct);


        alertDialog = new AlertDialog.Builder(this);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ct, CameraActivity.class);
//                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, getSaveFile(getApplication()).getAbsolutePath());
//                intent.putExtra(CameraActivity.KEY_NATIVE_TOKEN,
//                        OCR.getInstance().getLicense());
//                intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
//                        true);
//                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);



                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_NATIVE_TOKEN,
                        OCR.getInstance().getLicense());
                intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                        true);
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, 1);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ct, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, 1);
                // 通过参数确定接口类型
                startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public void showLogin(UserBean userBean) {
        showToast(userBean.getPsw().toString() + "*******" + userBean.getUsername().toString());
    }

    @Override
    public void onGetNewsDetails(List<News> m) {
        Toast.makeText(ct, m.get(0).getCName(), Toast.LENGTH_LONG).show();
    }


    public File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
        String filePath = getSaveFile(getApplicationContext()).getAbsolutePath();
        if (!TextUtils.isEmpty(contentType)) {
            if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
            } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
            }
        }
    }


    private void recIDCard(String idCardSide, final String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);

        OCR.getInstance().recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    alertText("", result.toString());
                    int rectX = result.getAddress().getLocation().getWidth() + result.getAddress().getLocation().getLeft() + 10;
                    int rectY = result.getName().getLocation().getTop();
                    Location location = result.getIdNumber().getLocation();
                    int height = location.getTop() - rectY - 20;
                    int width = location.getWidth() + location.getLeft() - rectX + 40;
                    Bitmap ocrBitmap = BitmapFactory.decodeFile(filePath);
                    if (ocrBitmap != null) {

                        x.image().bind(image, filePath);
                        Bitmap headBitmap = Bitmap.createBitmap(ocrBitmap, rectX, rectY, width, height, null,
                                false);
//                        ivHeadShow.setImageBitmap(headBitmap);
                    }
                }
            }

            @Override
            public void onError(OCRError error) {
                alertText("", error.getMessage());
            }
        });
    }


    private void alertText(final String title, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }
}
