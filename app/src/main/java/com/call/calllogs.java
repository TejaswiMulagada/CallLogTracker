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
public class calllogs extends Activity {
    SQLiteDatabase mydb=null;
	String TableName="AndroidLogCallHistory";
	Cursor c1,c2,c3;
	String Name="",Phone=null,Phone1=null;
	String duration=null;
	String type=null;
	int count;View row;
	int i=0;
	ArrayList <String> l1=new ArrayList<String>();
	ArrayList <String> l2=new ArrayList<String>();
	ArrayList <String> ctype=new ArrayList<String>();
	ArrayList <String> nooftimes=new ArrayList<String>();
	
	    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactsresult);
        try
		{
			mydb=openOrCreateDatabase("DatabaseName5", MODE_PRIVATE,null);
			mydb.execSQL("CREATE TABLE IF NOT EXISTS "
					+TableName
					+" (CallName varchar,CallNumber varchar,CallDuration integer,CallType varchar,Time long);");
	 c1=mydb.rawQuery("SELECT * FROM "+TableName,null);
			boolean b = c1.moveToFirst();
			if(!b)
        	{
			
		Cursor c=getContentResolver().query
        (android.provider.CallLog.Calls.CONTENT_URI, 
        		null, null,null,
        		android.provider.CallLog.Calls.DATE + " DESC ");
        startManagingCursor(c);
        int name=c.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME);
        int number=c.getColumnIndex(android.provider.CallLog.Calls.NUMBER);
        int datecolumn=c.getColumnIndex(android.provider.CallLog.Calls.DATE);
        int typeofcall=c.getColumnIndex(android.provider.CallLog.Calls.TYPE);
        int time=c.getColumnIndex(android.provider.CallLog.Calls.DURATION);
        if(c.moveToFirst())
        {
        	do
        	{	String callname=c.getString(name);
        		
        		String callno=c.getString(number);
        		long created = c.getLong(datecolumn);
        		
        		int duration=c.getInt(time);
        		int type=c.getInt(typeofcall);
        		
        		
        		String calltype=null;
        		switch(type)
        		{
        		case android.provider.CallLog.Calls.MISSED_TYPE:
        			calltype="missed call";
        		break;
        		case android.provider.CallLog.Calls.INCOMING_TYPE:
        			calltype="incoming call";
        		break;
        		case android.provider.CallLog.Calls.OUTGOING_TYPE:
        			calltype="outgoing call";
        		break;
        		}
mydb.execSQL("INSERT INTO "+TableName+"(CallName,CallNumber,CallDuration,CallType,Time)"+"VALUES('"+callname+"','"+callno+"',"+duration+",'"+calltype+"',"+created+");");
        		}while(c.moveToNext());
        }	
        
        	
  }
			else
        {
				Cursor c3=mydb.rawQuery("SELECT MAX(Time) FROM "+TableName, null);
				
			
				if(c3.moveToFirst())
				{
					long time2=c3.getLong(0);
					c3.close();
					//System.out.println("max time is"+time2);
					Cursor c=getContentResolver().query
			        (android.provider.CallLog.Calls.CONTENT_URI, 
			        		null, null,null,
			        		android.provider.CallLog.Calls.DATE +" DESC ");
			        startManagingCursor(c);
			        int name=c.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME);
			        int number=c.getColumnIndex(android.provider.CallLog.Calls.NUMBER);
			        int datecolumn=c.getColumnIndex(android.provider.CallLog.Calls.DATE);
			        int typeofcall=c.getColumnIndex(android.provider.CallLog.Calls.TYPE);
			        int time=c.getColumnIndex(android.provider.CallLog.Calls.DURATION);
			        if(c.moveToFirst())
			        {
			        	do
			        	{	
			        		long created2 = c.getLong(datecolumn);
			        		if(created2>time2){
			        			String callname=c.getString(name);
				        		
				        		String callno=c.getString(number);
				        		//System.out.println("n0 is "+callno);
				        		long created = c.getLong(datecolumn);
				        	//	System.out.println("value is "+created);
				        		int duration=c.getInt(time);
				        		int type=c.getInt(typeofcall);
				        		String calltype=null;
				        		switch(type)
				        		{
				        		case android.provider.CallLog.Calls.MISSED_TYPE:
				        			calltype="missed call";
				        		break;
				        		case android.provider.CallLog.Calls.INCOMING_TYPE:
				        			calltype="incoming call";
				        		break;
				        		case android.provider.CallLog.Calls.OUTGOING_TYPE:
				        			calltype="outgoing call";
				        		break;
				        		}
	mydb.execSQL("INSERT INTO "+TableName+"(CallName,CallNumber,CallDuration,CallType,Time)"+"VALUES('"+callname+"','"+callno+"',"+duration+",'"+calltype+"',"+created+");");
				        		
			        			
			        		}
			        		else
			        		{
			        			
			        			break;
			        		}
			        		}while(c.moveToNext());
			        }	 
					
				}
        	
        }
        
c2=mydb.rawQuery("SELECT CallNumber,CallType,MAX(Time),COUNT(CallNumber),CallName FROM "+TableName+" GROUP BY CallNumber,CallType ORDER BY MAX(Time) DESC", null);
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
        	nooftimes.add(c2.getString(3));
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
					int s=Integer.parseInt(arg0.getItemAtPosition(arg2).toString());
					String s1=l2.get(s);
					
					Intent it=new Intent(calllogs.this,listofcalls.class);
					it.putExtra("no", s1);
					it.putExtra("nooftimes", nooftimes.get(arg2));
					it.putExtra("type",ctype.get(s));
					startActivity(it);
					
				}
    			
    		});
        }else
        {
        	TextView tv=new TextView(this);
        	tv.setText("No Call Logs Found");
        	tv.setTextSize(20);
        }
        
        
        
        
		}//try close-----------------------------------------
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        finally{
        	
        	if (mydb != null)
        	    mydb.close();
        }
        
        
        
    }
	    
		
class MyCustomAdapter extends BaseAdapter {
		
		public int getCount() {
			return l1.size();
			
		}

		public Object getItem(int position) {
            return position;
      }

      public long getItemId(int position) {
            return position;
      }


		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			
			if(ctype.get(position).equals("missed call"))
			{
				row = inflater.inflate(R.layout.missedcall, parent, false);
				TextView textLabel = (TextView) row.findViewById(R.id.text);
				
				textLabel.setText(l1.get(position));
				
			}
			else if(ctype.get(position).equals("outgoing call"))
			{
				row = inflater.inflate(R.layout.outgoing, parent, false);
				TextView textLabel = (TextView) row.findViewById(R.id.text);
				
				textLabel.setText(l1.get(position));
			}
			else if(ctype.get(position).equals("incoming call"))
			{
				row = inflater.inflate(R.layout.incoming, parent, false);
				TextView textLabel = (TextView) row.findViewById(R.id.text);
				
				textLabel.setText(l1.get(position));
			}

			return (row);
		}
	}

}