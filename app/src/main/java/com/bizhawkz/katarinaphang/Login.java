package com.bizhawkz.katarinaphang;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Login extends AppCompatActivity {
    EditText mail,pass;
    TextView tvsign,tvforgot;
    Button submit;
    String mail1,pass1;
    ProgressDialog pb;
    SessionManager1 session;
    String Expn =
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new SessionManager1(getApplicationContext());

        pb = new ProgressDialog(Login.this);
        mail=(EditText)findViewById(R.id.ed_mail);
        pass=(EditText)findViewById(R.id.ed_password);
        tvsign=(TextView)findViewById(R.id.tv_signup);
        tvforgot=(TextView)findViewById(R.id.tv_forgotpass);
        tvsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(Login.this,Register.class);
                startActivity(it);
            }
        });
        submit=(Button)findViewById(R.id.btn_Submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail1=mail.getText().toString();
                pass1=pass.getText().toString();
                if(TextUtils.isEmpty(pass1) || pass1.length() < 4)
                {
                    pass.requestFocus();
                    pass.setError("You must have 6 characters in your password");
                    return;
                }
                if (mail1.matches("") || pass1.matches("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    TextView myMsg = new TextView(Login.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);

                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("All fields are mandatory.");
                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();
                }
                else {
                    if (mail1.matches(Expn) && mail1.length() > 0) {
                        new Logmem().execute();
                        session.createLoginSession(mail1.replaceAll(" ", ""), pass1.replaceAll(" ", ""));
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                        TextView myMsg = new TextView(Login.this);
                        myMsg.setText("Warning!");
                        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                        myMsg.setTextSize(20);

                        myMsg.setTextColor(Color.BLACK);
                        builder.setCustomTitle(myMsg);
                        builder.setMessage("Please enter a valid mail ID!");
                        builder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.cancel();
                                    }
                                });
                        builder.show();
                    }
                }
            }
        });

        tvforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent= new Intent(Login.this,ForgotPass.class);
                startActivity(intent);
            }
        });
    }

    private class Logmem  extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setMessage("Please wait while Loading...");
            pb.show();
            mail1 = mail.getText().toString().trim();
            pass1 = pass.getText().toString().trim();
        }

        @Override
        protected String doInBackground(String... urls) {
            HttpClient httpClient = new DefaultHttpClient();
            String url = "http://outsourcingservicesusa.com/clients/katrina/insertdata.php?caseid=2&email="+mail1.replaceAll(" ","")+"&password="+pass1.replaceAll(" ","")+"";
            String SetServerString = "";
            HttpGet httpget = new HttpGet(url);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                SetServerString = httpClient.execute(httpget, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("Response: ", "> " + SetServerString);
            return SetServerString;
        }

        protected void onPostExecute(String result) {
            pb.cancel();
            try {
                pb.dismiss();
                JSONObject jsonResult = new JSONObject(result);
                String message = jsonResult.getString("udata");
                Log.d("Response: ", "> " + message);
                if (message.equals("1")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                        TextView myMsg = new TextView(Login.this);
                        myMsg.setText("Congratulations!");
                        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                        myMsg.setTextSize(20);
                        myMsg.setTextColor(Color.BLACK);
                        builder.setCustomTitle(myMsg);
                        builder.setMessage("You have logged in successfully.");
                        builder.setPositiveButton("Continue",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Intent it = new Intent(Login.this, OptionScreen.class);
                                        startActivity(it);
                                    }
                                });
                        builder.show();

                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    TextView myMsg = new TextView(Login.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Email/Password is invalid.");
                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
