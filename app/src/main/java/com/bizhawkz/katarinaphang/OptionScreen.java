package com.bizhawkz.katarinaphang;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class OptionScreen extends AppCompatActivity {
 ImageView tv_about,tv_blog,tv_product,tv_member,tv_coaching,tv_contact;
    ImageView iv_fb,ipin,igoogle,itwitter;
    SessionManager1 session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_screen);
        tv_about=(ImageView)findViewById(R.id.home);
        tv_blog=(ImageView)findViewById(R.id.Blog);
        tv_product=(ImageView)findViewById(R.id.product1);
        tv_coaching=(ImageView)findViewById(R.id.coaching);
        tv_member=(ImageView)findViewById(R.id.membership2);
        tv_contact=(ImageView)findViewById(R.id.contact);
        iv_fb=(ImageView)findViewById(R.id.iv_facebook);
        ipin=(ImageView)findViewById(R.id.iv_pin);
        igoogle=(ImageView)findViewById(R.id.iv_google);
        itwitter=(ImageView)findViewById(R.id.iv_twitter);

        session = new SessionManager1(getApplicationContext());
        tv_coaching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ti= new Intent(OptionScreen.this,Coaching.class);
                startActivity(ti);
            }
        });
        tv_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ti= new Intent(OptionScreen.this,MainActivity.class);
                startActivity(ti);
            }
        });
        tv_blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ti= new Intent(OptionScreen.this,Blog.class);
                startActivity(ti);
            }
        });
        tv_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ti= new Intent(OptionScreen.this,Products.class);
                startActivity(ti);
            }
        });
        tv_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.checkLogin();

                Intent ti= new Intent(OptionScreen.this,Membership.class);
                startActivity(ti);
            }
        });
        tv_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.checkLogin();

                Intent ti= new Intent(OptionScreen.this,Contactus.class);
                startActivity(ti);
            }
        });
        iv_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.facebook.com/katarina.phang"));
                startActivity(intent);
            }
        });
        ipin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://katarinaphang.com/login/"));
                startActivity(intent);
            }
        });
        igoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://plus.google.com/up/accounts/upgrade/?continue=https://plus.google.com/share?url%3Dhttp://katarinaphang.com/login/?utm_campaign%253Dshareaholic%2526utm_medium%253Dgoogle_plus%2526utm_source%253Dsocialnetwork"));
                startActivity(intent);
            }
        });
        itwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://twitter.com/intent/tweet?text=Member%20Login%20-%20http%3A%2F%2Fgo.shr.lc%2F2alCZlN%20via%20%40Shareaholic&source=&related=shareaholic"));
                startActivity(intent);
            }
        });

    }
}
