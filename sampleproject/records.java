package com.example.hp.blockchain;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class records extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView l1;
    int pos=0;

    ProgressDialog mProgressDialog;
    private PowerManager.WakeLock mWakeLock;
    static final int DIALOG_DOWNLOAD_PROGRESS = 2;
    SharedPreferences sh;
     String recordid;
    ArrayList<String>doctorname,date,record,rid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        l1=(ListView)findViewById(R.id.list3);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        RequestQueue queue = Volley.newRequestQueue(records.this);
        String url = "http://" + sh.getString("ip", "") + ":5000/viewrecord";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    doctorname= new ArrayList<>();
                    date= new ArrayList<>();
                    record=new ArrayList<>();
                    rid=new ArrayList<>();
                    for(int i=0;i<ar.length();i++)

                    {
                        JSONObject jo=ar.getJSONObject(i);
                        doctorname.add(jo.getString("Firstname"));
                        date.add(jo.getString("date"));
                        record.add(jo.getString("Record"));
                        rid.add(jo.getString("R_id"));
                    }

//                    ArrayAdapter<String> ad=new ArrayAdapter<String>(Searchnearestcharity.this,android.R.layout.simple_list_item_1,cname);
//                    l1.setAdapter(ad);

                    l1.setAdapter(new Customrecord(records.this,doctorname,date,record));
                    l1.setOnItemClickListener(records.this);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userid",sh.getString("lid",""));



                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
    private void startDownload(String fn) {
        String url = "http://"+sh.getString("ip", "")+":5000/static/files/"+fn;

        new DownloadFileAsync().execute(url);
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        recordid=rid.get(i);
        SharedPreferences.Editor edp = sh.edit();
        edp.putString("recid", recordid);
        edp.commit();

        pos=i;
        SharedPreferences.Editor ed=sh.edit();
        ed.putString("orginal",record.get(pos));
        ed.commit();
        AlertDialog.Builder ald=new AlertDialog.Builder(records.this);
        ald.setTitle("Select option")
                .setPositiveButton(" Download ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                       startDownload(record.get(pos));
                    }
                })
                .setNegativeButton("Share ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent i=new Intent(getApplicationContext(),share.class);
                        startActivity(i);

                    }
                });

        AlertDialog al=ald.create();
        al.show();
//        startDownload(record.get(pos));


    }
    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }
        @Override
        protected String doInBackground(String... aurl) {
            int count;

            try {
                Log.d("aurllll",aurl[0]);

                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Length of file: " + lenghtOfFile);

//	String filename = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss").format(new Date())+"ticket.html";
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + sh.getString("orginal", ""));

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC",progress[0]);
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Downloading File...");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                return mProgressDialog;
        }
        return null;
    }
}