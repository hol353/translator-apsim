package org.agmip.translators.apsim.weather;

import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
	@JsonProperty("wsta_site") 	
	public String shortName;
	
	@JsonProperty("wsta_insi") 
	public String longName;
	
	@JsonProperty("wsta_lat")	
	public String latitude;
	
	@JsonProperty("wsta_long") 
	public String longitude;
	
	@JsonProperty("tav") 
	public String averageTemperature;

	@JsonProperty("dailyWeather") 
	Set<Record> records = new HashSet<Record>();
		
	
}

