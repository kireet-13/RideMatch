package com.example.google.playservices.placecomplete;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String> {
	private final Activity context;
	ArrayList<String> dat;
	ArrayList<DataBean> contentList = new ArrayList<DataBean>();
	public CustomList(Activity context,  ArrayList<String> name,ArrayList<DataBean> contentLists ) {
			super(context, R.layout.message_layout, name);
			this.context = context;
			dat=name;
		contentList=contentLists;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.message_layout, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.data);
		LinearLayout l = (LinearLayout) rowView.findViewById(R.id.parent);
		txtTitle.setText(dat.get(position));
		final String itemValue=contentList.get(position).username+", "+contentList.get(position).age+"\n"+contentList.get(position).gender +"\n"+contentList.get(position).source+"\n"+contentList.get(position).dest+"\n Out of way: "+contentList.get(position).outofway;
		if(dat.get(position).contains("Route")) {
			l.setBackgroundColor(Color.parseColor("#66ccff"));
		}
		l.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(),
						itemValue, Toast.LENGTH_LONG)
						.show();
			}
		});
		return rowView;
	}
}