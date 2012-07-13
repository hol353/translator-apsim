package org.agmip.translators.apsim.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	@JsonProperty("wsta_site") 	
	public String shortName="default";
	
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	@JsonProperty("wsta_insi") 
	public String longName = "?";

	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	@JsonProperty("wsta_lat")	
	public String latitude = "?";
	
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	@JsonProperty("wsta_long") 
	public String longitude = "?";
	
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	@JsonProperty("tav") 
	public String averageTemperature = "?";

	@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
	@JsonProperty("dailyWeather") 
	public List<DailyWeather> records = new ArrayList<DailyWeather>();

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAverageTemperature() {
		return averageTemperature;
	}

	public void setAverageTemperature(String averageTemperature) {
		this.averageTemperature = averageTemperature;
	}

	public List<DailyWeather> getRecords() {
		return records;
	}

	public void setRecords(List<DailyWeather> records) {
		this.records = records;
	}


	
	
	
}

