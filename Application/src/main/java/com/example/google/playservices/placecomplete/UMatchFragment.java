package com.example.google.playservices.placecomplete;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class UMatchFragment extends Fragment {

    private static final String TAG ="Testing: " ;
    int mCurrentPage;
    ArrayList<DataBean> contentList = new ArrayList<DataBean>();
    ListView listView ;
    ArrayList<String> values=new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parse();

        /** Getting the arguments to the Bundle object */
        //Bundle data = getArguments();

        /** Getting integer data of the key current_page from the bundle */
        //mCurrentPage = data.getInt("current_page", 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_layout, container,false);
        //TextView tv = (TextView) v.findViewById(R.id.tv);
        // dp=new DataParsing();
        //MainActivity ma=new MainActivity();
        //String data=parse(mCurrentPage);
        listView = (ListView) v.findViewById(R.id.list);
        //parse();
        //tv.setText("Hello There!");
        /*values.add("a");
        values.add("b");
        values.add("c");*/
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.user_layout_row, values);
        CustomList c = new CustomList(getActivity(),values,contentList);
        listView.setAdapter(c);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                //String itemValue = (String) listView.getItemAtPosition(position);
                String itemValue=contentList.get(position).username+", "+contentList.get(position).age+"\n"+contentList.get(position).gender+"\n"+contentList.get(position).source+"\n"+contentList.get(position).dest+"\n Out of way: "+contentList.get(position).outofway;

                // Show Alert
                Toast.makeText(getActivity(),
                        itemValue, Toast.LENGTH_LONG)
                        .show();

            }

        });
        return v;
    }

    public void parse()
    {
        String list="";
        //HashMap<Integer, ArrayList<DataBean>> contentMap = new HashMap<Integer, ArrayList<DataBean>>();

        try {

            for (int i = 0; i < ShowDet.jsa.length(); i++) {
                DataBean db= new DataBean("","You clicked on a route number. CLick on username","","","","","");
                contentList.add(db);
                values.add("Route "+(i+1));
                JSONObject jsox = (JSONObject) ShowDet.jsa.get(i);
                JSONArray jsoa1=(JSONArray) jsox.get(i+"");
                for (int j=0; j<jsoa1.length(); j++)
                {
                    JSONObject jso1 = (JSONObject) jsoa1.get(j);
                    String username = jso1.get("userName") + "";
                    String rideID = jso1.get("rideId") + "";

                    String age = jso1.get("age") + "";
                    //int userId = Integer.parseInt(tempUid);

                    String gender = jso1.get("sex") + "";
                    String outofway = jso1.get("outOfWay") + "";
                    String source = jso1.get("source") + "";
                    String dest = jso1.get("destination") + "";

                    //int id = Integer.parseInt(tempId);
                    db = new DataBean(age, username, gender, outofway, source, dest, rideID);
                    values.add(username+" ("+rideID+")");
                    contentList.add(db);
                }

            }

            Log.i(TAG, list);
        }
        catch (Exception e)
        {
            Log.i(TAG, "Exception in parse body" + e.toString());
            //list="Error";
        }

        //return list;

    }

}
