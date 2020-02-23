package com.journaldev.webview;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends Activity {
    private WebView webView = null;
    private WindowInsetsCompat insets;

    WindowInsetsCompat getInsets() {
        return insets;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getWindow().setStatusBarColor(Color.WHITE);
            //ActionBar v = getActionBar();
            //v.hide();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.webView = (WebView) findViewById(R.id.webview);
        webView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                /*
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR |
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR */);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        final WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView.setBackgroundColor(Color.parseColor("#ffffff"));
        webView.setWebViewClient(webViewClient);

        ViewCompat.setOnApplyWindowInsetsListener(webView, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                if(!insets.isConsumed()) {
                    insets.consumeDisplayCutout();
                    insets.consumeSystemWindowInsets();
                    MainActivity.this.insets = insets;
                    webViewClient.insetChange(webView);
                }
                return insets;
            }
        });

        View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        // Note that system bars will only be "visible" if none of the
                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            int i = 0;
                            Log.d("test",""+i);
                            // TODO: The system bars are visible. Make any desired
                            // adjustments to your UI, such as showing the action bar or
                            // other navigational controls.
                        } else {
                            int i = 0;
                            Log.d("test",""+i);
                            //webView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            //                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE/*
                            //                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION*/);
                            // TODO: The system bars are NOT visible. Make any desired
                            // adjustments to your UI, such as hiding the action bar or
                            // other navigational controls.
                        }
                    }
                });

        webView.loadUrl("http://192.168.29.19:8081");
        //webView.loadData("<html><body>Hello, world!</body></html>", "text/html", "UTF-8");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            Toast.makeText(this,"key pressed", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}