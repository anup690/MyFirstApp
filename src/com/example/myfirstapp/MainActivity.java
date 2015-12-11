package com.example.myfirstapp;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	ThreadPolicy tp = ThreadPolicy.LAX;
	public final static String EXTRA_MESSAGE = "com.myFirstApp.MESSAGE";
	public final static String ACTION_ANSWER = "answer";
	public final static String ACTION_RESULT = "result";
	private static final String ACTION_MENU = "menu";
	private static final String ACTION_SEND_EMAIL = "email";
	private static final String ACTION_STATE = "state";
	private static final String EMAIL_FILE = "email.txt";
//	private static final String ACTION_EMAIL_VIDEO = "video";
//	private static final String ACTION_EMAIL_AUDIO = "audio";
	@SuppressWarnings("unused")
	private static boolean sentFlag = false;
	private static int RECORD_COUNT = 0;
	private static String[] student = null;
	private static String[] emailid =null;
	public static boolean vflag = false;
	String path;
	AudioRecorder recorder = null;
	private MailCustom m;
	TxtReader tr =null;
	ProgressDialog progressDialog;
	//from preference
	//jcifs.Config.registerSmbURLHandler();
	
	
	
	   
    
//    String durl =  "smb://" + ip + "/d/1.jpg";
	//private Toast msg;    
	
	@SuppressLint("NewApi")
	public void save2Samba(String text) {
		StrictMode.setThreadPolicy(tp);
		try{
		Intent mServiceIntent;
		mServiceIntent = new Intent(this, Save2SambaService.class);
		mServiceIntent.setAction(ACTION_STATE);
		mServiceIntent.setData(Uri.parse(text));
		
		this.startService(mServiceIntent);
		 Toast.makeText(this.getApplication(), "  Done", Toast.LENGTH_SHORT).show();
		    }
		catch(Exception ex){
			Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	public void save2answer(String answerTxt){
		try{
			Intent intent = new Intent();  
			intent.setClass(this,Save2SambaService.class);
			intent.setAction(ACTION_ANSWER);
			intent.setData(Uri.parse(answerTxt));  
			this.startService(intent);
			Toast.makeText(this.getApplication(), "  Done", Toast.LENGTH_SHORT).show();
		}
		catch(Exception ex){
			Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	public void save2result(String resultTxt){
		try{
			Intent intent = new Intent();  
			intent.setClass(this,Save2SambaService.class);
			intent.setAction(ACTION_RESULT);
			intent.setData(Uri.parse(resultTxt));  
			this.startService(intent);
			Toast.makeText(this.getApplication(), "  Done", Toast.LENGTH_SHORT).show();
		}
		catch(Exception ex){
			Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	public void save2menu(String menuTxt){
		try{
			Intent intent = new Intent();  
			intent.setClass(this,Save2SambaService.class);
			intent.setAction(ACTION_MENU);
			intent.setData(Uri.parse(menuTxt));  
			this.startService(intent);
			Toast.makeText(this.getApplication(), "  Done", Toast.LENGTH_SHORT).show();
		}
		catch(Exception ex){
			Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	public void buttonStateToTxt(String sBody){
	    try
	    {
	        File root = new File(Environment.getExternalStorageDirectory(), "button");
	        if (!root.exists()) {
	            root.mkdirs();
	        }
	        File fptr = new File(root, "state.txt");
	        FileWriter writer = new FileWriter(fptr);
	        writer.append(sBody);
	        writer.flush();
	        writer.close();
	        Toast.makeText(this.getApplication(), "Saved", Toast.LENGTH_SHORT).show();
	        /*if(null == msg)
	        {
	        msg = Toast.makeText(this," Saved", Toast.LENGTH_SHORT);
	        //msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
	        msg.show();

	        //handels the stupid queueing toast messages
	        new Handler().postDelayed(new Runnable()
	        {
	              public void run()
	              {
	                  msg = null;

	              }
	        }, 2000);

	        }*/
	        
	    }
	    catch(IOException e)
	    {
	         e.printStackTrace();
	         //importError = e.getMessage();
	         //iError();
	    }
	   }  

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	       
	        case R.id.action_settings:
	        	Intent intent = new Intent(this,SetIP.class);
	        	startActivity(intent);
	            return true;
	        case R.id.preferences:
	        	Intent intent1 = new Intent(this,SettingsActivity.class);
	        	startActivity(intent1);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	/*public void getIP(MenuItem item){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);                 
		alert.setTitle("IP");  
		alert.setMessage("Enter Pin :");  
		alert.setMessage("Enter Path :");

		 // Set an EditText view to get user input   
		 final EditText input = new EditText(this); 
		 alert.setView(input);

		    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {  
		    public void onClick(DialogInterface dialog, int whichButton) {  
		        String value = input.getText().toString();
		       // Log.d( TAG, "Pin Value : " + value);
		        return;                  
		       }  
		     });  

		    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

		        public void onClick(DialogInterface dialog, int which) {
		            // TODO Auto-generated method stub
		            return;   
		        }
		    });
		            alert.show();
	}*/

	public void sendMessage(View view){
		try{
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		save2answer(message);
		//startActivity(intent);	
		Toast.makeText(this, "  Done", Toast.LENGTH_SHORT).show();
		}
		catch(Exception ex){
			android.util.Log.i("sendMessage",ex.toString());
		}
		
		
	}
	/*String scanList;
	public void wifiStatus(View view){
		try{
			
			IntentFilter filter = new IntentFilter();
		     filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		     Activity context= new CheckWifiActivity();
			final WifiManager wifiManager = 
		                         (WifiManager)context.getSystemService(Context.WIFI_SERVICE);;
		                          registerReceiver(new BroadcastReceiver(){
		        @Override
		        public void onReceive(Context arg0, Intent arg1) {
		            // TODO Auto-generated method stub
		            Log.d("wifi","Open Wifimanager");

		            scanList = wifiManager.getScanResults().toString();
		            Log.d("wifi","Scan:"+scanList);
		            
		            
		            
		        }           
		      },filter);        
		        wifiManager.startScan();
			
			

			Intent intent1 = new Intent(this, CheckWifiActivity.class);
			intent1.putExtra(EXTRA_MESSAGE, scanList);
			startActivity(intent1);
		}
		catch(Exception ex){
			android.util.Log.i("wifiStatusMain",ex.toString());
		}
		
	}*/
	public boolean playAnimation(View view){
		try{
//		buttonStateToTxt("1");
		save2Samba("1");
	} catch (Exception e) {
        e.printStackTrace();
        android.util.Log.i("pc", "Authenication problem");
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        return false;
    }
		return true;
				
	}
	public void nextQuestion(View view){
		try{
//		buttonStateToTxt("2");
		save2Samba("2");
		}
		catch(Exception ex){
			android.util.Log.i("nextQuestion",ex.toString());
		}
		
	}
	public void answer(View view){
		try{
//		buttonStateToTxt("3");
		save2Samba("3");
		}
		catch(Exception ex){
			android.util.Log.i("answer",ex.toString());
		}
	}
	
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.button10:
//			buttonStateToTxt("0");
			save2Samba("0");
			initStuDetails();
		break;
		case R.id.button4:
//			buttonStateToTxt("10");
			save2result("10");
		break;
		case R.id.button5:
//			buttonStateToTxt("11");
			save2result("11");
		break;
		case R.id.button6:
//			buttonStateToTxt("12");
			save2result("12");
			break;
		case R.id.button11:
//			buttonStateToTxt("13");
			save2result("13");
			break;
		case R.id.button7:
//			buttonStateToTxt("1");
			save2menu("1");
			
			break;
		case R.id.button8:
//			buttonStateToTxt("2");
			save2menu("2");
			break;
		case R.id.button9:
//			buttonStateToTxt("3");
			save2menu("3");
			break;
			
		case R.id.button12:
//			buttonStateToTxt("4");
			save2menu("4");
			break;
		case R.id.button13:
//			buttonStateToTxt("5");
			save2Samba("4");
			Button buttonRec = (Button) findViewById(R.id.button_rec);
			buttonRec.setEnabled(true);
			break;
		case R.id.button_br:			
//			buttonStateToTxt("5");
			save2Samba("5");
			break;
		case R.id.buttonEmailAudio:
			if(RECORD_COUNT == 0){
				Toast.makeText(this, " Record an Audio Clip first", Toast.LENGTH_SHORT).show();				
				return;
			}
			Thread thread = new Thread() {
	            @Override
	            public void run() {	            	
	                emailSend();
	            }
	        };
	        thread.start();
	        
			break;
		case R.id.buttonEmailVideo:
			
			Intent intent = new Intent();  
			intent.setClass(this,Save2SambaService.class);
			intent.setAction(ACTION_SEND_EMAIL);
			//intent.setData(Uri.parse());  
			this.startService(intent);
			
			vflag = true;
			
			Thread thread1 = new Thread() {
	            @Override
	            public void run() {
					emailSend();					
	            }
	        };
			thread1.start();
			Toast.makeText(this, "Email being sent.", Toast.LENGTH_LONG).show();       
//			SendEmail s = new SendEmail();
			
				            	           	
	            
//			Intent sendIntent1 = new Intent(Intent.ACTION_SEND);
//          	sendIntent1.setType("video/3gp");
//        	sendIntent1.putExtra(Intent.EXTRA_SUBJECT, "Sent from _____ Institution");
//        	sendIntent1.putExtra(Intent.EXTRA_STREAM, Uri.parse(sanitizePath("Rec/"+RECORD_COUNT+".3gp")));
//        	sendIntent1.putExtra(Intent.EXTRA_TEXT, "Video Clip of your Ward");
//        	startActivity(Intent.createChooser(sendIntent1, "Email:"));			
			break;
			
		default:
		throw new RuntimeException("Unknow button ID");
		}
	}
	
	
//	private void publishToast(){
//		if(sentFlag) { 
//		      Toast.makeText(this, "Email was sent successfully.", Toast.LENGTH_LONG).show(); 
//		    } else { 
//		      Toast.makeText(this, "Email was not sent.", Toast.LENGTH_LONG).show(); 
//		    }
//	}
	public void onToggleClicked(View view) {
	    // Is the toggle on?
	    boolean on = ((ToggleButton) view).isChecked();
	    
	    if(student == null || student[RECORD_COUNT] == null){
	    	Toast.makeText(this.getApplication(), "  No (more) entries in Student list", Toast.LENGTH_SHORT).show();	    			
	    	
	    	return;
	    }
	    
	    if (on) {
	        // Enable vibrate
	    	
	    	recorder = new AudioRecorder("Rec/"+student[RECORD_COUNT]);  //String.valueOf(++RECORD_COUNT)
	    	
	    	//path = sanitizePath("Rec/"+String.valueOf(++RECORD_COUNT));
	    	try {
				recorder.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    } else {
	        // Disable vibrate  		
	    		    	
	    	try {
				recorder.stop();
				Toast.makeText(this.getApplication(), "  "+student[RECORD_COUNT++], Toast.LENGTH_SHORT).show();
				Button buttonRec = (Button) findViewById(R.id.button_rec);
				buttonRec.setEnabled(false);
	    	} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	private static String sanitizePath(String path) {
	    if (!path.startsWith("/")) {
	      path = "/" + path;
	    }
	    if (!path.contains(".")) {
	      path += ".3gp";
	    }
	    return Environment.getExternalStorageDirectory().getAbsolutePath() + path;
	  }
		  
	  
	  private void emailSend(){
		  		  	
		  if(emailid == null || student == null){
//			  Toast.makeText(MainActivity.this, " Please START first", Toast.LENGTH_SHORT ).show();
			  return;
		  }
		  
		   for(int i=0;(emailid[i]!=null & student[i]!=null) ;i++){
				//Email Send:-
				m = new MailCustom("anoop690@gmail.com","***********");
			     String[] toArr = {emailid[i], "anoop690@gmail.com"}; 
			      m.setTo(toArr); 
			      m.setFrom("wooo@wooo.com"); 
			      m.setSubject("Sent from _____ Institution"); 
			       
			 
			      try { 
			         
			        if(vflag == true){
			        	m.setBody("video Clip of " + student[i]);
			        	m.addAttachment(sanitizePath("clips/"+ student[i] +".jpg"));
			        }
			        				        
			        else{
			        	m.setBody("Audio Clip of " + student[i]);
			        	m.addAttachment(sanitizePath("Rec/"+ student[i] +".3gp"));
			        }
			        	
			 
			        if(m.send()) { 
			          sentFlag = true; //Toast.makeText(MainActivity.this, "Email was sent successfully.", Toast.LENGTH_LONG).show(); 
			        } else { 
			          sentFlag = false; //Toast.makeText(MainActivity.this, "Email was not sent.", Toast.LENGTH_LONG).show(); 
			        } 
			      } catch(Exception e) { 
			        //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show(); 
			        Log.e("MailApp", "Could not send email", e); 
			      }          		     
				     	
				     	
			}	
					
	  }
	  
	  private void initStuDetails(){
		  tr = new TxtReader(EMAIL_FILE);
			emailid = tr.getEmail();
			student = tr.getStudent();
	  }
	 


	
		
}
