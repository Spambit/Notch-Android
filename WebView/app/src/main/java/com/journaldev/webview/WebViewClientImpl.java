package com.journaldev.webview;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class WebViewClientImpl extends WebViewClient {

    private Activity activity = null;

    public WebViewClientImpl(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return null;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        return super.shouldOverrideUrlLoading(webView, url);
    }

    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view,url);
        insetChange(view);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    public int getDPsFromPixels(Activity context, int pixels){
        Resources r = context.getResources();
        int  dps = Math.round(pixels/(r.getDisplayMetrics().densityDpi/160f));
        return dps;
    }

    public void insetChange(WebView view){
        MainActivity main = (MainActivity) this.activity;
        String left = ""+getDPsFromPixels(this.activity, main.getInsets().getSystemWindowInsetLeft());// getDisplayCutout().getSafeInsetLeft());
        String top = ""+getDPsFromPixels(this.activity,main.getInsets().getSystemWindowInsetTop());//.getDisplayCutout().getSafeInsetTop());
        String right = ""+getDPsFromPixels(this.activity,main.getInsets().getSystemWindowInsetRight());// getDisplayCutout().getSafeInsetRight());
        String bottom = ""+getDPsFromPixels(this.activity,main.getInsets().getSystemWindowInsetBottom());//.getDisplayCutout().getSafeInsetBottom());
        String js = String.format("document.documentElement.style.setProperty('--hsi-safe-area-left', '%spx');\n" +
                "document.documentElement.style.setProperty('--hsi-safe-area-top', '%spx');\n" +
                "document.documentElement.style.setProperty('--hsi-safe-area-right', '%spx');\n" +
                "document.documentElement.style.setProperty('--hsi-safe-area-bottom', '%spx');", left, top, right, bottom);
        view.evaluateJavascript(js,null);
    }
}