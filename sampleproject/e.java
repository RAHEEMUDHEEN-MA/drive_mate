package com.example.user.gethelpme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewmechanics extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String url="",ip="";
    ArrayList<String> name,place,gender,post,dis,photo,email,phone,sname,splace,lati,longi,mid;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i =new Intent(getApplicationContext(),userhome.class);
        startActivity(i);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmechanics);
        l1=(ListView) findViewById(R.id.listView);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        url ="http://"+sh.getString("ip", "") + ":5000/viewmechanics";
        RequestQueue queue = Volley.newRequestQueue(viewmechanics.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                     mid= new ArrayList<>();
                     name= new ArrayList<>();
                     gender=new ArrayList<>();
                     place= new ArrayList<>();
                     post= new ArrayList<>();
                     dis= new ArrayList<>();
                     photo=new ArrayList<>();
                     email= new ArrayList<>();
                     phone= new ArrayList<>();
                     sname= new ArrayList<>();
                     splace= new ArrayList<>();
                     lati= new ArrayList<>();
                     longi= new ArrayList<>();

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        mid.add(jo.getString("lid"));
                        name.add(jo.getString("name"));
                        gender.add(jo.getString("gender"));
                        place.add(jo.getString("place"));
                        phone.add(jo.getString("phone"));
                        post.add(jo.getString("post"));
                        dis.add(jo.getString("district"));
                        photo.add(jo.getString("photo"));
                        email.add(jo.getString("email"));
                        sname.add(jo.getString("shopname"));
                        splace.add(jo.getString("shpplace"));
                        lati.add(jo.getString("lattitude"));
                        longi.add(jo.getString("longitude"));




                    }
                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new Custom(viewmechanics.this,name,place,phone));
                    l1.setOnItemClickListener(viewmechanics.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(viewmechanics.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lati", LocationService.lati);
                params.put("longi",LocationService.logi );


                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
        Intent in = new Intent(getApplicationContext(), viewmore.class);
        in.putExtra("mid", mid.get(i));
        in.putExtra("name", name.get(i));
        in.putExtra("gender", gender.get(i));
        in.putExtra("place", place.get(i));
        in.putExtra("post", post.get(i));
        in.putExtra("dis", dis.get(i));
        in.putExtra("photo", photo.get(i));
        in.putExtra("email", email.get(i));
        in.putExtra("phone", phone.get(i));
        in.putExtra("sname", sname.get(i));
        in.putExtra("splace", splace.get(i));
        in.putExtra("lati", lati.get(i));
        in.putExtra("longi", longi.get(i));
        startActivity(in);




    }
}
