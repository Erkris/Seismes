package com.lp.pierrerubier.seismes;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by pierrerubier on 02/12/2014.
 */
public class WebSiteSeisme extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_site);

        String url = getIntent().getStringExtra("url");

        WebView myWebView = (WebView)findViewById(R.id.myWebView);

        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setBuiltInZoomControls(true);

        myWebView.loadUrl(url);
    }
}
