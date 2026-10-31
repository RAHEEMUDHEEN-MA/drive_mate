package com.example.user.sampleproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
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

import static android.widget.AdapterView.*;

public class viewuser extends AppCompatActivity implements AdapterView.OnItemClickListener  {
    ListView l1;
    SharedPreferences sp;
    String url="",ip="";
    ArrayList<String> name,place,gender,photo,rid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewuser);
        l1=(ListView) findViewById(R.id.listView);
        sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        url ="http://"+sp.getString("ip", "") + ":5000/view";
        RequestQueue queue = Volley.newRequestQueue(viewuser.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    rid= new ArrayList<>();
                    name= new ArrayList<>();
                    place= new ArrayList<>();
                    gender=new ArrayList<>();
                    photo=new ArrayList<>();

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        rid.add(jo.getString("rid"));
                        name.add(jo.getString("name"));
                        place.add(jo.getString("place"));
                        gender.add(jo.getString("gender"));
                        photo.add(jo.getString("photo"));


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new Custom(viewuser.this,name,place));
                    l1.setOnItemClickListener(viewuser.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(viewuser.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        queue.add(stringRequest);
    }
        @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id){
            Intent in = new Intent(getApplicationContext(), viewmore.class);
            in.putExtra("rid", rid.get(i));
            in.putExtra("name", name.get(i));
            in.putExtra("place", place.get(i));
            in.putExtra("gender", gender.get(i));
            in.putExtra("photo", photo.get(i));
            startActivity(in);

        }
}
