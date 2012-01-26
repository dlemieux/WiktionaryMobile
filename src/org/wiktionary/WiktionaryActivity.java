package org.wiktionary;

import java.net.URLEncoder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.phonegap.DroidGap;

public class WiktionaryActivity extends DroidGap {
    /** Called when the activity is first created. */
	
	public class WiktionaryWebViewClient extends GapViewClient {
		public WiktionaryWebViewClient(DroidGap ctx) {
			super(ctx);
		}
		
		//@Override
		//public void onLoadResource(WebView view, String url) {
		//	Log.d("WiktionaryWebViewClient", "OnLoadResource "+url);
		//}
		
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          
        // Check if we were started by another app's Intent
        boolean startedFromAnotherApp = false;
        String wordToShow = null;
        
        Intent startingIntent = this.getIntent();
        if (startingIntent != null) {
          Bundle extraParams = startingIntent.getExtras();
          if (extraParams != null) {
            String value = extraParams.getString(Intent.EXTRA_TEXT);
            if (value != null && !value.trim().equals("")) {
              startedFromAnotherApp = true;
              wordToShow = value;
            }
          }
        }
		
        String startingUrl = "file:///android_asset/www/index.html";
        
        if (startedFromAnotherApp) { // Specify the word display on startup
          startingUrl += "?define=" + URLEncoder.encode(wordToShow);
        }
		
        super.loadUrl(startingUrl);
        this.webViewClient = new WiktionaryWebViewClient(this);
        this.appView.setWebViewClient(this.webViewClient);
        
        String currentUA = this.appView.getSettings().getUserAgentString();
        
        this.appView.getSettings().setUserAgentString("WikipediaMobile/1.1 " + currentUA);
    }
    
    @Override
    public void onReceivedError(final int errorCode, final String description, final String failingUrl) {
    	// no-op!
    }
}