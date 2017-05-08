package com.bizhawkz.katarinaphang;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class Coaching extends AppCompatActivity {
    Toolbar toolbar;
    WebView webView;
    ProgressDialog pb;
    String url;
    SwipeRefreshLayout mySwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coaching);
        mySwipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipeContainer);
        initToolBar();
        // spinner = (Spinner) findViewById(R.id.spinner);

       /* spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        List<String> categories = new ArrayList<String>();
        categories.add("Overview");
        categories.add("Seven Reason Why Men Marry some women and dump others");
        categories.add("The journey inward Audio");
        categories.add("Four Components of Melting his heart Audio");
        categories.add("He's Really that into you,he'sjust not Ready");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);*/

        webView = (WebView) findViewById(R.id.web1);
        pb = new ProgressDialog(Coaching.this);
        pb.setMessage("Please wait while Loading...");
        pb.show();


        webView.setWebViewClient(new MyWebViewClient());
         url = "http://newsite.katarinaphang.com/coaching/";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:document.getElementById(\"header\").setAttribute(\"style\",\"display:none;\");");
                webView.loadUrl("javascript:document.getElementById(\"navi-wrap\").setAttribute(\"style\",\"display:none;\");");
                webView.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('app_img')[0].style.display='none'; })()");
                pb.dismiss();
                mySwipeRefreshLayout.setRefreshing(false);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Coaching.this);
                TextView myMsg = new TextView(Coaching.this);
                myMsg.setText("Mobile Data is off");
                myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                myMsg.setTextSize(20);
                myMsg.setTextColor(Color.BLACK);
                builder.setCustomTitle(myMsg);
                builder.setMessage("Turn on mobile data or use Wi-Fi to access data.");
                builder.setPositiveButton("ok",
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
                builder.show();
            }
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


    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Coaching");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back2_icon);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent It = new Intent(Coaching.this, OptionScreen.class);
                        startActivity(It);
                    }
                });
    }


    /* public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         item = parent.getItemAtPosition(position).toString();
        if(item.equals("Overview")){
            url="http://katarinaphang.com/products/overview/";
        }else if (item.equals("Seven Reason Why Men Marry some women and dump others"))
        {
            url="http://katarinaphang.com/products/seven-reasons-why-men-marry-some-women-and-dump-others/";
        }
         else if (item.equals("The journey inward Audio"))
        {
            url="http://katarinaphang.com/products/the-journey-inward-audio/";
        }
         else if(item.equals("Four Components of Melting his heart Audio"))
        {
            url="http://katarinaphang.com/products/four-components-of-melting-his-heart-audio/";
        }else if(item.equals("He's Really that into you,he'sjust not Ready")){
            url="http://katarinaphang.com/products/four-components-of-melting-his-heart-audio/";
        }
     }


     public void onNothingSelected(AdapterView<?> parent) {
     }*/
    public void onResume() {
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