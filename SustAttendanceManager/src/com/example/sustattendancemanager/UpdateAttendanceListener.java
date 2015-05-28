package com.example.sustattendancemanager;

import android.content.Context;

public interface UpdateAttendanceListener {
	void getErrorMessage(Exception e);
	String attendanceNumber(boolean isPresent);
	Context classContext();
	Key_value[] getKeyVal(String attendance,String reg_no);
	NameRegAttend objectToModify(int pos);
	String getUri();
}
