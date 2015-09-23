package com.example.google.playservices.placecomplete;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.common.logger.Log;

import java.net.URLEncoder;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import static com.example.google.playservices.placecomplete.R.id.button;

public class UserLogin extends AppCompatActivity {

    //private static final  TAG = ;
    EditText usname;
    String gender="Male";
    String pref="No";
    String age;
    EditText usage;
    String uid="";
    RadioButton m;
    RadioButton f;
    RadioButton y;
    RadioButton n;
    String uname;
    Button b;
    public final static String MESS = "com.mycompany.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        usname = (EditText) findViewById(R.id.uname);
        usage = (EditText) findViewById(R.id.editText);
        Toast.makeText(UserLogin.this, "Enter a username " + uname, Toast.LENGTH_SHORT);

        m = (RadioButton) findViewById(R.id.radioButton);
        f = (RadioButton) findViewById(R.id.radioButton2);
        y = (RadioButton) findViewById(R.id.radioButton3);
        n = (RadioButton) findViewById(R.id.radioButton4);
        b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uname=usname.getText().toString();
                age=usage.getText().toString();
                 logini();
            }
        });
        loadSavedPreferences();
        n.setEnabled(false);
        y.setEnabled(false);
    }
    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //boolean checkBoxValue = sharedPreferences.getBoolean("CheckBox_Value", false);
        String name = sharedPreferences.getString("storedName", "");
       /* if (checkBoxValue) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }*/

        if (name.equals("")==false) {
            uid=name.toString();
            start();
        }
    }


    private void savePreferences(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public void setMale(View view)
    {
        gender="Male";
        f.setChecked(false);
        n.setEnabled(false);
        y.setEnabled(false);

    }
    public void setFemale(View view)
    {
        gender="Female";
        m.setChecked(false);
        n.setEnabled(true);
        y.setEnabled(true);

    }
    public void setYes(View view)
    {
        pref="Yes";
        n.setChecked(false);

    }
    public void setNo(View view)
    {
        pref="No";
        y.setChecked(false);

    }

    public void start()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MESS, uid);
        startActivity(intent);
        finish();
    }

    public void logini() {
       Log.e("rfef", uname + " few " + age);
        if (uname.matches("")==true) {

            Toast.makeText(this,"Enter a username",Toast.LENGTH_SHORT).show();

        }
        else if (age.matches("")==true)
        {

            Toast.makeText(this,"Enter a age",Toast.LENGTH_SHORT).show();
        }
        else {
            sendD();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Log.i(MESS, uid);
                    savePreferences("storedName", uid);
                    start();
                }
            }, 2000);
        }


    }
    public void sendD()
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="http://traffickarma.iiitd.edu.in:8090/UserDataServlet?age="+age+"&username="+URLEncoder.encode(uname)+"&sex="+gender+"&genderpreference="+pref;

        //String url = "http://192.168.1.201:8090/RideSave?source="+ URLEncoder.encode(origin)+"&destination="+URLEncoder.encode(destination)+"&timestamp="+URLEncoder.encode(time)+"&userid="+URLEncoder.encode(uname)+"&type="+rs+"&schedule="+nl+"&sourcell="+URLEncoder.encode(sll)+"&destinationll="+URLEncoder.encode(dll);

        Log.i(MESS, url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        //Log.i(TAG, "No Error");
                        uid=response;
                        Log.i(MESS, "Response received"+response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(MESS, "Error");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
        //Log.i(TAG, "Request send");
        //c=0;
        //reset();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
