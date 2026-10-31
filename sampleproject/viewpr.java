package com.example.user.gethelpme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class viewprofile extends AppCompatActivity {
    EditText e13,e14,e15,e16,e17,e18,e19;
    Button b5,b9;
    ImageView iv5;
    String url="",url1="",ip="";
    SharedPreferences sh;
    String name,gender,place,post,dis,email,phone;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i =new Intent(getApplicationContext(),userhome
                .class);
        startActivity(i);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewprofile);
        e13=(EditText)findViewById(R.id.editText13);
        e14=(EditText)findViewById(R.id.editText14);
        e15=(EditText)findViewById(R.id.editText15);
        e16=(EditText)findViewById(R.id.editText16);
        e17=(EditText)findViewById(R.id.editText17);
        e18=(EditText)findViewById(R.id.editText18);
        e19=(EditText)findViewById(R.id.editText19);
        iv5=(ImageView)findViewById(R.id.imageView5);
        b5=(Button)findViewById(R.id.button5);
        b9=(Button)findViewById(R.id.button9);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        RequestQueue queue = Volley.newRequestQueue(viewprofile.this);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = e13.getText().toString();
                gender = e14.getText().toString();
                place = e15.getText().toString();
                post = e16.getText().toString();
                dis = e17.getText().toString();
                email = e18.getText().toString();
                phone = e19.getText().toString();
                if (name.equalsIgnoreCase("")) {
                    e13.setError("Enter Your Name");
                } else if (place.equalsIgnoreCase("")) {
                    e15.setError("Enter Your place");
                } else if (post.equalsIgnoreCase("")) {
                    e16.setError("Enter Your post");
                } else if (dis.equalsIgnoreCase("")) {
                    e17.setError("Enter Your district");
                } else if (phone.equalsIgnoreCase("")) {
                    e19.setError("Enter Your phonenumber");
                } else if (phone.length() < 10) {
                    e19.setError("Minimum 10 nos required");
                    e19.requestFocus();
                } else if (email.equalsIgnoreCase("")) {
                    e18.setError("Enter Your Email");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    e18.setError("Enter Valid Email");
                    e18.requestFocus();
                } else if (gender.equalsIgnoreCase("")) {
                    e14.setError("Enter Your gender");
                } else {

                    RequestQueue queue = Volley.newRequestQueue(viewprofile.this);
                    url = "http://" + sh.getString("ip", "") + ":5000/update";

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
                                    Toast.makeText(viewprofile.this, "updated sucessfully", Toast.LENGTH_SHORT).show();
                                    Intent ik = new Intent(getApplicationContext(), userhome.class);
                                    startActivity(ik);


                                } else {

                                    Toast.makeText(viewprofile.this, "not updated", Toast.LENGTH_SHORT).show();

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
                            params.put("lid", sh.getString("lid", ""));
                            params.put("name", name);
                            params.put("gender", gender);
                            params.put("post", post);
                            params.put("place", place);
                            params.put("dis", dis);
                            params.put("phone", phone);
                            params.put("email", email);


                            return params;
                        }
                    };
                    queue.add(stringRequest);


                }
            }

        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik= new Intent(getApplicationContext(),changephoto.class);
                startActivity(ik);
            }
        });
        url1 = "http://" + sh.getString("ip", "") + ":5000/viewprofile";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    {
                        JSONObject jo=ar.getJSONObject(0);
                        e13.setText(jo.getString("name"));
                        e14.setText(jo.getString("gender"));
                        e15.setText(jo.getString("place"));
                        e16.setText(jo.getString("post"));
                        e17.setText(jo.getString("district"));
                        e18.setText(jo.getString("email"));
                        e19.setText(jo.getString("phone"));


                        if(android.os.Build.VERSION.SDK_INT>9)
                        {
                            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                        }

//        i2.setImageDrawable(Drawable.createFromPath(getIntent().getStringExtra("photo"))));
                        java.net.URL thumb_u;
                        try {

                            //thumb_u = new java.net.URL("http://192.168.43.57:5000/static/photo/flyer.jpg");

                            thumb_u = new java.net.URL("http://"+sh.getString("ip","")+":5000/static/photos/"+jo.getString("photo"));
                            Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
                            iv5.setImageDrawable(thumb_d);

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

                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("uid", sh.getString("lid", ""));


                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
