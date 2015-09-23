package com.example.google.playservices.placecomplete;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.common.logger.Log;

import java.net.URLEncoder;


public class Signup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String MESS ="Testing Signup" ;
    Spinner gen,age;
    Button signup;
    private String[] gender= {"Female","Male","Others"};
    private String[] agegroups= {"15-19","20-24","25-29","30-39","40+"};
    String u,a="15-19",g="Female",p,e,pr="No",uid,cn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        gen = (Spinner) findViewById(R.id.gender);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, gender);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gen.setAdapter(adapter_state);
        gen.setOnItemSelectedListener(this);
        setTitle("Ride Share");

        age = (Spinner) findViewById(R.id.age);
        ArrayAdapter<String> adapter_state2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, agegroups);
        adapter_state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age.setAdapter(adapter_state2);
        age.setOnItemSelectedListener(this);

        signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Signup button","Here works!");
                getV();

            }

        });


    }

    /*public void setPref(View view)
    {
        CheckBox pref = (CheckBox) findViewById(R.id.pref);
        if (pref.isSelected()==true)
            p="Yes";
        else
            p="No";
    }*/

    public void getV()
    {
        Log.i("Here in getV","Working okay till now");
        EditText usname = (EditText) findViewById(R.id.uname);
        EditText mail = (EditText) findViewById(R.id.email);
        EditText pass = (EditText) findViewById(R.id.pass);
        EditText passco = (EditText) findViewById(R.id.passc);
        EditText con = (EditText) findViewById(R.id.cn);
        cn=con.getText().toString();
        u=usname.getText().toString();
        String pi=pass.getText().toString();
        String pc=passco.getText().toString();
        e=mail.getText().toString();
        g=gen.getSelectedItem().toString();
        a=age.getSelectedItem().toString();
        CheckBox pref = (CheckBox) findViewById(R.id.pref);
        if (pref.isChecked())
            pr="Yes";
        else
            pr="No";

        if (u.equals("")||e.equals("")||pi.equals("")||pc.equals(""))
            Toast.makeText(Signup.this, "Some fields are left empty!", Toast.LENGTH_SHORT).show();
        else
        {
            if (pi.equals(pc)) {
                p = pi;
                sendD();
            }
            else {
                Toast.makeText(Signup.this, "Enter same password in both fields", Toast.LENGTH_SHORT).show();
                Log.d("Hello!","Not same!");
            }
        }


    }

    public void start()
    {
        Intent i = new Intent(Signup.this, Login.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(view.getId()==gen.getId())
        {
            gen.setSelection(position);
            String selState = (String) gen.getSelectedItem();
            g=selState;
            //Toast.makeText(Signup.this,selState,Toast.LENGTH_SHORT).show();
        }
        if(view.getId()==age.getId())
        {
            age.setSelection(position);
            String selState = (String) age.getSelectedItem();
            a=selState;
            //Toast.makeText(Signup.this,selState,Toast.LENGTH_SHORT).show();
        }
    }

    public void sendD()
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="http://traffickarma.iiitd.edu.in:8090/UserDataServlet?age="+URLEncoder.encode(a)+"&username="+ URLEncoder.encode(u)+"&sex="+g+"&genderpreference="+pr+"&pass="+URLEncoder.encode(p)+"&email="+URLEncoder.encode(e)+"&conu="+URLEncoder.encode(cn);

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
                        start();

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
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
