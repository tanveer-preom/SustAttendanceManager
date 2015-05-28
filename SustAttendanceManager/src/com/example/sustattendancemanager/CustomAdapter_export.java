package com.example.sustattendancemanager;

import java.util.List;



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter_export extends ArrayAdapter{

	private Context context;
	private List<ExportDatas> students;
	private String s_name,s_reg,s_attend;
	private UpdateAttendanceListener attendanceWindow;
	private class MyViewHolder
	{
		//private int pos;
		//private String s_name,s_reg;
		private TextView name,regNumber,present,percentage;
		MyViewHolder(View v)
		{
			name  = (TextView) v.findViewById(R.id.name);
			regNumber = (TextView) v.findViewById(R.id.reg_num);
			present = (TextView)v.findViewById(R.id.attended);
			percentage = (TextView) v.findViewById(R.id.percentage);
			//present.setBackgroundResource(R.drawable.btn_selector);
		}
		public void setPos(int pos)
		{
			name.setText(students.get(pos).getName());
			regNumber.setText(students.get(pos).getReg());
			present.setText(students.get(pos).getTotal());
			percentage.setText(students.get(pos).getPercent());
		}
		
		
		
		
		
	}
	
	public CustomAdapter_export(UpdateAttendanceListener listener,List<ExportDatas> students,String[] arr) {
		
		super(listener.classContext(), R.layout.export_view_row,R.id.reg_no,arr);
		this.context =listener.classContext();
		this.attendanceWindow =listener;
		this.students=students;
		Log.i("Tanvy", "inside customadapter cons");
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i("Tanvy", "inside getView");
		View row =convertView;
		MyViewHolder holder=null;
		if(row==null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row= inflater.inflate(R.layout.export_view_row, parent,false);
			holder =new MyViewHolder(row);
			row.setTag(holder);
		}
		else
		{
			holder =(MyViewHolder) row.getTag();
		}

			//s_name =students.get(position).getName();
			//s_reg =students.get(position).getReg();
			
		
		//holder.setNameRegAttend(s_name,s_reg,s_attend );
		holder.setPos(position);
		
		return row;
	}

}
