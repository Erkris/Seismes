package com.lp.pierrerubier.seismes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class MyActivity extends Activity {

    private static final int RESULT_SETTINGS = 1;
    TextView titleSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //Titre de la section
        titleSection = (TextView)findViewById(R.id.titleSection);

        showSeismes();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(MyActivity.this, "Settings", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, SetPreferenceActivity.class);
            startActivityForResult(i, RESULT_SETTINGS);
            return true;
        } else if (id == R.id.exit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SETTINGS:
                showSeismes();
                break;

        }
    }

    private void showSeismes() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (Connectivity.isConnected(this) == true) {
            //Affichage du titre du fichier recu

            String magnitude = sharedPrefs.getString("prefMagnitude","4.5");
            String frequency = sharedPrefs.getString("prefFrequency", "day");
            SeismeParse.urlString = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/" + magnitude + "_" + frequency + ".geojson";
            titleSection.setText("Seisme de Magnitude supérieure à " + magnitude);

            //ListView
            final ListView myListView = (ListView)findViewById(R.id.myListView);

            SeismeParse.listItem.clear();
            new SeismeParse(MyActivity.this).execute(myListView);

            myListView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            HashMap<String, Object> map = (HashMap<String, Object>) myListView.getItemAtPosition(i);

                            Intent intentMap = new Intent(MyActivity.this, MapsActivity.class);
                            intentMap.putExtra("lat", (Double)map.get("lat"));
                            intentMap.putExtra("long", (Double)map.get("long"));
                            intentMap.putExtra("title", (String)map.get("title"));
                            intentMap.putExtra("url", (String)map.get("url"));

                            startActivity(intentMap);
                            //finish();
                        }
                    }
            );
        } else {
            Toast.makeText(MyActivity.this, "Veuillez vous connecter à internet", Toast.LENGTH_SHORT).show();
            titleSection.setText("Vous n'êtes pas connecté à internet!");
        }
    }


}
