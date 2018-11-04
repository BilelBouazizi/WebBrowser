package com.example.pret_ur.webbrowser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Web_favoris extends AppCompatActivity {
    ListView ListeFavoris;
    private ArrayList<String> favoris;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_favoris);
        ListeFavoris = (ListView) findViewById(R.id.ListeFavoris);
        favoris = getIntent().getExtras().getStringArrayList("favoris");

    /*    for (String a : favoris
                ) {
            Log.e("bilel", a);
        }
    }*/
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                favoris );

        ListeFavoris.setAdapter(arrayAdapter);
        ListeFavoris.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //recuperer les donnes contenu dans l'item de list view
                     name = (String) parent.getItemAtPosition(position);
                     Intent myIntet=new Intent(Web_favoris.this,MainActivity.class);
                     myIntet.putExtra("url", name);
                     startActivity(myIntet);


            }
        });


       // Log.e("bich",name);

    }
}