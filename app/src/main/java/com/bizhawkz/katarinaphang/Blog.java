package com.bizhawkz.katarinaphang;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class Blog extends AppCompatActivity {
    Toolbar toolbar;
    WebView webView;
    ProgressDialog pb;
    SwipeRefreshLayout mySwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        mySwipeRefreshLayout = (SwipeRefreshLayout)this.findViewById(R.id.swipeContainer);
        initToolBar();
        String url = "http://newsite.katarinaphang.com/blog/";

        webView = (WebView) findViewById(R.id.web1);
        pb = new ProgressDialog(Blog.this);
        pb.setMessage("Please wait while Loading...");
        pb.show();

        webView.setWebViewClient(new MyWebViewClient());

        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:document.getElementById(\"header\").setAttribute(\"style\",\"display:none;\");");
                webView.loadUrl("javascript:document.getElementById(\"navi-wrap\").setAttribute(\"style\",\"display:none;\");");
                webView.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('app_img')[0].style.display='none'; })()");
                pb.dismiss();
                pb.dismiss();
                mySwipeRefreshLayout.setRefreshing(false);
            }
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Blog.this);
                TextView myMsg = new TextView(Blog.this);
                myMsg.setText("Mobile Data is off");
                myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                myMsg.setTextSize(20);
                myMsg.setTextColor(Color.BLACK);
                builder.setCustomTitle(myMsg);
                builder.setMessage("Turn on mobile data or use Wi-Fi to access data.");
                builder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                            }
                        });
                builder.setNegativeButton("Setting", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                        dialog.cancel();
                    }
                });
                builder.show();}
        });
        webView.loadUrl(url);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        webView.reload();
                    }
                }
        );
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Blog");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back2_icon);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent It = new Intent(Blog.this, OptionScreen.class);
                        startActivity(It);
                    }
                });
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    }
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()) {
            pb.show();
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}