package com.example.filesharing;

import android.app.Activity;
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
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
import java.util.List;

public class DownloadFile extends Activity implements AdapterView.OnItemClickListener {

    ListView l1;
    SharedPreferences sh;
    JSONParser jsonParser = new JSONParser();
    String url="";
    JSONArray json=new JSONArray();

    ArrayList<String> fid,fname,desc,date;
    int pos=0;

    ProgressDialog mProgressDialog;
    private PowerManager.WakeLock mWakeLock;
    static final int DIALOG_DOWNLOAD_PROGRESS = 2;

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),Profile.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_file);


        l1=(ListView) findViewById(R.id.left_drawer);

        l1.setOnItemClickListener(DownloadFile.this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        url="http://"+sh.getString("ip","")+"/view_file";
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("uid", sh.getString("uid","0")));




        try {
            json = (JSONArray)jsonParser.makeHttpRequest(url,"GET", params);
        } catch (JSONException e1) {
            Toast.makeText(getApplicationContext(),"Error"+e1,Toast.LENGTH_LONG).show();

            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
//            Log.d("ins===",json.toString());
        String s=null;

        try {
            Log.d("==============", "Emmmntered");

            Log.d("Msg+++++++++", json.toString());
            fid=new ArrayList<String>();
            fname=new ArrayList<String>();
            desc=new ArrayList<String>();
            date=new ArrayList<String>();




            for (int i = 0; i < json.length(); i++) {
                JSONObject c=json.getJSONObject(i);
                fid.add(c.getString("id"));
                fname.add(c.getString("filename"));
                desc.add(c.getString("description"));
                date.add(c.getString("date"));


                Log.d("+++++++++++",c+"");

            }

            l1.setAdapter(new Custom(this, fname,desc,date));

        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error"+e,Toast.LENGTH_LONG).show();
            Log.d("==============", "Catched");

            Log.d( "Error"+e,"================");
        }









    }


    private void startDownload(String fn) {
        String url = "http://"+sh.getString("ip", "")+"/static/file/"+fn;
        new DownloadFileAsync().execute(url);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//#############################################

        pos=position;


        AlertDialog.Builder ald=new AlertDialog.Builder(DownloadFile.this);
        ald.setTitle("File")
                .setPositiveButton(" View File ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try
                        {

                            SharedPreferences.Editor ed=sh.edit();
                            ed.putString("orginal",fname.get(pos));
                            ed.commit();

                            startDownload(fname.get(pos));


                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNegativeButton(" Share File ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent i=new Intent(getApplicationContext(),ShareFile.class);
                        i.putExtra("imgid", fid.get(pos));
                        startActivity(i);
                    }
                });

        AlertDialog al=ald.create();
        al.show();


//    #################################################

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
