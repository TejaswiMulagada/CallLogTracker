package com.call;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class listofcalls extends Activity{
	public static AlertDialog.Builder alertbox=null;
	SQLiteDatabase mydb=null;
	String TableName="AndroidLogCallHistory";
	String sec=null,min=null;
	ArrayList <String> l1=new ArrayList<String>();
	ArrayList <String> l2=new ArrayList<String>();
	ArrayList <String> l3=new ArrayList<String>();
	ArrayList <String> l4=new ArrayList<String>();
	View row;
	String phone=null,nooftimes=null;
	Cursor c=null,c1=null,c2=null;
	String pno=null,type=null;
	
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.selectedcall);
		Bundle extras=getIntent().getExtras();
		pno=extras.getString("no");
		type=extras.getString("type");
		nooftimes=extras.getString("nooftimes");
		try
		{
		mydb=openOrCreateDatabase("DatabaseName5", MODE_PRIVATE,null);
		mydb.execSQL("CREATE TABLE IF NOT EXISTS "
				+TableName
				+" (CallName varchar,CallNumber varchar,CallDuration integer,CallType varchar,Time long);");
		c2=mydb.rawQuery("SELECT CallType , COUNT( CallType ) as Count FROM "+TableName+" where CallNumber='"+pno+"' GROUP BY CallType", null);
		if(c2.moveToFirst())
		{
			do
			{
				l4.add(c2.getString(0)+"~"+c2.getString(1));
			}while(c2.moveToNext());
		}
		c1=mydb.rawQuery("SELECT CallType , COUNT( CallType ) as Count FROM "+TableName+" GROUP BY CallType", null);
		if(c1.moveToFirst())
		{
			do
			{
				l3.add(c1.getString(0)+"~"+c1.getString(1));
				
			}while(c1.moveToNext());
		}
		
		
		c=mydb.rawQuery("SELECT CallDuration,Time,CallNumber From "+TableName+" WHERE CallType='"+type+"' AND CallNumber='"+pno+"' ORDER BY Time DESC", null);
		if(c.moveToFirst())
		{
			do{
				
			
			phone=c.getString(2);
			int time1=c.getInt(0);
			String hours="00";
			sec=new Integer(time1%60).toString();
			int sec1=time1%60;
			if(sec1<10)
			{
				sec="0"+new Integer(sec1).toString();
			}
			min=new Integer(time1/60).toString();
			int mins=time1/60;
			if(mins<10)
			{
				min="0"+new Integer(mins).toString();
			}
			if(mins>60)
			{	
				int m=mins/60;
				hours=new Integer(m).toString();
				 mins=mins%60;
			}
			String time=hours+":"+min+":"+sec;
			long created = c.getLong(1);
			String created2=c.getString(1);
			l2.add(created2);
        	Date date = new Date(created);
    		String i1=Integer.toString(date.getDate());
    		int y1=date.getYear()-100;
    		int y=2000+y1;
    		String date2[]=date.toString().split(" ");
        	String month=date2[1];
    		String calldate=i1+"-"+month+"-"+y;
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
        	l1.add(calldate+"  "+calltime+"\n"+time);
			}while(c.moveToNext());
			
			
			ImageView msg=(ImageView)findViewById(R.id.msg);
			
			msg.setOnClickListener(new OnClickListener()
			{

				public void onClick(View v) {
					Intent it=new Intent(listofcalls.this,MessageSending.class);
					it.putExtra("phone",phone);
					startActivity(it);
				
				}
				
			});
    		ImageView ib=(ImageView)findViewById(R.id.call);
    		//ImageButton ib1=(ImageButton)findViewById(R.id.msg);
    		ib.setOnClickListener(new OnClickListener()
    		{

				
				public void onClick(View v) {
					Intent it=new Intent(listofcalls.this,Call.class);
					it.putExtra("phone",phone);
					startActivity(it);
					
				}
    			
    		});
    		ListView myList = (ListView)findViewById(R.id.ListView01);
    		myList.setAdapter(new MyCustomAdapter());
    		myList.setCacheColorHint(0);
    		myList.setOnItemLongClickListener(new OnItemLongClickListener()
    		{

				
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					final int s=Integer.parseInt(arg0.getItemAtPosition(arg2).toString());
					alertbox = new AlertDialog.Builder(listofcalls.this);
				    alertbox.setTitle("Delete This History Only");
			        alertbox.setIcon(R.drawable.call);
			        alertbox.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface arg0, int arg1) {
			        mydb.execSQL("DELETE FROM "+TableName+" WHERE CallType='"+type+"' AND Time='"+l2.get(s)+"' AND CallNumber='"+phone+"'");
			        Toast.makeText(getApplicationContext(),"Deleted Successfully", Toast.LENGTH_SHORT).show();
			        Intent it=new Intent(listofcalls.this,tabactivity.class);	
			           it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			           startActivity(it);
			            }
			        });
			        alertbox.show();
			        return false;
				}
    			
    		});
    		
    			
    		
    		
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			if(mydb!=null)
			{
				c.close();
				mydb.close();	
			}
			
		}
		
		
	}
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.statistics:     
            	Intent it2=new Intent(listofcalls.this,chart.class);
            	it2.putStringArrayListExtra("values", l3);
            	it2.putExtra("nooftimes", nooftimes);
            	it2.putStringArrayListExtra("noof", l4);
            
            	startActivity(it2);
            	
                                
            break;

            
        }
        return true;
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
			
			
				row = inflater.inflate(R.layout.data, parent, false);
				TextView textLabel = (TextView) row.findViewById(R.id.text);
				
				textLabel.setText(l1.get(position));
				
			

			return (row);
		}
	}


}
