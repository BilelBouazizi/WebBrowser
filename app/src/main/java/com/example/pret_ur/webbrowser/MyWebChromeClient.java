package com.example.pret_ur.webbrowser;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MyWebChromeClient extends WebChromeClient {

     MainActivity parent ;

        public MyWebChromeClient(MainActivity parent) {
         this.parent=parent ;
         }

         @Override
 public void onProgressChanged (WebView view , int progress ) {
         parent.progressBar.setProgress ( progress );
         if( progress == 100) {
             parent . setTitle ( view . getTitle () );
             }
         }
 }



