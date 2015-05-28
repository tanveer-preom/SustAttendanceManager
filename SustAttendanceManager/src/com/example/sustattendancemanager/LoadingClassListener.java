package com.example.sustattendancemanager;

import android.app.Activity;

interface LoadingClassListener {
	public Activity getContext();
	public void onPostExecuted(String jsonresult);
}
