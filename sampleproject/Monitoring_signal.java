package com.example.user.jamfree;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Monitoring_signal extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner s1, s2;
    Button b1;
    ImageView i1;
    SharedPreferences sh;
    String url = "", signalid="";
    ArrayList<String> signals,id;
    String sids[]={"left","right"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_signal);
        s1 = (Spinner) findViewById(R.id.spinner1);
        s2 = (Spinner) findViewById(R.id.spinner2);
        i1=(ImageView) findViewById(R.id.imageView4);
        b1 = (Button) findViewById(R.id.button8);

        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ArrayAdapter<String> ad=new ArrayAdapter<>(Monitoring_signal.this,android.R.layout.simple_list_item_1,sids);
        s2.setAdapter(ad);

        s2.setOnItemSelectedListener(this);

        if(android.os.Build.VERSION.SDK_INT>9)
        {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        url ="http://"+sh.getString("ip","")+":5000/view_signals";
        s1.setOnItemSelectedListener(Monitoring_signal.this);
        RequestQueue queue = Volley.newRequestQueue(Monitoring_signal.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    signals= new ArrayList<>(ar.length());
                    id= new ArrayList<>(ar.length());
                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        signals.add(jo.getString("NAME"));
                        id.add(jo.getString("ID"));


                    }

                    ArrayAdapter<String> ad=new ArrayAdapter<>(Monitoring_signal.this,android.R.layout.simple_spinner_item,signals);
                    s1.setAdapter(ad);

                   // l1.setAdapter(new custom2(Monitoring_signal.this,notification,date));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);






        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                final String side=s2.getSelectedItem().toString();

                RequestQueue queue = Volley.newRequestQueue(Monitoring_signal.this);
                url = "http://" + sh.getString("ip", "") + ":5000/moni_signals";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            String res = json.getString("task");

                            if (res.equalsIgnoreCase("error")) {
                                Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
                            } else {
                                    try {
                                        java.net.URL thumb_u;
                                        try {

//                                            thumb_u = new java.net.URL("http://192.168.43.57:5000/static/photo/IMG-20191017-WA0013.jpg");

                                            thumb_u = new java.net.URL("http://"+sh.getString("ip","")+":5000/static/photo/"+res);
                                            Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
                                            i1.setImageDrawable(thumb_d);

                                        }
                                        catch (Exception e)
                                        {
                                            Log.d("errsssssssssssss",""+e);
                                        }




                                    }
                                   catch (Exception e)
                                   {
                                    Log.d("errsssssssssssss",""+e);
                                   }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("signalid", signalid );
                        params.put("side", side );


                            return params;
                    }




                    };
                queue.add(stringRequest);






            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        signalid=signals.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

