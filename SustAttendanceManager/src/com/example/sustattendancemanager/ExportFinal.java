package com.example.sustattendancemanager;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class ExportFinal extends Activity implements LoadingClassListener{
	private ListView studentList;
	private Button extract;
	private String username,password,coursename;
	private boolean classloaded =false;
	private JArrayIndex classes;
	private List<ExportDatas> students;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.export_result);
		username =getIntent().getExtras().getString("user_name");
		password =getIntent().getExtras().getString("password");
		coursename =getIntent().getExtras().getString("course_name");
	}
	private void initUi()
	{
		studentList =(ListView) findViewById(R.id.studentList);
		extract = (Button) findViewById(R.id.extract);
		students = new LinkedList<ExportDatas>();
	}
	@Override
	public Activity getContext() {
		// TODO Auto-generated method stub
		return this;
	}
	@Override
	public void onPostExecuted(String jsonresult) {
		// TODO Auto-generated method stub
		try {
		if(!classloaded)
		{
			classes =new JArrayIndex(jsonresult);
			classloaded=true;
			return;
		}
		JArrayIndex index = new JArrayIndex(jsonresult);
		int len=index.getLength();
		for(int i=0;i<len;i++)
		{
			index.setJObjectPosition(i);
			int present=0;
			double percentage=0;
			for(int j=0;j<classes.getLength();j++)
			{
				classes.setJObjectPosition(j);
				String key =classes.getValue("classes");
				if(index.getValue(key).compareTo("null")==0|| index.getValue(key).compareTo("0")==0)
					continue;
				else
				{
					present+=Integer.parseInt(index.getValue(key));
				}
			}
			String name = index.getValue("s_name");
			String reg =index.getValue("reg_id");
			String presents =""+present;
			String percentages ="NN";
			students.add(new ExportDatas(name,reg,presents,percentages));
		}
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
