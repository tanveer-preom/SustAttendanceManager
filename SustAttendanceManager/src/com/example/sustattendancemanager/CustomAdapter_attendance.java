package com.example.sustattendancemanager;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class CustomAdapter_attendance extends ArrayAdapter 
{
	private Context context;
	private List<NameRegAttend> students;
	private String s_name,s_reg,s_attend,username,password;
	private UpdateAttendanceListener attendanceWindow;
	private class MyViewHolder implements OnClickListener
	{
		private int pos;
		private String s_name,s_reg;
		private TextView name,regNumber,present,absent;
		MyViewHolder(View v)
		{
			name  = (TextView) v.findViewById(R.id.name);
			regNumber = (TextView) v.findViewById(R.id.reg_no);
			present = (TextView) v.findViewById(R.id.attendance_tick);
			absent =(TextView) v.findViewById(R.id.absence_tick);
			present.setOnClickListener(this);
			absent.setOnClickListener(this);
			//present.setBackgroundResource(R.drawable.btn_selector);
			
		}
		public void setPos(int ps)
		{
			pos=ps;
		}
		public void setNameRegAttend(String passedname,String passedreg,String passedAttend)
		{
			//Log.i("size", passedname+" "+passedreg);
			name.setText(passedname);
			regNumber.setText(passedreg);
			if(passedAttend.compareTo("null")==0)
			{
				present.setBackgroundResource(R.drawable.unticked);
				absent.setBackgroundResource(R.drawable.uncrossed);
				
			}
			else if(passedAttend.compareTo("0")==0)
			{
				present.setBackgroundResource(R.drawable.unticked);
				absent.setBackgroundResource(R.drawable.crossed);
			}
			else
			{
				present.setBackgroundResource(R.drawable.ticked);
				absent.setBackgroundResource(R.drawable.uncrossed);
			}
			
		}
		public int getPosition()
		{
			return pos;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			/*tick or abs clicked*/
			if(v.getId()==present.getId())
			{
				present.setBackgroundResource(R.drawable.ticked);
				absent.setBackgroundResource(R.drawable.uncrossed);
				students.get(pos).setAttendance(attendanceWindow.attendanceNumber(true));
				new UpdateAttendanceThread(attendanceWindow,pos,true).start();
				
				
			}
			if(v.getId()==absent.getId())
			{
				absent.setBackgroundResource(R.drawable.crossed);
				present.setBackgroundResource(R.drawable.unticked);
				students.get(pos).setAttendance(attendanceWindow.attendanceNumber(false));
				new UpdateAttendanceThread(attendanceWindow,pos,false).start();
			}
			
		}
		
	}
	
	public CustomAdapter_attendance(UpdateAttendanceListener listener,List<NameRegAttend> students,String[] arr,String username,String password) {
		
		super(listener.classContext(), R.layout.single_row,R.id.reg_no,arr);
		this.context =listener.classContext();
		this.attendanceWindow =listener;
		this.students=students;
		this.username=username;
		this.password =password;
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
			row= inflater.inflate(R.layout.single_row, parent,false);
			holder =new MyViewHolder(row);
			row.setTag(holder);
		}
		else
		{
			holder =(MyViewHolder) row.getTag();
		}

			s_name =students.get(position).getName();
			s_reg =students.get(position).getReg();
			s_attend =students.get(position).getAttend();
		
		holder.setNameRegAttend(s_name,s_reg,s_attend );
		holder.setPos(position);
		
		return row;
	}
	
	
	
}
