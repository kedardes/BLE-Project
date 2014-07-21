package com.i360.estimotedemo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.Utils;
import com.i360.estimotedemo.model.beaconLink;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LeDeviceListAdapter extends BaseAdapter {

	private ArrayList<Beacon> beacons;
	private LayoutInflater inflater;
	private ArrayList<beaconLink> beaconlinks;
	
	public LeDeviceListAdapter(Context context, ArrayList<beaconLink> beaconlinks)
	{
		this.beaconlinks = beaconlinks;
		this.inflater = LayoutInflater.from(context);
		this.beacons = new ArrayList<Beacon>();
	}
	
	public void replaceWith(Collection<Beacon> newBeacons)
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
	public Beacon getItem(int position) {
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

	private void bind(Beacon beacon, View view) {
		// TODO Auto-generated method stub
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.macTextView.setText(String.format("MAC: %s (%,2fm)",beacon.getMacAddress(),Utils.computeAccuracy(beacon)));
		holder.majorTextView.setText("Major: " + beacon.getMajor());
		holder.minorTextView.setText("Minor: " + beacon.getMinor());
		holder.measuredPowerTextView.setText("MPower: " + beacon.getMeasuredPower());
		holder.rssiTextView.setText("RSSI: " + beacon.getRssi());
		
		beaconLink beaconLocal = null;
		for (beaconLink _beaconlocal:beaconlinks) {
			if (_beaconlocal.getMinor().equals(String.valueOf(beacon.getMinor()))) {
				beaconLocal = _beaconlocal;
				break;
			}
		}
		if (beaconLocal != null) {
			holder.modelImageView.setText("Image : " + beaconLocal.getModelImage());
			holder.modelNameView.setText("ModelName : " + beaconLocal.getModelName());
			holder.modelYearView.setText("Model Year : " + beaconLocal.getModelYear());
			holder.modelPriceView.setText("Price : " + beaconLocal.getModelPrice());
			holder.modelLocationView.setText("Location" + beaconLocal.getModelLocation());
			holder.targeturlView.setText("TargetURl :" + beaconLocal.getTargetUrl());
		}
	}

	private View inflateIfRequired(View view, int position, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if (view == null)
		{
		  view = inflater.inflate(R.layout.device_item, null);
		  view.setTag(new ViewHolder(view));
		}
		
		return view;
	}
	
	static class ViewHolder {
		final TextView macTextView;
		final TextView majorTextView;
		final TextView minorTextView;
		final TextView measuredPowerTextView;
		final TextView rssiTextView;
		
		final TextView modelImageView;
		final TextView modelNameView;
		final TextView modelYearView;
		final TextView modelPriceView;
		final TextView modelLocationView;
		final TextView targeturlView;
		
		ViewHolder(View view) {
			  macTextView = (TextView) view.findViewWithTag("mac");
			  majorTextView = (TextView) view.findViewWithTag("major");
			  minorTextView = (TextView) view.findViewWithTag("minor");
			  measuredPowerTextView = (TextView) view.findViewWithTag("mpower");
			  rssiTextView = (TextView) view.findViewWithTag("rssi");
			  
			  modelImageView = (TextView) view.findViewWithTag("modelimage");
			  modelNameView = (TextView) view.findViewWithTag("modelname");
			  modelYearView = (TextView) view.findViewWithTag("modelyear");
			  modelPriceView = (TextView) view.findViewWithTag("modelprice");
			  modelLocationView = (TextView) view.findViewWithTag("modellocation");
			  
			  targeturlView = (TextView) view.findViewWithTag("targeturl");
			  
			  
		      
		}
	}
}
