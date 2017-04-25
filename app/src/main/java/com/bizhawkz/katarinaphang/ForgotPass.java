package com.bizhawkz.katarinaphang;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ForgotPass extends AppCompatActivity {
    EditText edforgot;
    Button btnsub;
    String forgot;
    ProgressDialog pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        edforgot=(EditText)findViewById(R.id.ed_mail);
        btnsub=(Button)findViewById(R.id.btn_Submit);
        pb = new ProgressDialog(ForgotPass.this);
        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgot=edforgot.getText().toString();
                new Forgot().execute();
            }
        });
    }

    private class Forgot extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pb.setMessage("Please wait while Loading...");
            pb.show();
            forgot=edforgot.getText().toString();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            String url = "http://outsourcingservicesusa.com/clients/katrina/insertdata.php?caseid=3&email="+forgot.replaceAll(" ","")+"";
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

        @Override
        protected void onPostExecute(String result) {
            pb.cancel();
            try {
                pb.dismiss();
                JSONObject jsonResult = new JSONObject(result);
                String message = jsonResult.getString("udata");
                Log.d("Response: ", "> " + message);
                if (message.equals("11")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPass.this);
                    TextView myMsg = new TextView(ForgotPass.this);
                    myMsg.setText("Thank You!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Your password has been sent to your registered id.");
                    builder.setPositiveButton("Continue.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent it = new Intent(ForgotPass.this, Login.class);
                                    startActivity(it);

                                }
                            });
                    builder.show();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPass.this);
                    TextView myMsg = new TextView(ForgotPass.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("This email id is not registered with us");
                    builder.setPositiveButton("OK.",
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
