package com.example.myfirstapp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class Save2SambaService extends IntentService {
	public String ipG = null;
	public String pathG = null;
//	private Toast msg;
	public static Activity MainActivity;
	
	private static final String EMAIL_FILE = "email.txt";
	
	private static String[] student = null;
	
	TxtReader tr = null;
	
//	private static final String TAG = "discovery";

	public Save2SambaService() {
		super("save2samba");
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        String dataString = workIntent.getDataString();
        //System.out.println(dataString);
        String action = workIntent.getAction();
        //String newline = "\n";
         
        
        // Do work here, based on the contents of dataString
        
        try {
        	jcifs.Config.registerSmbURLHandler();
            // My Windows shares doesn't require any login/password
            
        	//nw disc
        	//ArrayList<String> hosts = scanSubNet("192.168.1.");
        	
            SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
        	String ip = prefs.getString(SettingsActivity.KEY_PREF_IP.toString(), "");
        	ipG=ip;
            String path = prefs.getString(SettingsActivity.KEY_PREF_PATH_FILENAME.toString(),"");                       
            pathG=path;
            String uname = prefs.getString(SettingsActivity.KEY_PREF_USERNAME.toString(),null);
            String pass = prefs.getString(SettingsActivity.KEY_PREF_PASSWORD.toString(),null);
            boolean flag = prefs.getBoolean(SettingsActivity.KEY_PREF_AUTHENTICATION, false);
            //String lang = prefs.getString(SettingsActivity.KEY_PREF_LANG_LIST.toString(),null);
            System.out.println(flag);
            
            
            String name= uname;//my windows username
            String password=pass;//my windows password
            
            // sSambaFolder contains a path like MYPC/E/SharedFolderName/
            String url = "smb://" + ip + "/" + path;
//            String lurl = "smb://" + ip + "/d/new.txt";
            String aurl = "smb://" + ip + "/d/answer.txt";
            String rurl = "smb://" + ip + "/d/result.txt";
            String murl = "smb://" + ip + "/d/menu.txt";
            String jpgurl = "smb://" + ip + "/d/clips/";
//            String durl =  "smb://" + ip + "/d/";
//            String rdurl = "smb://" + ip + "/d/ready.txt";
            
//            SmbFile[] files;
            
            SmbFile file = null;
//            SmbFile rdFile = null;
//            SmbFile dir =  null;
            //lang file new.txt
            //SmbFile lfile = null;
            
            
            SmbFileOutputStream out;//lout;
            
            NtlmPasswordAuthentication auth;
            try {
            	if(flag){
            		 auth = new NtlmPasswordAuthentication(null,name,password);
            	}
            	else{            		
            	
                // assume ANONYMOUS is my case but there is no description of this in JCIFS API
            		auth = new NtlmPasswordAuthentication(null,null,null); //new NtlmPasswordAuthentication(null,name, password); 
            	}
            	System.out.println(auth);
            	
//            	dir = new SmbFile(durl, auth);
//            	boolean readyFlag = false;
//            	files = dir.listFiles();
//            	for(int i=0;i<files.length;i++){
//            		String temp = files[i].getName();
//            		if( temp == "ready.txt"){
//            			readyFlag = true;
//            			
//            		}
//            		android.util.Log.i("smbfiles",temp);
//            	}
            	
 
            	
            	if(action =="state"){
        			file = new SmbFile(url, auth);
        			//rdFile = new SmbFile(rdurl, auth);           			
        		            		
            	}
            	else if(action == "answer"){
            		file = new SmbFile(aurl, auth);	//o/p to answer.txt to translate to specified language and write to state.text 
            		out = new SmbFileOutputStream(file,true);
            		dataString = "#" + dataString ;
            		out.write(dataString.getBytes());
            		out.flush();
            		out.close();
            	}
            	else if(action == "menu"){
            		file = new SmbFile(murl, auth);
            	}
            	else if(action == "result"){
            		file = new SmbFile(rurl,auth);
            		out = new SmbFileOutputStream(file,true);
            		
            		//dataString += String.format("%n");
                    out.write(dataString.getBytes());
//                    out.write(newline.getBytes());
//                    out.write(System.getProperty("line.separator").getBytes());
                    out.flush();
                    out.close();
                    return;
                    
            	}
            	else if(action == "email"){
            		initStuDetails();
            		for(String s : student){
            			
            			file = new SmbFile(jpgurl+s+".jpg",auth);
            			if(!file.exists())
            				continue;
                    	writeFile(file,s);
            		}
            		return;
            	}
            	
//            	else if(action == "lang"){
//            		file = new SmbFile(lurl,auth);
//            		out = new SmbFileOutputStream(file);         		
//            		
//                    out.write(dataString.getBytes());
//                    out.flush();
//                    out.close();
//                    return;
//            	}            	
            	
                
            	//language read new.txt
                //lfile = new SmbFile(lurl,auth);
                
                android.util.Log.i("TestApp",url);
                // output is like smb://mypc/e/sharedfoldername/file.txt;
                
                //output to new.txt to read language:-
                //lout = new SmbFileOutputStream(lfile);                
                //lout.write(lang.getBytes());
                if((!file.exists())){	//&& rdFile.exists()
                	//rdFile.delete();
                	out = new SmbFileOutputStream(file);
                    out.write(dataString.getBytes());
                    out.flush();
                    out.close();
                    return;
                    
                }
                
               	
            	
            } catch (Exception e) {
                e.printStackTrace();
                android.util.Log.i("pc", "Authenication problem");
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                return;
            }
                
                
//                Toast.makeText(MainActivity/*getApplicationContext()*/, "Saved to "+ipG+"/"+pathG, Toast.LENGTH_SHORT).show();
//                if(null == msg)
//                {
//                msg = Toast.makeText(this, "Saved to "+ipG+"/"+pathG, Toast.LENGTH_SHORT);
//               // msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
//                msg.show();
//
//                //handels the stupid queueing toast messages
//                new Handler().postDelayed(new Runnable()
//                {
//                      @Override
//					public void run()
//                      {
//                          msg = null;
//
//                      }
//                }, 2000);
//
//                }
            
            /*try {
            	   for (SmbFile f : file.listFiles()) {
            	    System.out.println(f.getName());
            	    //shares = dir.list();
            	    }
            	  } catch (SmbException e) {
            	   // TODO Auto-generated catch block
            	   e.printStackTrace();
            	   android.util.Log.i("MyHome", "Directory listing problem");
            	  }*/

            //return true;
        } catch (Exception e) {
        	Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
           // return false;
        }
		return;

    }
	
	private void writeFile(SmbFile remoteFile,String fname) throws IOException{
		OutputStream os = null;
		try {
			os = new FileOutputStream(sanitizePath("clips/"+fname+".jpg"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SmbFileInputStream is = null;
		try {
			is = new SmbFileInputStream(remoteFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*int ch;
		while ((ch = is.read()) != -1) {
		    os.write(ch);
		}
		 */
		
		byte[] b = new byte[8192];
        int n;
        while(( n = is.read( b )) > 0 ) {
            os.write( b, 0, n );            
        }
		os.close();
		is.close();
		return;
	}
	
	private void initStuDetails(){
		  tr = new TxtReader(EMAIL_FILE);
			
			student = tr.getStudent();
	  }
	
	private static String sanitizePath(String path) {
	    if (!path.startsWith("/")) {
	      path = "/" + path;
	    }
	    if (!path.contains(".")) {
	      path += ".jpg";
	    }
	    return Environment.getExternalStorageDirectory().getAbsolutePath() + path;
	  }
//	private ArrayList<String> scanSubNet(String subnet){
//	    ArrayList<String> hosts = new ArrayList<String>();
//
//	    InetAddress inetAddress = null;
//	    for(int i=1; i<10; i++){
//	        Log.d(TAG, "Trying: " + subnet + String.valueOf(i));
//	        try {
//	            inetAddress = InetAddress.getByName(subnet + String.valueOf(i));
//	            if(inetAddress.isReachable(1000)){
//	                hosts.add(inetAddress.getHostName());
//	                Log.d(TAG, inetAddress.getHostName());
//	            }
//	        } catch (UnknownHostException e) {
//	            e.printStackTrace();
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	    }
//
//	    return hosts;
//	}
	
	
}
