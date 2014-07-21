package com.i360.estimotedemo;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.i360.estimotedemo.model.beaconLink;

public class modelListAdapter extends BaseAdapter {

	private ArrayList<beaconLink> beacons;
	private LayoutInflater inflater;
	private Context context;
	
	public modelListAdapter(Context context)
	{
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.beacons = new ArrayList<beaconLink>();
	}
	
	public void replaceWith(Collection<beaconLink> newBeacons)
	{
		this.beacons.clear();
		this.beacons.addAll(newBeacons);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return beacons.size();
	}

	@Override
	public beaconLink getItem(int position) {
		// TODO Auto-generated method stub
		return beacons.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		view = inflateIfRequired(view,position,parent);
		bind(getItem(position),view);			
		return view;
	}

	private void bind(beaconLink beacon, View view) {
		// TODO Auto-generated method stub
		ViewHolder holder = (ViewHolder) view.getTag();
		int imageid = this.context.getResources()
			       .getIdentifier("com.i360.estimotedemo:drawable/" + beacon.getModelImage(), null, null);
		holder.modelImageView.setImageResource(imageid);
		holder.modelNameView.setText("ModelName : " + beacon.getModelName());
		holder.modelYearView.setText("Model Year : " + beacon.getModelYear());
		holder.modelPriceView.setText("Price : " + beacon.getModelPrice());		
	}

	private View inflateIfRequired(View view, int position, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if (view == null)
		{
		  view = inflater.inflate(R.layout.model_item, null);
		  view.setTag(new ViewHolder(view));
		}
		
		return view;
	}
	
	static class ViewHolder {
		final ImageView modelImageView;
		//final TextView modelImageView;
		final TextView modelNameView;
		final TextView modelYearView;
		final TextView modelPriceView;
		
		ViewHolder(View view) {
			  modelImageView = (ImageView) view.findViewWithTag("modelimage");
			  modelNameView = (TextView) view.findViewWithTag("modelname");
			  modelYearView = (TextView) view.findViewWithTag("modelyear");
			  modelPriceView = (TextView) view.findViewWithTag("modelprice");
			  
		}
	}
}
