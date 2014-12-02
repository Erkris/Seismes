package com.lp.pierrerubier.seismes;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by pierrerubier on 29/11/2014.
 */
public class SetPreferenceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SeismeSettings()).commit();
    }

}