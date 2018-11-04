package com.example.pret_ur.webbrowser;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnTouchListener {
    WebView myWebView;
    //private WebView webView ;
    MyWebClient myWebClient;
    ProgressBar progressBar;
    ImageButton search;
    SharedPreferences sharedpreferences;
    EditText myurl;
    Button bt;
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<String> favoris;
    Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //instanciation de ma Arraylist
        favoris = new ArrayList<>();
        myurl = findViewById(R.id.et);
        bt = (Button) findViewById(R.id.go);
        myWebView = (WebView) findViewById(R.id.webview);

        search = (ImageButton) findViewById(R.id.search);
        // You can be pretty confident that the intent will not be null here.
        Intent intent = getIntent();
        // Get the extras (if there are any)
         extras = intent.getExtras();
        //if there is data
        if (extras != null) {
            String isNew = extras.getString("url", "");
            myWebView.loadUrl(isNew);
        }
        else{

            myWebView.loadUrl("http://mastersid.univ-rouen.fr");
        }



        //Active le lien dans le webview
        //
        myWebClient=new MyWebClient(this);
        myWebView.setWebViewClient(myWebClient);

        //charger les pages
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        this.myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebChromeClient(new MyWebChromeClient(this));
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myurlet = myurl.getText().toString();
                //Autoriser le javaScript d'executer dans webview
                myWebView.getSettings().setLoadsImagesAutomatically(true);

                myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

                WebSettings settings = myWebView.getSettings();
                settings.setJavaScriptEnabled(true);
                myWebView.loadUrl(myurlet);
            }
        });


        myurl.setOnTouchListener(this);


    }
//Gérer  le bouton back


    public void pop(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_file);
        popup.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_file, menu);
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.myWebView.canGoBack()) {
            this.myWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.i1:

                swipeRefreshLayout.setRefreshing(true);
                myWebView.reload();
                //Toast.makeText(this, "Refraichir", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.a:
                onBackPressed();
                // Toast.makeText(this, "presedent", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.b:
                onForwordPressed();
                //  Toast.makeText(this, "suivant", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.c:
//                //Toast.makeText(this, "favoris", Toast.LENGTH_SHORT).show();
//                sharedpreferences.edit().putString("link", myWebClient.muurl).apply();

                // test  : if  tab1 !=null et  contient 1 element  : boucle for
                // recuperer  le contenu dans  un tab2

                if (getSaredFavoris() != null && getSaredFavoris().size() > 0) {
                    if (!getSaredFavoris().contains(myWebClient.muurl)) {
                        this.favoris.addAll(getSaredFavoris());
                        this.favoris.add(myWebClient.muurl);
                        setSaredFavoris(this.favoris);
                        this.favoris.clear();
                    }
//                    for (String favoris : getSaredFavoris()
//                            ) {
//
//                        if (!favoris.equals(myWebClient.muurl)) {
//                            this.favoris.addAll(getSaredFavoris());
//                            this.favoris.add(myWebClient.muurl);
//                            setSaredFavoris(this.favoris);
//                            this.favoris.clear();
//                        }
//                    }

                } else {

                    // creation  d'un  tableau  avec  remlissage  avec  favoris  et  sauvegarde   shared  prefs
                    favoris.add(myWebClient.muurl);
                    //  sauvegarde   .....
                    setSaredFavoris(favoris);
                    favoris.clear();
                }

                for (String a : getSaredFavoris()
                        ) {
                    Log.e("link", a);
                }

                return true;

            case R.id.d:
                //Toast.makeText(this, "favoris", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this,Web_favoris.class);
                intent.putExtra("favoris",getSaredFavoris());
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void onForwordPressed() {
        if (myWebView.canGoForward()) {
            myWebView.goForward();
        } else
            Toast.makeText(this, "Pas de page Suivant", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        if (myWebView.canGoBack()) {
            myWebView.goBack();
        } else
            finish();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.et:
                myurl.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(myurl, InputMethodManager.SHOW_IMPLICIT);
                break;
        }

        return false;
    }


    private ArrayList<String> getSaredFavoris() {
        SharedPreferences sharedpreferences = getSharedPreferences("sharedpreferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedpreferences.getString("favoris", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    private void setSaredFavoris(ArrayList<String> favoris) {
        SharedPreferences sharedpreferences = getSharedPreferences("sharedpreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(favoris);
        editor.putString("favoris", json);
        editor.apply();

    }

}
