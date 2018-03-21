package com.llwy.carpool.utils;

import android.widget.Toast;

import com.llwy.carpool.base.StageApplication;


public class ToastUtils {

    private static Toast mToast;

    /**
     * 显示Toast
     */
    public static void showToast( CharSequence text) {
        if (mToast == null) {
            mToast = Toast.makeText(StageApplication.getInstance(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }


}
