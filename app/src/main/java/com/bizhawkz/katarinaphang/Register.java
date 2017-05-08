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
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Register extends AppCompatActivity {
    EditText ed_firstname, ed_lastname, ed_emailid, ed_password, ed_ageuser;
    String name, lstname, mail, password, age;
    Button btnRegi;
    int aging;
    ProgressDialog pb;
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
        setContentView(R.layout.activity_register);
        pb = new ProgressDialog(Register.this);
        ed_firstname = (EditText) findViewById(R.id.ed_firstName);
        ed_lastname = (EditText) findViewById(R.id.ed_lastName);
        ed_emailid = (EditText) findViewById(R.id.ed_email);
        ed_password = (EditText) findViewById(R.id.ed_password);
        ed_ageuser = (EditText) findViewById(R.id.ed_age);

        btnRegi = (Button) findViewById(R.id.btn_Submit);
        btnRegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = ed_firstname.getText().toString();
                lstname = ed_lastname.getText().toString();
                mail = ed_emailid.getText().toString();
                password = ed_password.getText().toString();
                if(TextUtils.isEmpty(password) || password.length() < 4)
                {
                    ed_password.requestFocus();
                    ed_password.setError("You must have 6 characters in your password");
                    return;
                }
                age = ed_ageuser.getText().toString();
                if (name.matches("") || lstname.matches("") || mail.matches("") ||
                        password.matches("") || age.matches("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    TextView myMsg = new TextView(Register.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);

                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("All fields are mandatory");
                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();
                } else {
                    if (mail.matches(Expn) && mail.length() > 0) {
                        aging=Integer.parseInt(age);
                        if((aging>18)&&(aging<100)) {
                            new Registration().execute();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"You must be 18+ to register yourself",Toast.LENGTH_LONG).show();
                        }
                               /* Intent it= new Intent(Register.this,OptionScreen.class);
                                startActivity(it);*/
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                        TextView myMsg = new TextView(Register.this);
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
    }


    private class Registration extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();

            pb.setMessage("Please wait while Loading...");
            pb.show();
            pb.setCancelable(false);
            name = ed_firstname.getText().toString();
            lstname = ed_lastname.getText().toString();
            mail = ed_emailid.getText().toString();
            password = ed_password.getText().toString();
            age = ed_ageuser.getText().toString();

        }

        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();

            String url1 ="http://outsourcingservicesusa.com/clients/katrina/insertdata.php?caseid=1&fname="+name.replaceAll(" ", "")+"&lname="+lstname.replaceAll(" ", "") + "" +
                    "&email="+ mail.replaceAll(" ", "") + "&password=" + password.replaceAll(" ", "") + "" +
                    "&age=" + age.replaceAll(" ", "") +"";

            String SetServerString = "";
            HttpGet httpget = new HttpGet(url1);
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
            try {
                pb.dismiss();
                JSONObject jsonResult = new JSONObject(result);
                String message = jsonResult.getString("udata");
                Log.d("Response: ", "> " + message);
                if (message.equals("1")) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    TextView myMsg = new TextView(Register.this);
                    myMsg.setText("Congratulations!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("You have Registered in successfully.");
                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                     ed_firstname.setText("");
                                    ed_lastname.setText("");
                                    ed_emailid.setText("");
                                    ed_password.setText("");
                                    ed_ageuser.setText("");
                                    Intent it= new Intent(Register.this,Login.class);
                                    startActivity(it);
                                }
                            });
                    builder.show();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    TextView myMsg = new TextView(Register.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Already Registered");
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

