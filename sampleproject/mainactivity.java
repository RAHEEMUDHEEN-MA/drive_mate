package com.example.user.sampleproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class mainactivity extends AppCompatActivity {
EditText e2,e5;
Button b1,b3;
String url="",ip="192.168.43.58";
SharedPreferences sh;
String un,pass="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e2 = (EditText) findViewById(R.id.editText2);
        e5 = (EditText) findViewById(R.id.editText5);
        b1 = (Button) findViewById(R.id.button);
        b3 = (Button) findViewById(R.id.button3);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed = sh.edit();
        ed.putString("ip", ip);
        ed.commit();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                un = e2.getText().toString();
                pass = e5.getText().toString();
                if (un.equalsIgnoreCase("")) {
                    e2.setError("Enter Your username");
                } else if (pass.equalsIgnoreCase("")) {
                    e5.setError("Enter Your Password");
                } else if (pass.length() != 8) {
                    e5.setError("Minimum 6 nos required");
                    e5.requestFocus();
                } else {
                    RequestQueue queue = Volley.newRequestQueue(mainactivity.this);
                    url = "http://" + ip + ":5000/log";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("valid")) {
                                    String lid = json.getString("id");
                                    SharedPreferences.Editor edp = sh.edit();
                                    edp.putString("lid", lid);
                                    edp.commit();
                                    Intent ik = new Intent(getApplicationContext(), Home.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(mainactivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("uname", un);
                            params.put("pass", pass);

                            return params;
                        }
                    };
                    queue.add(stringRequest);

                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent ik = new Intent(getApplicationContext(), registrationpage.class);
                startActivity(ik);

            }});

    }
}