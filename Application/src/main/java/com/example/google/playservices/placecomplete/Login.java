package com.example.google.playservices.placecomplete;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.common.logger.Log;

import java.net.URLEncoder;


public class Login extends AppCompatActivity {

    private static final String MESS = "Login Activity";
    Button sub;

    String u, p, s;
    Integer save = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sub = (Button) findViewById(R.id.button);
        loadSavedPreferences();
        sub.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Intent intent = new Intent(Login.this, MainActivity.class);
                                       startActivity(intent);
                                       //getV();

                                   }
                               }
        );
    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //boolean checkBoxValue = sharedPreferences.getBoolean("CheckBox_Value", false);
        String name = sharedPreferences.getString("storedUName", "");
        String passw = sharedPreferences.getString("storedP", "");
        EditText usname = (EditText) findViewById(R.id.uname);

        EditText pass = (EditText) findViewById(R.id.pass);
        pass.setText(passw);
        usname.setText(name);
        if (name.equals("") == true && passw.equals("") == true) {
            save = 1;
        } else {
            getV();
        }
       /* if (checkBoxValue) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }*/


    }

    private void savePreferences(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void start() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        //Toast.makeText(getApplicationContext(),s+" and "+s.split(",")[0],Toast.LENGTH_LONG).show();
        intent.putExtra("UserID", s.split(",")[0]);
        intent.putExtra("Gender", s.split(",")[1]);
        intent.putExtra("Pref", s.split(",")[2]);
        startActivity(intent);
        finish();
    }

    public void getV() {
        EditText usname = (EditText) findViewById(R.id.uname);

        EditText pass = (EditText) findViewById(R.id.pass);

        u = usname.getText().toString();
        p = pass.getText().toString();
        if (save == 1) {
            savePreferences("storedUName", u);
            savePreferences("storedP", p);
        }
        sendD();

    }


    public void sendD() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://traffickarma.iiitd.edu.in:8090/LoginServlet?username=" + URLEncoder.encode(u) + "&pass=" + p;

        //String url = "http://192.168.1.201:8090/RideSave?source="+ URLEncoder.encode(origin)+"&destination="+URLEncoder.encode(destination)+"&timestamp="+URLEncoder.encode(time)+"&userid="+URLEncoder.encode(uname)+"&type="+rs+"&schedule="+nl+"&sourcell="+URLEncoder.encode(sll)+"&destinationll="+URLEncoder.encode(dll);

        Log.i(MESS, url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        //Log.i(TAG, "No Error");
                        if (response.equals("Wrong"))
                            Toast.makeText(Login.this, "No such username or wrong password", Toast.LENGTH_SHORT).show();
                        else {
                            s = response;
                            //uid=s.split(",")[0];
                            Log.i(MESS, "Response received" + response);
                            start();
                        }

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
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }
}
