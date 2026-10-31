package com.example.user.raven;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class feedback extends AppCompatActivity {

    EditText feedback;
    JSONParser jsonParser = new JSONParser();
    SharedPreferences sp;
    Button fdclk;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i =new Intent(getApplicationContext(),Home.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedback=(EditText)findViewById(R.id.editText3);
        fdclk=(Button)findViewById(R.id.button);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        fdclk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fb = feedback.getText().toString();
                if (fb.equals("")) {
                    feedback.setError("Enter your feedback");
                    feedback.requestFocus();
                } else {
                    try {


                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("uid", sp.getString("uid", "")));
                        params.add(new BasicNameValuePair("fedbck", fb));
                        String url = "http://" + sp.getString("ip", "") + ":5000/feedbac";

                        JSONObject jobj = null;

                        jobj = (JSONObject) jsonParser.makeHttpRequest(url, "POST", params);
                        String ss = jobj.getString("task");
                        if (ss.equals("sucessfully")) {

                            Toast.makeText(getApplicationContext(), "sucessfully inserted", Toast.LENGTH_LONG).show();
                            ;
                        } else {
                            Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
                            ;
                        }


                    } catch (Exception e) {
                    }
                }
            }
        });

    }
}

