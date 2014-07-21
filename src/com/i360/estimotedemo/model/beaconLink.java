package com.i360.estimotedemo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class beaconLink extends ModelBase implements Parcelable {

	private String minor;
	private String modelimage;
	private String modelname;
	private String modelyear;
	private String modelprice;
	private String location;
	private String targeturl;
	
	public void setMinor(String minor)
	{
		this.minor = minor;
	}
	
	public String getMinor()
	{
		return this.minor;
	}
	
	public void setModelImage(String modelImage) {
		this.modelimage = modelImage;
	}
	
	public String getModelImage() {
		return this.modelimage;
	}
	
	
	public void setModelName(String modelName) {
		this.modelname = modelName;
	}
	
	public String getModelName()
	{
		return this.modelname;
	}
	
	public void setTargetUrl(String targetUrl) {
		this.targeturl = targetUrl;
	}
	
	public String getTargetUrl()
	{
		return this.targeturl;
	}
	
	public void setModelYear(String modelYear) {
		this.modelyear = modelYear;
	}
	
	public String getModelYear() {
		return this.modelyear;
	}
	
	public void setModelPrice(String modelPrice) {
		this.modelprice = modelPrice;
	}
	
	public String getModelPrice() {
		return this.modelprice;
	}
	
	public void setModelLocation(String location) {
		this.location = location;
	}
	
	public String getModelLocation() {
		return this.location;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[] {
				this.minor,
				this.modelimage,
				this.modelname,
				this.modelyear,
				this.modelprice,
				this.location,
				this.targeturl});
		// TODO Auto-generated method stub
		
	}
	
	public beaconLink() {
	}
	
	public beaconLink(Parcel source) {
		String[] data = new String[7];
		source.readStringArray(data);
		minor = data[0];
		modelimage = data[1];
		modelname = data[2];
		modelyear = data[3];
		modelprice = data[4];
		location = data[5];
		targeturl= data[6];
	}
	
	public static final Parcelable.Creator<beaconLink> CREATOR = new Parcelable.Creator<beaconLink>() {

		@Override
		public beaconLink createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new beaconLink(source);
		}

		@Override
		public beaconLink[] newArray(int size) {
			// TODO Auto-generated method stub
			return new beaconLink[size];
		}
	};
	
	
}
