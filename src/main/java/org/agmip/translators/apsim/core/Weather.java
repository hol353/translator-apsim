package org.agmip.translators.apsim.core;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @since Jul 13, 2012
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    
    // shortName
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("wst_name")
    private String shortName="default";
    public String getShortName() { return shortName; }
    
    // longName    
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("wst_source") 
    private String longName = "?";
    public String getLongName() { return longName; }
    
    // latitude
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("wst_lat")	
    private String latitude = "?";
    public String getLatitude() { return latitude; }
    public void setLatitude(String value) { latitude = value; }
    
    // longitude
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("wst_long") 
    private String longitude = "?";
    public String getLongitude() { return longitude; }
    
    // averageTemperature
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("tav") 
    public String averageTemperature = "?";
    private String getAverageTemperature() { return averageTemperature; }

    // CO2
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("ambient_CO2_conc") 
    private String co2;
    public String getCo2() { return co2; }
    
    
    // records
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
    @JsonProperty("dailyWeather") 
    private List<DailyWeather> records = new ArrayList<DailyWeather>();
    public List<DailyWeather> getRecords() { return records; }

	
    

	
}

