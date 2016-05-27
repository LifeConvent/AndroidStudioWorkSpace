package com.ex.homework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SMSReceiveActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smsreceive);
		
		TextView senderText  = (TextView)findViewById(R.id.sentTelText);
		TextView senderDate  = (TextView)findViewById(R.id.sentDateText);
		TextView contentText = (TextView)findViewById(R.id.sentContentText);
		
		Intent intent = getIntent();
		
		Bundle bundle = intent.getExtras();
		
		String sender   = (String) bundle.get("Sender");
		String content  = (String) bundle.get("Content");
		String sendTime = (String) bundle.get("SendTime");
		
		senderText.setText(sender);
		senderDate.setText(sendTime);
		contentText.setText(content);
	}

}
