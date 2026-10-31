package com.example.user.sampleproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
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

public class

viewmore extends AppCompatActivity {
    EditText e3, e6,e10;
    ImageView i2;
    SharedPreferences sh;
    Button b6;

    String name="",place="",gender="",url="" ,ip="",rid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmore);
        e3 = (EditText) findViewById(R.id.editText3);
        e6 = (EditText) findViewById(R.id.editText6);
        e10 = (EditText) findViewById(R.id.editText10);
        i2 = (ImageView) findViewById(R.id.imageView2);
        b6=(Button)findViewById(R.id.button6);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e3.setText(getIntent().getStringExtra("name"));
        e6.setText(getIntent().getStringExtra("place"));
        e10.setText(getIntent().getStringExtra("gender"));
        rid=getIntent().getStringExtra("rid");

        if(android.os.Build.VERSION.SDK_INT>9)
        {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

//        i2.setImageDrawable(Drawable.createFromPath(getIntent().getStringExtra("photo"))));
        java.net.URL thumb_u;
        try {

            //thumb_u = new java.net.URL("http://192.168.43.57:5000/static/photo/flyer.jpg");

            thumb_u = new java.net.URL("http://"+sh.getString("ip","")+":5000/static/photos/"+getIntent().getStringExtra("photo"));
            Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
            i2.setImageDrawable(thumb_d);

        }
        catch (Exception e)
        {
            Log.d("errsssssssssssss",""+e);
        }
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name= e3.getText().toString();
                place=e6.getText().toString();
                gender=e10.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(viewmore.this);
                url= "http://" + sh.getString("ip", "") + ":5000/update";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            String res = json.getString("task");

                            if (res.equalsIgnoreCase("success")) {
                                Toast.makeText(viewmore.this, "updated sucessfully", Toast.LENGTH_SHORT).show();
                                Intent ik = new Intent(getApplicationContext(), Home.class);
                                startActivity(ik);



                            } else {

                                Toast.makeText(viewmore.this, "not updated", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(getApplicationContext(), "Error"+error, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("lid",rid);
                        params.put("name",name );
                        params.put("place", place);
                        params.put("gender", gender);




                        return params;
                    }
                };
                queue.add(stringRequest);


            }
        });


    }


    }

