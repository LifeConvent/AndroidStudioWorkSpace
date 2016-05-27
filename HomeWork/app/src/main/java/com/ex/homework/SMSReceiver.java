package com.ex.homework;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub  
		String sender   = null; 
		String content  = null; 
		String sendTime = null;
		Toast.makeText(context, "���յ��¶���!!", Toast.LENGTH_SHORT).show();

		Bundle bundle = intent.getExtras();  
		Object[] pdus = (Object[]) bundle.get("pdus");  
		          
		if(pdus != null && pdus.length>0){  
			
			//�����˶���   
			SmsMessage[] smsMessages = new SmsMessage[pdus.length];  

			for(int i= 0;i<smsMessages.length;i++){
				byte[] pdu = (byte[]) pdus[i];  
				smsMessages[i]  = SmsMessage.createFromPdu(pdu);  
			}  
		             
			for(SmsMessage msg:smsMessages){  

				//ת�����ڸ�ʽ   
				Date date            = new Date(msg.getTimestampMillis());  
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				sender   = msg.getOriginatingAddress();
				content  = msg.getMessageBody();  
				sendTime = sdf.format(date);
			}
		}
		
		Intent receiveIntent = new Intent(context, SMSReceiveActivity.class);
		receiveIntent.putExtra("Sender",   sender);
		receiveIntent.putExtra("Content",  content);
		receiveIntent.putExtra("SendTime", sendTime);		
		receiveIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    context.startActivity(receiveIntent); 
	}

}
