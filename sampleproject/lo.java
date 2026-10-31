package com.example.user.raven;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    String ip="192.168.43.58";
    EditText uname,passwd;
    JSONParser jsonParser = new JSONParser();
    SharedPreferences sp;

    Button loginclk;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uname=(EditText) findViewById(R.id.editText);
        passwd=(EditText)findViewById(R.id.editText2);
        loginclk=(Button)findViewById(R.id.button2);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        loginclk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String un = uname.getText().toString();
                String pwd = passwd.getText().toString();
                if (un.equals("")) {
                    uname.setError("Enter username");
                    uname.requestFocus();
                } else if (pwd.equals("")) {
                    passwd.setError("Enter password");
                    passwd.requestFocus();
                } else if(pwd.length()!=8)
                {
                    passwd.setError("Minimum 8 nos required");
                    passwd.requestFocus();


                }

                else {

                    try {


                        List<NameValuePair> params = new ArrayList<NameValuePair>();

                        params.add(new BasicNameValuePair("uname", un));

                        params.add(new BasicNameValuePair("passwd", pwd));

                        String url = "http://" + ip + ":5000/login";


                        JSONObject jobj = (JSONObject) jsonParser.makeHttpRequest(url, "GET", params);

                        String ss = jobj.getString("task");

                        if (ss.equals("invalid")) {

                            Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
                            ;
                        } else

                        {
                            SharedPreferences.Editor ed = sp.edit();
                            ed.putString("uid", ss);
                            ed.putString("ip", ip);

                            ed.commit();

                            Intent i = new Intent(getApplicationContext(), Home.class);
                            startActivity(i);

                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e + "fff", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }
}
