package com.call;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MessageSending extends Activity{
	String name=null,no=null,body="",phno=null;
	EditText msg=null,pno=null;
	Bundle extras=null;
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.messagesending);
		extras=getIntent().getExtras();
		name=extras.getString("name");
		no=extras.getString("phone");
		pno=(EditText)findViewById(R.id.txtPhoneNo);
		 msg=(EditText)findViewById(R.id.txtMessage);
		 
		if(name!=null)
		{
			pno.setText(name);
			
		}else
		{
			pno.setText(no);
			
		}
		Button send=(Button)findViewById(R.id.btnSendSMS);
		send.setOnClickListener(new OnClickListener()
		{

			
			public void onClick(View v) {
				
				String bod=msg.getText().toString();
				body=bod;
				
				//System.out.println("body is "+body);
				if (body==null || body=="" || body.equals("") || body.equals(null))
			{
					Toast.makeText(getBaseContext(), "Please enter message",Toast.LENGTH_SHORT).show();
					 
			}
			else
			{
				no=extras.getString("phone");
				System.out.println("phone no2 is"+no);
				sendSMS(no, body); 
			}
				
				
			}
			
		});
		
		
		
	}
	 private void sendSMS(String phoneNumber, String message)
	    {        
	        String SENT = "SMS_SENT";
	        String DELIVERED = "SMS_DELIVERED";
	 
	        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
	            new Intent(SENT), 0);
	 
	        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
	            new Intent(DELIVERED), 0);
	        SmsManager sms = SmsManager.getDefault();
	        System.out.println("phone no is"+phoneNumber);
	        sms.sendTextMessage(phoneNumber, null, body, sentPI, deliveredPI);  
	 
	        //---when the SMS has been sent---
	        registerReceiver(new BroadcastReceiver(){
	            
				public void onReceive(Context context, Intent intent) {
					switch (getResultCode())
	                {
	                    case Activity.RESULT_OK:
	                    	msg.setText("");
	                        Toast.makeText(getBaseContext(), "SMS sent", 
	                                Toast.LENGTH_SHORT).show();
	                        break;
	                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
	                        Toast.makeText(getBaseContext(), "Generic failure", 
	                                Toast.LENGTH_SHORT).show();
	                        break;
	                    case SmsManager.RESULT_ERROR_NO_SERVICE:
	                        Toast.makeText(getBaseContext(), "No service", 
	                                Toast.LENGTH_SHORT).show();
	                        break;
	                    case SmsManager.RESULT_ERROR_NULL_PDU:
	                        Toast.makeText(getBaseContext(), "Null PDU", 
	                                Toast.LENGTH_SHORT).show();
	                        break;
	                    case SmsManager.RESULT_ERROR_RADIO_OFF:
	                        Toast.makeText(getBaseContext(), "Radio off", 
	                                Toast.LENGTH_SHORT).show();
	                        break;
	                }
					
				}
	        }, new IntentFilter(SENT));
	 
	        //---when the SMS has been delivered---
	        registerReceiver(new BroadcastReceiver(){
	            @Override
	            public void onReceive(Context arg0, Intent arg1) {
	                switch (getResultCode())
	                {
	                    case Activity.RESULT_OK:
	                        Toast.makeText(getBaseContext(), "SMS delivered", 
	                                Toast.LENGTH_SHORT).show();
	                        break;
	                    case Activity.RESULT_CANCELED:
	                        Toast.makeText(getBaseContext(), "SMS not delivered", 
	                                Toast.LENGTH_SHORT).show();
	                        break;                        
	                }
	            }
	        }, new IntentFilter(DELIVERED));        
	 
	             
	    }

}
