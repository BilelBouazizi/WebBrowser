package com.example.pret_ur.webbrowser;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MyWebClient extends WebViewClient {
    MainActivity parent ;
    String muurl="";
  public MyWebClient(MainActivity param){
      this.parent=param;
      //String TAG="bilel";

  }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
    view.loadUrl(url);
    return true;
    }
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        parent.progressBar.setVisibility(View.VISIBLE);
        Toast.makeText(parent.getApplicationContext(),"Chargement ....",Toast.LENGTH_SHORT).show();
        //view.loadUrl(url);<
    }


    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view,url);
        parent.progressBar.setVisibility(View.GONE);
        Toast.makeText(parent.getApplicationContext(),"Terminé ",Toast.LENGTH_SHORT).show();
        parent.swipeRefreshLayout.setRefreshing(false);
        muurl=view.getUrl();
        Log.i("bilel",muurl);
    }
}
