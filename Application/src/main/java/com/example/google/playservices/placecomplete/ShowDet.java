package com.example.google.playservices.placecomplete;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;
//import com.example.google.playservices.placecomplete.MyFragmentPageAdapter;


public class ShowDet extends FragmentActivity {
    public static JSONArray jsa;
    public static String origin;
    public static String dest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        origin=intent.getStringExtra("SLL");
        dest=intent.getStringExtra("DLL");
        try {
            String s=intent.getStringExtra("JSONArray");
            if (s=="No match found")
            {
                String k="{\"data\":[]";
                JSONObject jsao=new JSONObject(k);
                jsa=(JSONArray)jsao.get("data");
            }
            JSONObject jsao= new JSONObject(s);
            jsa=(JSONArray)jsao.get("data");

        }
        catch (Exception e)
        {

            System.out.println("s /n"+"Array Problem"+e.toString());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_det);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        /** Getting fragment manager */
        FragmentManager fm = getSupportFragmentManager();

        /** Instantiating FragmentPagerAdapter */
        MyFragmentPageAdapter pageAdapter= new MyFragmentPageAdapter(fm);


        /** Setting the pagerAdapter to the pager object */
        pager.setAdapter(pageAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_det, menu);
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
