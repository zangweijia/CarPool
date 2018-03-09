package com.llwy.llwystage.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;


import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 * 该类使用单例模式来管理，使整个程序在任何地方都可以访问这个Activity栈
 */
public class AppManager {

	private static Stack<Activity> activityStack;
	private static AppManager instance;

	private AppManager() {
	}

	/**
	 * 单一实例
	 */
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	/**
	 * 添加Activity到栈
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}


	public Activity indexOfActivity(int index) {
		if (index < 0 || index >= activityStack.size()) {
			return null;
		}
		Activity activity = activityStack.elementAt(index);
		return activity;
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 返回当前activity的索引号
	 * */
	public int currentActivityIndex() {
		return activityStack.size() - 1;
	}

//	/**
//	 * 回到索引位置的Activity
//	 */
//	public void finishActivity(int index) {
//		if (index < 0 || index > activityStack.size() - 2)
//			return;
//		for (int i = activityStack.size() - 1; i >= index; i--) {
//			if (null != activityStack.get(i)) {
//				if (activityStack.get(i).getClass() == MainActivity.class){
//					continue;
//				}
//				activityStack.get(i).finish();
//			}
//		}
//	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = activityStack.size() - 1; i >= 0; i--) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
	}


	/**
	 * 应用程序退出
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.killBackgroundProcesses(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
		}
	}
}