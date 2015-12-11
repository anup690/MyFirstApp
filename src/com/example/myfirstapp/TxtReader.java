package com.example.myfirstapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.os.Environment;


public final class TxtReader {

	private BufferedReader bReader = null;
	private String[] datavalue = null;
	private String[] student = null;
	private String[] emailid = null;
	  
	public TxtReader(String path) {
		// TODO Auto-generated constructor stub
		
		try {
			bReader = new BufferedReader(
			             new FileReader(sanitizePath(path)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		read();
	}
	
	private void read(){
		String line;
		student = new String[10];
		emailid = new String[10];

        /**
         * Looping the read block until all lines in the file are read.
         */
        try {
			for (int i=0;(line = bReader.readLine()) != null;i++) {
				
			     /**
			      * Splitting the content of tabbed separated line
			      */
			     datavalue = line.split("\t",-1);
			     student[i] = datavalue[0];
			     emailid[i] = datavalue[1];
			}
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();				
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
	public String[] getStudent(){
		return student;
	}

	public String[] getEmail(){
		return emailid;
	}
}
