package com.llwy.llwystage.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xmm.tcy.R;
import com.example.xmm.tcy.Zpx.text.lodingDialog;
import com.example.xmm.tcy.util.DialogUtil;
import com.example.xmm.tcy.view.CustomToast;

import java.text.ParseException;


public abstract class BaseFragment extends Fragment implements OnClickListener {
	protected Context ct;
	protected XBDApplication app;
	protected View loadingView;
	protected LinearLayout loadfailView;
	protected TextView titleTv;
	protected ImageView bar_iv_left;
	protected RelativeLayout bar_rl_left;
	protected View rootView;

	protected View emptyview;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initData(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ct = getActivity();
		emptyview = LayoutInflater.from(ct).inflate(R.layout.a_empty,null);

	}
	public View getRootView(){
		return rootView;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		try {
			rootView  = initView(inflater,container);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rootView;
	}


	@Override
	public void onDestroyView() {

		super.onDestroyView();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {

		super.onDetach();
	}

	@Override
	public void onStart() {

		super.onStart();
	}

	@Override
	public void onStop() {

		super.onStop();
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
	}

	@Override
	public void onPause() {

		super.onPause();
		
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	protected abstract View initView(LayoutInflater inflater, ViewGroup container) throws ParseException;

	protected abstract void initData(Bundle savedInstanceState);
	protected abstract void processClick(View v);

	public void showToast(String msg) {
		showToast(msg, 0);
	}

	public void showToast(String msg, int time){
		CustomToast customToast = new CustomToast(ct, msg, time);
		customToast.show();
	}



	public void showLoadingView() {
		if (loadingView != null)
			loadingView.setVisibility(View.VISIBLE);
	}

	public void dismissLoadingView() {
		if (loadingView != null)
			loadingView.setVisibility(View.INVISIBLE);
	}

	public void showLoadFailView() {
		if (loadingView != null) {
			loadingView.setVisibility(View.VISIBLE);
			loadfailView.setVisibility(View.VISIBLE);
		}

	}

	public void dismissLoadFailView() {
		if (loadingView != null)
			loadfailView.setVisibility(View.INVISIBLE);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}
		processClick(v);

	}


	public lodingDialog dialogs;

	public void showProgressDialog() {
		if (dialogs == null && ct != null) {
			dialogs = (lodingDialog) DialogUtil.createProgress(ct);
		}
		dialogs.show();
	}

	protected void closeProgressDialog() {
		if (dialogs != null) {
			dialogs.dismiss();
		}
	}


}
