package com.lp.pierrerubier.seismes;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pierrerubier on 10/11/2014.
 */
public class SeismeParse extends AsyncTask<Object, Void, ArrayList<HashMap<String, Object>>> {

    ProgressDialog waiting; // ProgressBar
    Context myContext; // Mon contexte
    public static String urlString  = "";

    public static ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
    HashMap<String, Object> map;
    ListView myListView;

    //Constructeur
    public SeismeParse(Context c) {
        myContext = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        waiting = new ProgressDialog(myContext);
        waiting.setMessage("Waiting...");
        waiting.show();
    }

    @Override
    protected ArrayList<HashMap<String, Object>> doInBackground(Object... tab) {

        myListView = (ListView)tab[0];
        String jsonResult = ""; // On récupère le résultat du JSON
        HttpURLConnection urlConnection = null; // Variable pour ouvrir ou fermer un connexion
        BufferedReader in; // Récupération du contenu
        String PHtml = ""; // in.readLine
        JSONObject json = null; // Notre objet JSON
        String setTitle, setPlace, setURL; // On récupère différente données du JSON
        int setTime ; // ....
        double setMagnitude, setLatitude, setLongitude ; // ....

        // récupération du JSON sous forme de String
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                in = new BufferedReader(new
                        InputStreamReader(urlConnection.getInputStream()));
                while ((PHtml = in.readLine()) != null) {
                    jsonResult += PHtml;
                }

                try {
                    // On associe à notre JSON Object la String du JSON récupérer
                    json = new JSONObject(jsonResult);

                    JSONArray jArray = json.getJSONArray("features");

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jsonProperties = jArray.getJSONObject(i).getJSONObject("properties");
                        setTitle = jsonProperties.getString("title");
                        setPlace = jsonProperties.getString("place");
                        setTime = jsonProperties.getInt("time");
                        setURL = jsonProperties.getString("url");
                        setMagnitude = jsonProperties.getDouble("mag");

                        JSONObject jsonCoordinates = jArray.getJSONObject(i).getJSONObject("geometry");
                        JSONArray arrayCoordinates = jsonCoordinates.getJSONArray("coordinates");
                        setLatitude = arrayCoordinates.getDouble(0);
                        setLongitude = arrayCoordinates.getDouble(1);

                        Seisme unSeisme = new Seisme(i, setTitle, setPlace, setTime, setURL, setMagnitude, setLatitude, setLongitude);
                        map = new HashMap<String, Object>();
                        map.put("id", unSeisme.getId());
                        map.put("title", unSeisme.getTitle());
                        map.put("place", unSeisme.getPlace());
                        map.put("mag", unSeisme.getMagnitude());
                        map.put("lat", unSeisme.getLatitude());
                        map.put("long", unSeisme.getLongitude());
                        map.put("url", unSeisme.getUrl());
                        listItem.add(map);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }



        return listItem;
    }

    @Override
    protected void onPostExecute(ArrayList<HashMap<String, Object>> s) {
        super.onPostExecute(s);

        // Creation d'un SimpleAdapter
        Log.d("list", listItem.toString());
        SimpleAdapter mySchedule = new SimpleAdapter(myContext, listItem, R.layout.view_list, new String[] {"title", "place", "mag"}, new int[] {R.id.titleSeisme, R.id.placeSeisme, R.id.magnitudeSeisme});

        myListView.setAdapter(mySchedule);
        waiting.dismiss();
    }
}
