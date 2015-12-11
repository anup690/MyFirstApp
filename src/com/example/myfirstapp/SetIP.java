package com.example.myfirstapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class SetIP extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_ip);
		// Show the Up button in the action bar.
		setupActionBar();
		
		EditText ipAddress = (EditText)findViewById(R.id.ip);
		InputFilter[] filters = new InputFilter[1];
		    filters[0] = new InputFilter() {
		        @Override
		        public CharSequence filter(CharSequence source, int start, int end,
		                android.text.Spanned dest, int dstart, int dend) {
		            if (end > start) {
		                String destTxt = dest.toString();
		                String resultingTxt = destTxt.substring(0, dstart) + source.subSequence(start, end) + destTxt.substring(dend);
		                if (!resultingTxt.matches ("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) { 
		                    return "";
		                } else {
		                    String[] splits = resultingTxt.split("\\.");
		                    for (int i=0; i<splits.length; i++) {
		                        if (Integer.valueOf(splits[i]) > 255) {
		                            return "";
		                        }
		                    }
		                }
		            }
		            return null;
		        }

		    };
		    ipAddress.setFilters(filters);
	}

	
	//to change background of edittext ip
	
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_i, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
//	EditText edAddress = (EditText) findViewById(R.id.ip);
//	//edAddress.setKeyListener(IPAddressKeyListener.getInstance());
//	
//	public static class IPAddressKeyListener extends NumberKeyListener {
//		 
//	    private char[] mAccepted;
//	    private static IPAddressKeyListener sInstance;
//	 
//	    @Override
//	    protected char[] getAcceptedChars() {
//	        return mAccepted;
//	    }
//	 
//	    /**
//	     * The characters that are used.
//	     * 
//	     * @see KeyEvent#getMatch
//	     * @see #getAcceptedChars
//	     */
//	    private final char[] CHARACTERS =
//	 
//	    new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.' };
//	 
//	    private IPAddressKeyListener() {
//	        mAccepted = CHARACTERS;
//	    }
//	 
//	    /**
//	     * Returns a IPAddressKeyListener that accepts the digits 0 through 9, plus the dot
//	     * character, subject to IP address rules: the first character has to be a digit, and
//	     * no more than 3 dots are allowed.
//	     */
//	    public static IPAddressKeyListener getInstance() {
//	        if (sInstance != null) return sInstance;
//	 
//	        sInstance = new IPAddressKeyListener();
//	        return sInstance;
//	    }
//	 
//	    /**
//	     * Display a number-only soft keyboard.
//	     */
//	    public int getInputType() {
//	        return InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL;
//	    }
//	 
//	    /**
//	     * Filter out unacceptable dot characters.
//	     */
//	    @Override
//	    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
//	            int dend) {
//	        CharSequence out = super.filter(source, start, end, dest, dstart, dend);
//	 
//	        if (out != null) {
//	            source = out;
//	            start = 0;
//	            end = out.length();
//	        }
//	 
//	        int decimal = -1;
//	        int dlen = dest.length();
//	 
//	        // Prevent two dot characters in a row
//	        if (dstart > 0 && dest.charAt(dstart - 1) == '.') {
//	            decimal = dstart - 1;
//	        }
//	        if (dend < dlen && dest.charAt(dend) == '.') {
//	            decimal = dend;
//	        }
//	 
//	        // Up to three dot charcters, and no more
//	        if (decimal == -1) {
//	            int decimalCount = 0;
//	            for (int i = 0; i < dstart; i++) {
//	                char c = dest.charAt(i);
//	 
//	                if (c == '.') {
//	                    decimalCount++;
//	                    decimal = i;
//	                }
//	            }
//	            for (int i = dend; i < dlen; i++) {
//	                char c = dest.charAt(i);
//	 
//	                if (c == '.') {
//	                    decimalCount++;
//	                    decimal = i;
//	                }
//	            }
//	 
//	            if (decimalCount < 3) {
//	                decimal = -1;
//	            }
//	        }
//	 
//	        SpannableStringBuilder stripped = null;
//	 
//	        for (int i = end - 1; i >= start; i--) {
//	            char c = source.charAt(i);
//	            boolean strip = false;
//	 
//	            if (c == '.') {
//	                if (i == start && dstart == 0) {
//	                    strip = true;
//	                } else if (decimal >= 0) {
//	                    strip = true;
//	                } else {
//	                    decimal = i;
//	                }
//	            }
//	 
//	            if (strip) {
//	                if (end == start + 1) {
//	                    return ""; // Only one character, and it was stripped.
//	                }
//	 
//	                if (stripped == null) {
//	                    stripped = new SpannableStringBuilder(source, start, end);
//	                }
//	 
//	                stripped.delete(i - start, i + 1 - start);
//	            }
//	        }
//	 
//	        if (stripped != null) {
//	            return stripped;
//	        } else if (out != null) {
//	            return out;
//	        } else {
//	            return null;
//	        }
//	    }
//	}

}
