package com.llwy.llwystage.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xmm.tcy.util.AppManager;

public abstract class BaseFragmentActivity extends FragmentActivity {

	protected Context ct;
	protected XBDApplication app;

	protected View loadingView;


	protected LinearLayout loadfailView;

	protected TextView titleTv;
	private boolean isActive;
	private boolean stopStatus;
	private boolean resumeStatus;
	private boolean nojump;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		AppManager.getAppManager().addActivity(this);
		ct = this;
		app = (XBDApplication) getApplication();
		nojump = false;
		initView();

		initData();
	}

	protected void initTitleBar() {
//		titleTv = (TextView) findViewById(R.id.bar_tv_title);
	}

	protected abstract void initView();

	protected abstract void initData();

	protected abstract void processClick(View v);

	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}
		processClick(v);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
}
