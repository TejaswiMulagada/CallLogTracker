package com.call;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class OutGoingCallActivity extends Activity{
	SQLiteDatabase mydb=null;
	String TableName="AndroidLogCallHistory";
	int i=0;int count;View row;
	Cursor c2;
	ArrayList <String> l1=new ArrayList<String>();
	ArrayList <String> l2=new ArrayList<String>();
	ArrayList <String> ctype=new ArrayList<String>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactsresult);
        try
		{
			mydb=openOrCreateDatabase("DatabaseName5", MODE_PRIVATE,null);
			String call="outgoing call";
			c2=mydb.rawQuery("SELECT CallNumber,CallType,MAX(Time),COUNT(CallNumber),CallName FROM "+TableName+" WHERE CallType='"+call+"' GROUP BY CallNumber,CallType ORDER BY MAX(Time) DESC " , null);
	        if(c2.moveToFirst())
	        {
	        	do{
	        	
	        		long created = c2.getLong(2);
	            	Date date = new Date(created);
	            	String date2[]=date.toString().split(" ");
	            	String i1=Integer.toString(date.getDate());
	        		String month=date2[1];
	        		String calldate=i1+" "+month;
	        		String dateString = DateFormat.getDateTimeInstance().format(date);
	        		String[] dat=dateString.split(" ");
	        		String dat2=dat[3];
	        		String[] tim=dat2.split(":");
	        		String calltime="";
	        		if(dat.length==5)
	        		{
	        			calltime=tim[0]+": "+tim[1]+" "+dat[4];	
	        		}else
	        		{
	        			if(Integer.parseInt(tim[0])>=12)
	        			{
	        				if(Integer.parseInt(tim[0])==12)
	        				{
	        					calltime=Integer.parseInt(tim[0])+": "+tim[1]+" "+"PM";	
	        				}else
	        				{
	        				
	        				calltime=Integer.parseInt(tim[0])-12+": "+tim[1]+" "+"PM";
	        				}
	        			}else
	        			{
	        				calltime=tim[0]+": "+tim[1]+" "+"AM";
	        			}
	        			
	        			
	        		}
	            	String name=c2.getString(4);
	            	String no=c2.getString(0)+" ("+c2.getString(3)+")";
	            	ctype.add(c2.getString(1));
	            	l2.add(c2.getString(0));
	        	if(name.equals("null"))
	        	{
	        		l1.add(no+"\n"+calldate+"    "+calltime);
	        	}
	        	else
	        	{
	        		l1.add(c2.getString(4)+" ("+c2.getString(3)+")"+"  \n"+calldate+"    "+calltime);
	        	}
	        }while(c2.moveToNext());
	        	
	    		ListView myList = (ListView)findViewById(R.id.myList);
	    		myList.setAdapter(new MyCustomAdapter());
	    		myList.setCacheColorHint(0);
	    		myList.setOnItemClickListener(new OnItemClickListener()
	    		{

					
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						String s1=l2.get(arg2);
						Intent it=new Intent(OutGoingCallActivity.this,listofcalls.class);
						it.putExtra("no", s1);
						it.putExtra("type",ctype.get(arg2));
						startActivity(it);
					}
	    			
	    		});
	        }
	        
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    }finally{
	    	//c2.close();
        	if (mydb != null)
        	    mydb.close();
        }
    }
	class MyCustomAdapter extends BaseAdapter {
			
			public int getCount() {
				return l1.size();
				
			}

			public String getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			public View getView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater = getLayoutInflater();
				
				if(ctype.get(position).equals("outgoing call"))
				{
					row = inflater.inflate(R.layout.outgoing, parent, false);
					TextView textLabel = (TextView) row.findViewById(R.id.text);
					
					textLabel.setText(l1.get(position));
				}
				
				return (row);
			}
		}

        
    }	


