package com.llwy.llwystage.utils;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.llwy.llwystage.R;


public class CustomToast extends Toast {

	private TextView textView;
	private String text;
	private View view;
	private int time;
	private Context contexts;
	
	public CustomToast(Context context, String text, int time) {
		super(context);
		this.contexts = context;
		this.text = text;
		this.time = time;
		init();
	}
	
	private void init() {
		view = View.inflate(contexts, R.layout.custom_toast, null);
		setView(view);
		textView = (TextView) view.findViewById(R.id.textView);
		textView.setText(text);
		setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
		setDuration(time); 
	}

}
