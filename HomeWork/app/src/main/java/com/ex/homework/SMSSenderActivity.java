package com.ex.homework;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SMSSenderActivity extends Activity {

	private Button mButton1;
	private EditText mEditText1;
	private EditText mEditText2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smssender);

	    mEditText1 = (EditText) findViewById(R.id.myEditText1);
	    mEditText2 = (EditText) findViewById(R.id.myEditText2);
	    mButton1 = (Button) findViewById(R.id.myButton1);

	    mEditText1.setText("������绰����");
	    mEditText2.setText("�������������!!");

	    mEditText1.setOnClickListener(new EditText.OnClickListener()
	    {
	      public void onClick(View v)
	      {
	        mEditText1.setText("");
	      }
	    });

	    mEditText2.setOnClickListener(new EditText.OnClickListener()
	    {
	      public void onClick(View v)
	      {
	        mEditText2.setText("");
	      }
	    });

	    mButton1.setOnClickListener(new Button.OnClickListener()
	    {

	      @Override
	      public void onClick(View v)
	      {
	        String strDestAddress = mEditText1.getText().toString();

	        String strMessage = mEditText2.getText().toString();

	        SmsManager smsManager = SmsManager.getDefault();
	        
	        // TODO Auto-generated method stub
	        if (isPhoneNumberValid(strDestAddress) == true
	            && iswithin70(strMessage) == true)
	        {
	          try
	          {
	            PendingIntent mPI = PendingIntent.getBroadcast(SMSSenderActivity.this, 0,
	                new Intent(), 0);
	            
	            smsManager.sendTextMessage(strDestAddress, null, strMessage, mPI,
	                null);
	          } catch (Exception e)
	          {
	            e.printStackTrace();
	          }
	          
	          Toast.makeText(SMSSenderActivity.this, "�ͳ��ɹ�!!", Toast.LENGTH_SHORT).show();
	          
	          mEditText1.setText("");
	          mEditText2.setText("");
	        }
	        else
	        {
	          if (isPhoneNumberValid(strDestAddress) == false)
	          {
	            if (iswithin70(strMessage) == false)
	            {
	              Toast.makeText(SMSSenderActivity.this, "�绰�����ʽ����+�������ݳ���70��,����!!",
	                  Toast.LENGTH_SHORT).show();
	            } 
	            else
	            {
	              Toast
	                  .makeText(SMSSenderActivity.this, "�绰�����ʽ����,����!!", Toast.LENGTH_SHORT)
	                  .show();
	            }
	          }
	          else if (iswithin70(strMessage) == false)
	          {
	            Toast.makeText(SMSSenderActivity.this, "�������ݳ���70��,��ɾ����������!!",
	                Toast.LENGTH_SHORT).show();
	          }
	        }
	      }
	    });
	  }

	  public static boolean isPhoneNumberValid(String phoneNumber)
	  {
	    boolean isValid = false;

	    String expression = "\\d{11}";

	    String expression2 = "\\d{8}";	    
	    
	    CharSequence inputStr = phoneNumber;
	    Pattern pattern = Pattern.compile(expression);
	    Matcher matcher = pattern.matcher(inputStr);
	    Pattern pattern2 = Pattern.compile(expression2);
	    Matcher matcher2 = pattern2.matcher(inputStr);
	    
	    if (matcher.matches() || matcher2.matches())
	    {
	      isValid = true;
	    }
	    return isValid;
	  }

	  public static boolean iswithin70(String text)
	  {
		  if (text.length() <= 70)
			  return true;
		  else      return false;		  
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.smssender, menu);
		return true;
	}

}
