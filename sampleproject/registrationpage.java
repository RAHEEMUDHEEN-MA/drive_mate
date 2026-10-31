package com.example.user.sampleproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class registrationpage extends AppCompatActivity {
    EditText e1,e4,e7,e8,e9;
    Button b4,b8;
    ImageView i1;
    RadioButton r1,r2;
    SharedPreferences sh;
    private static final int FILE_SELECT_CODE = 0;
    int res;
    String fileName="",path="";
    String name,place,gender,photo,username,pwd,cpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationpage);
        e1=(EditText)findViewById(R.id.editText);
        e4=(EditText)findViewById(R.id.editText4);
        i1=(ImageView) findViewById(R.id.imageView);
        e7=(EditText)findViewById(R.id.editText7);
        e8=(EditText)findViewById(R.id.editText8);
        e9=(EditText)findViewById(R.id.editText9);
        r1=(RadioButton) findViewById(R.id.radioButton);
        r2=(RadioButton) findViewById(R.id.radioButton2);
        b4=(Button) findViewById(R.id.button4);
        b8=(Button) findViewById(R.id.button8);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(android.os.Build.VERSION.SDK_INT>9)
        {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //getting all types of files
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {
                    startActivityForResult(Intent.createChooser(intent, ""), FILE_SELECT_CODE);
                } catch (android.content.ActivityNotFoundException ex) {

                    Toast.makeText(getApplicationContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                }



            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = e1.getText().toString();
                place = e4.getText().toString();
                gender = "";
                if (r1.isChecked()) {
                    gender = r1.getText().toString();
                } else {
                    gender = r2.getText().toString();
                }
                username = e7.getText().toString();
                pwd = e8.getText().toString();
                cpwd = e9.getText().toString();
                if (name.equalsIgnoreCase("")) {
                    e1.setError("Enter Your Name");
                } else if (place.equalsIgnoreCase("")) {
                    e4.setError("Enter Your place");
                } else if (username.equalsIgnoreCase("")) {
                    e4.setError("Enter Your username");
                } else if (pwd.equalsIgnoreCase("")) {
                    e8.setError("Enter Your Password");
                } else if (pwd.length() != 8) {
                    e8.setError("Minimum 8 nos required");
                    e8.requestFocus();
                } else if (cpwd.equalsIgnoreCase("")) {
                    e9.setError("Enter Your Password");
                } else if (cpwd.length() != 8) {
                    e9.setError("Minimum 8 nos required");
                    e9.requestFocus();
                }

             else
                {

                res = uploadFile(path);

                    if (res == 1) {
                        Toast.makeText(getApplicationContext(), " registered", Toast.LENGTH_LONG).show();
                        Intent ik = new Intent(getApplicationContext(), mainactivity.class);
                        startActivity(ik);

                    } else {
                        Toast.makeText(getApplicationContext(), " error", Toast.LENGTH_LONG).show();
                    }

                }
            }

            });

        }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d("File Uri", "File Uri: " + uri.toString());
                    // Get the path
                    try {
                        path = FileUtils.getPath(this, uri);
                        //e4.setText(path1);
                        Log.d("path", path);
                        File fil = new File(path);
                        int fln = (int) fil.length();
                        //  e2.setText(path);

                        File file = new File(path);

                        byte[] byteArray = null;
                        try {
                            InputStream inputStream = new FileInputStream(fil);
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            byte[] b = new byte[fln];
                            int bytesRead = 0;

                            while ((bytesRead = inputStream.read(b)) != -1) {
                                bos.write(b, 0, bytesRead);
                            }

                            byteArray = bos.toByteArray();
                            inputStream.close();
                            Bitmap bmp= BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                            if(bmp!=null){
//
//
                                i1.setVisibility(View.VISIBLE);
                                i1.setImageBitmap(bmp);
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    } catch (URISyntaxException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Please select suitable file", Toast.LENGTH_LONG).show();
                }
                break;


        }


    }

    public int uploadFile(String sourceFileUri) {
        try {
            fileName = sourceFileUri;
            String upLoadServerUri = "http://" + sh.getString("ip", "") + ":5000/reg";
            Toast.makeText(getApplicationContext(), upLoadServerUri, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), path, Toast.LENGTH_LONG).show();
            FileUpload fp = new FileUpload(fileName);
            Map mp = new HashMap<String, String>();
            mp.put("name",name );
            mp.put("place",place);
            mp.put("gender",gender);
            mp.put("uname",username);
            mp.put("pwd",pwd);
            mp.put("cpwd",cpwd);
            fp.multipartRequest(upLoadServerUri, mp, fileName, "files", "application/octet-stream");
            return 1;

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"error"+e,Toast.LENGTH_LONG).show();
            return 0;
        }
    }


}

