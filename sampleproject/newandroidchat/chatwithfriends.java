package com.example.fakenews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class chatwithfriends extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView L1;
    SharedPreferences sh;
    ArrayList<String> name,photo,rid;
    String url,type;
    String ttid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatwithfriends);
        L1=findViewById(R.id.LIST);
        L1.setOnItemClickListener(chatwithfriends.this);
        type=getIntent().getStringExtra("type");


        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url ="http://"+sh.getString("ip", "") + ":5000/viewfriends";
        RequestQueue queue = Volley.newRequestQueue(chatwithfriends.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    rid= new ArrayList<>();
                    name= new ArrayList<>();

                    photo=new ArrayList<>();

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        rid.add(jo.getString("lid"));
                        name.add(jo.getString("fname"));

                        photo.add(jo.getString("photo"));


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    L1.setAdapter(new custom2(chatwithfriends.this,photo,name));


                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(chatwithfriends.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("uid",sh.getString("lid",""));

                return params;
            }
        };
        queue.add(stringRequest);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ttid=rid.get(position);
        SharedPreferences.Editor edp = sh.edit();
        edp.putString("uid", ttid);
        edp.commit();

        Intent i=new Intent(getApplicationContext(), Test.class);
//        i.putExtra("toid",rid.get(position));

        i.putExtra("name",name.get(position));
        i.putExtra("photo",photo.get(position));

        i.putExtra("type",type);

        startActivity(i);


    }
}