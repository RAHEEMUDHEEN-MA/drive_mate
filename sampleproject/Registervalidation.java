package com.example.nature;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends Activity {
	EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10;
	Button b1;
	Spinner s1,s2;
	RadioButton r1,r2;
	SharedPreferences sp;
	String gender="";
	String type[]={"farmer","public user"};
String dist[]={"kasargod","kannur","wayanad","kozhikode","malappuam","thrissur","ernakulam","palakad","eduki","kollam","alapuzha","kottayam","pathanthitta","trivandrum"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		e3=(EditText)findViewById(R.id.editText3);
		e4=(EditText)findViewById(R.id.editText4);
		e5=(EditText)findViewById(R.id.editText5);
		e6=(EditText)findViewById(R.id.editText6);
		e7=(EditText)findViewById(R.id.editText7);
		e8=(EditText)findViewById(R.id.editText8);
		e9=(EditText)findViewById(R.id.editText9);
		e10=(EditText)findViewById(R.id.editText10);
		b1=(Button)findViewById(R.id.button1);
		s1=(Spinner)findViewById(R.id.spinner1);
		s2=(Spinner)findViewById(R.id.spinner2);
		r1=(RadioButton)findViewById(R.id.radio0);
		r2=(RadioButton)findViewById(R.id.radio1);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		ArrayAdapter<String > ad= new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_item,type);
		s1.setAdapter(ad);
		ArrayAdapter<String > a= new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_item,dist);
		s2.setAdapter(a);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String fname= e1.getText().toString();
				final String lname=e2.getText().toString();
				final String age=e3.getText().toString();
				final String mobile=e8.getText().toString();
				final String email=e4.getText().toString();
				final String place=e5.getText().toString();
				final String post=e6.getText().toString();
				final String pin=e7.getText().toString();
				final String usern=e9.getText().toString();
				final String pass=e10.getText().toString();
				final String dis=s2.getSelectedItem().toString();
				final String typ=s1.getSelectedItem().toString();
				 gender="";
				if(r1.isChecked())
				{
					gender=r1.getText().toString();
				}
				else
				{
					gender=r2.getText().toString();
				}
				  if(fname.equalsIgnoreCase("")) {
                    e1.setError("Please Enter Your FirstName");
                }
                else if(!fname.matches("^[a-z A-Z]*$"))
                {
                    e1.setError("characters allowed");
                    e1.requestFocus();
                }
                else if(lname.equalsIgnoreCase("")) {
                    e2.setError("Please Enter Your lastname");
                }

                else if(!lname.matches("^[a-z A-Z]*$"))
                {
                    e2.setError("characters allowed");
                    e2.requestFocus();
                }

                else  if(place.equalsIgnoreCase("")) {
                    e3.setError("Please Enter Your place");
                }

                else   if(post.equals("")) {
                    e4.setError("Please Enter Your post");
                }
                else  if(pin.equals(""))
                {
                    e5.setError("enter pin");
                }
                else if(pin.length()!=6)
                {
                    e5.setError("Minimum 6 nos required");
                    e5.requestFocus();
                }
                else if(email.equalsIgnoreCase(""))
                {
                    e8.setError("Enter Your Email");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    e8.setError("Enter Valid Email");
                    e8.requestFocus();
                }
                else if(phno.equalsIgnoreCase(""))
                {
                    e6.setError("Enter Your Phone No");
                }
                else if(phno.length()!=10)
                {
                    e6.setError("Minimum 10 nos required");
                    e6.requestFocus();
                }
                else if(uname.equalsIgnoreCase(""))
                {
                    e9.setError("Enter Your username");
                }
                else if(pw.equalsIgnoreCase(""))
                {
                    e10.setError("Enter Your password");
                }
                else {

					
				
				
				
				
				//====================
			
				// Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(Register.this);
                String url ="http://"+sp.getString("ip", "")+":5000/reg";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {
                            JSONObject json=new JSONObject(response);
                            String res=json.getString("task");
                            
                            if(res.equalsIgnoreCase("success"))
                            {
                                Toast.makeText(getApplicationContext(),"successfully register",Toast.LENGTH_LONG).show();
                                Intent in=new Intent(getApplicationContext(),Login.class)	;
                                
                            	 startActivity(in);
                            
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"registration failed",Toast.LENGTH_LONG).show();
                 
                            }
                           
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("fname", fname);
                        params.put("lname", lname);
                        params.put("gender", gender);
                        params.put("age", age);
                        params.put("place", place);
                        params.put("post", post);
                        params.put("pin", pin);
                        params.put("district", dis);
                        params.put("contact", mobile);
                        params.put("email", email);
                        params.put("username", usern);
                        params.put("password", pass);
                        params.put("type", typ);

                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);

				
					}
			
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
