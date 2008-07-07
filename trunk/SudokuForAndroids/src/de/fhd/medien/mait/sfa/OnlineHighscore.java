package de.fhd.medien.mait.sfa;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class OnlineHighscore extends Activity
  {
    public void onCreate(Bundle icicle)
      {
        super.onCreate(icicle);
        
        WebView web = new WebView(this);
        web.loadUrl("http://dac-xp.com/sfa_highscore/highscore.php");
        web.reload();
        
        this.setContentView(web);
      }
    
    public void onResume()
      {
        super.onResume();
        
        WebView web = new WebView(this);
        web.loadUrl("http://dac-xp.com/sfa_highscore/highscore.php");
        web.clearCache();
        web.reload();
        
        this.setContentView(web);
      }
    
    public void onFreeze(Bundle outState)
      {
        super.onFreeze(outState);

        WebView web = new WebView(this);
        web.loadUrl("http://dac-xp.com/sfa_highscore/highscore.php");
        web.clearCache();
        web.reload();
        
        this.setContentView(web);
        
        this.finish();
      }
  }
