
package com.example.google.playservices.placecomplete;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import com.example.android.common.activities.SampleActivityBase;
import com.example.android.common.logger.Log;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends SampleActivityBase
        implements GoogleApiClient.OnConnectionFailedListener {

    /**
     * GoogleApiClient wraps our service connection to Google Play Services and provides access
     * to the user's sign in state as well as the Google's APIs.
     */
    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mAdapter;
    private AutoCompleteTextView mAutocompleteView;
    private AutoCompleteTextView mAutocompleteView2;
    private TextView mPlaceDetailsText;
    private TextView mPlaceDetailsAttribution;
    ProgressBar pb, pb2, pb3;

    private static final LatLngBounds BOUNDS_GREATER_DELHI = new LatLngBounds(
            new LatLng(28.2754, 76.3948), new LatLng(29.0129, 77.7901));
    String origin = "";
    String destination = "";
    String rs = "seek";
    String time = "now";
    RadioButton now, later;

    //EditText tv;
    TextView jdt;

    String uname = "", pref, gen;
    String nl = "now";
    String sll = "";
    String dll = "";
    Integer c = 0;
    String ll = "";
    Button s;
    public static Integer y,m,d,h,mi;
    public static final int REQUEST_TIMEOUT_MS = 20000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        uname = intent.getStringExtra("UserID");
        //Toast.makeText(getApplicationContext(),uname,Toast.LENGTH_SHORT).show();
        gen = intent.getStringExtra("Gender");
        pref = intent.getStringExtra("Pref");


        // Construct a GoogleApiClient for the {@link Places#GEO_DATA_API} using AutoManage
        // functionality, which automatically sets up the API client to handle Activity lifecycle
        // events. If your activity does not extend FragmentActivity, make sure to call connect()
        // and disconnect() explicitly.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();

        setContentView(R.layout.activity_main);

        now = (RadioButton) findViewById(R.id.now);
        later = (RadioButton) findViewById(R.id.later);
        // Retrieve the AutoCompleteTextView that will display Place suggestions.
        mAutocompleteView = (AutoCompleteTextView)
                findViewById(R.id.autocomplete_places);

        // Register a listener that receives callbacks when a suggestion has been selected
        mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);

        // Retrieve the TextViews that will display details and attributions of the selected place.
        mPlaceDetailsText = (TextView) findViewById(R.id.place_details);
        mPlaceDetailsAttribution = (TextView) findViewById(R.id.place_attribution);

        // Set up the adapter that will retrieve suggestions from the Places Geo Data API that cover
        // the entire world.
        mAdapter = new PlaceAutocompleteAdapter(this, android.R.layout.simple_list_item_1,
                mGoogleApiClient, BOUNDS_GREATER_DELHI, null);
        mAutocompleteView.setAdapter(mAdapter);
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb2 = (ProgressBar) findViewById(R.id.progressBar2);
        pb3 = (ProgressBar) findViewById(R.id.progressBar3);

        //tv=(EditText) findViewById(R.id.timev);
        jdt = (TextView) findViewById(R.id.finalj);
        s = (Button) findViewById(R.id.button5);
        //d=(Button) findViewById(R.id.button_des);
        //d.setEnabled(false);
        //setO();
        c = 0;
        s.setEnabled(false);
        mAutocompleteView2 = (AutoCompleteTextView)
                findViewById(R.id.autocomplete_places2);

        mAutocompleteView2.setEnabled(false);
    }

    /**
     * Listener that handles selections from suggestions from the AutoCompleteTextView that
     * displays Place suggestions.
     * Gets the place id of the selected item and issues a request to the Places Geo Data API
     * to retrieve more details about the place.
     *
     * @see com.google.android.gms.location.places.GeoDataApi#getPlaceById(com.google.android.gms.common.api.GoogleApiClient,
     * String...)
     */
    //Set Origin



    /*public void setO() {
        origin = "Terminal 1, Indira Gandhi International Airport, New Delhi, Delhi, India";
        sll = "28.562696,77.119783";
        c++;

        Toast.makeText(this, "The origin is set to Terminal 1, Indira Gandhi International Airport, New Delhi, Delhi, India", Toast.LENGTH_SHORT);
        o.setEnabled(false);
        d.setEnabled(true);
    }*/


    /*public void set(View view)
    {   Toast.makeText(getApplicationContext(), "Clicked: " + "Origin", Toast.LENGTH_SHORT).show();

        if (c==0)
            CandO();
        else if (c==1)
            CandD();

    }*/
    public void CandO() {
        AutoCompleteTextView editText = (AutoCompleteTextView) findViewById(R.id.autocomplete_places);
        origin = editText.getText().toString();
        //editText.setText("");
        Toast.makeText(this, origin, Toast.LENGTH_LONG).show();
        Log.i(TAG, "Origin details received: " + origin);
        c++;
        //o.setEnabled(false);
        //d.setEnabled(true);
        //sll=ll;
        //o.setText("Destination");

        // Register a listener that receives callbacks when a suggestion has been selected
        mAutocompleteView2.setOnItemClickListener(mAutocompleteClickListener);

        mAutocompleteView2.setAdapter(mAdapter);

        pb.setVisibility(View.GONE);
        mAutocompleteView2.setEnabled(true);
        mAutocompleteView2.requestFocus();

        //sll=place.getLatLng().toString();

    }

    public void CandD() {

        AutoCompleteTextView editText = (AutoCompleteTextView) findViewById(R.id.autocomplete_places2);
        destination = editText.getText().toString();
        //editText.setText("");
        Toast.makeText(this,
                destination,
                Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Destination details received: " + destination);
        pb2.setVisibility(View.GONE);
        s.setEnabled(true);
        //c++;
        //dll=ll;
        //dll=place.getLatLng().toString();
    }

    /*public void Drive(View view) {
        rs="drive";
        Toast.makeText(this,
                rs,
                Toast.LENGTH_SHORT).show();

    }

    public void Ride(View view) {
        rs="ride";
        Toast.makeText(this,
                rs,
                Toast.LENGTH_SHORT).show();
    }*/

    public void setNow() {
        Calendar c = Calendar.getInstance();
        long seconds = c.getTimeInMillis();
        //tv.setText("" +seconds, TextView.BufferType.EDITABLE);
        //tv.setEnabled(false);
        time = "" + seconds;
        nl = "now";
    }

    /*public void setTime(View view)
    {
        setNow();

    }*/

    public void jDet(View view) {
        Toast.makeText(getApplicationContext(), "Clicked: " + "Submit", Toast.LENGTH_SHORT).show();
        pb3.setVisibility(View.VISIBLE);
        /*time=""+tv.getText().toString();
        jdt.setText("You are a " + rs + " in system from " + origin + " to " + destination + " at " + time);*/
        //setNow();
        sendD();


        //jdt.setText("Hello");

    }

    public void time(View v) {
        nl = "later";
        now.setChecked(false);
        DialogFragment newFragment2 = new TimePickerFragment();
        newFragment2.show(getSupportFragmentManager(), "timePicker");
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

        //System.out.println(a/1000);

    }

    public void timenow(View v) {
        setNow();
        later.setChecked(false);


    }
    public void setLater()
    {
        String da=String.format("%04d",y)+String.format("%02d",m)+String.format("%02d",d)+String.format("%02d",h)+String.format("%02d",mi)+"00";
        SimpleDateFormat dfm = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            long a = dfm.parse(da).getTime();
            time=""+a;
            Log.i("Time test",""+a);
            //Toast.makeText(getApplicationContext(),""+a,Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            //Toast.makeText(getApplicationContext(),"Cannot parse time!",Toast.LENGTH_SHORT).show();
        }
    }

    public void sendD() {
        //Toast.makeText(getApplicationContext(),origin + " fwawda" + destination,Toast.LENGTH_LONG).show();
        //Log.e(TAG, origin + " fwawda" + destination);
        if (nl.equals("later"))
        {
            setLater();
        }
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        start("No match found");
        //String url = "http://traffickarma.iiitd.edu.in:8090/RideSave?source=" + URLEncoder.encode(origin) + "&destination=" + URLEncoder.encode(destination) + "&timestamp=" + URLEncoder.encode(time) + "&userid=" + URLEncoder.encode(uname) + "&type=" + rs + "&schedule=" + nl + "&sourcell=" + URLEncoder.encode(sll) + "&destinationll=" + URLEncoder.encode(dll) + "&gender=" + gen + "&pref=" + pref;
        //Log.i(TAG, url);
        //StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
               // new Response.Listener<String>() {
                   // @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        //mTextView.setText("Response is: "+ response.substring(0,500));
//                        Log.i(TAG, response);
//                        if (response == "No one matches")
//                            Toast.makeText(MainActivity.this, "Sorry, no one matches on your route", Toast.LENGTH_SHORT);
//                        else
//                            start(response);
//                        //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
//
//                    }
               // }, new Response.ErrorListener() {
            //@Override
            //public void onErrorResponse(VolleyError error) {
              //  Log.i(TAG, "Error" + error.toString());
            //}
      //  });

//        stringRequest.setRetryPolicy(
//                new DefaultRetryPolicy(
//                        REQUEST_TIMEOUT_MS,
//                        0,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Add the request to the RequestQueue.
        //queue.add(stringRequest);
        //Log.i(TAG, "Request send");
        //c=0;

    }

    public void start(String a) {
        pb3.setVisibility(View.GONE);
        Intent intent = new Intent(this, ShowDet.class);
        //intent.putExtra("JSONArray", a);
        intent.putExtra("SLL", sll);
        intent.putExtra("DLL", dll);
        reset();
        startActivity(intent);
        //finish();
    }

    public void reset() {
        origin = "";
        destination = "";
        rs = "";
        time = "";
        //d.setEnabled(false);
        //tv.setText("");
        jdt.setText("");
        //tv.setEnabled(true);
        nl = "later";
        sll = "";
        dll = "";
        c = 0;
        s.setEnabled(false);
        mAutocompleteView.setText("");
        mAutocompleteView2.setText("");
        pb.setVisibility(View.GONE);
        pb2.setVisibility(View.GONE);
        pb3.setVisibility(View.GONE);
        //setO();
    }


    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a PlaceAutocomplete object from which we
             read the place ID.
              */
            final PlaceAutocompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "Autocomplete item selected: " + item.description);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
              details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

            Toast.makeText(getApplicationContext(), "Clicked: " + item.description, Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Called getPlaceById to get Place details for " + item.placeId);
        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);

            // Format details of the place for display and show it in a TextView.
            /*mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                    place.getId(), place.getAddress(), place.getPhoneNumber(),
                    place.getWebsiteUri()));*/

            //String ll=place.getLatLng().toString();
            ll = place.getLatLng().latitude + "," + place.getLatLng().longitude;
            // Display the third party attributions if set.
            final CharSequence thirdPartyAttribution = places.getAttributions();
           /* if (thirdPartyAttribution == null) {
                mPlaceDetailsAttribution.setVisibility(View.GONE);
            } else {
                mPlaceDetailsAttribution.setVisibility(View.VISIBLE);
                mPlaceDetailsAttribution.setText(Html.fromHtml(thirdPartyAttribution.toString()));
            }*/
            Handler handler = new Handler();
            if (c == 0) {
                pb.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    public void run() {

                        Log.e(TAG, c.toString());

                        sll = ll;
                        Log.i(TAG, sll);
                        //Toast.makeText(getApplicationContext(), "Select Origin", Toast.LENGTH_SHORT).show();
                        //c++;
                        CandO();


                    }
                }, 2000);
            } else {
                pb2.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    public void run() {

                        //Toast.makeText(getApplicationContext(), "Select Destination", Toast.LENGTH_SHORT).show();
                        dll = ll;
                        CandD();

                    }
                }, 2000);
            }


            //mPlaceDetailsText.setText(ll);

            places.release();
        }
    };


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            h=hourOfDay;
            mi=minute;
            //setLater();

            // Do something with the time chosen by the user
        }
    }

    public static class DatePickerFragment extends android.support.v4.app.DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            y=year;
            m=month;
            d=day;


            // Do something with the date chosen by the user
        }
    }


    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }

    /**
     * Called when the Activity could not connect to Google Play services and the auto manager
     * could resolve the error automatically.
     * In this case the API is not available and notify the user.
     *
     * @param connectionResult can be inspected to determine the cause of the failure
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(this,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }


}
