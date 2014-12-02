package com.lp.pierrerubier.seismes;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by pierrerubier on 29/11/2014.
 */
public class SeismeSettings extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
    }
}
