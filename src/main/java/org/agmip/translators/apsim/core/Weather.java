package org.agmip.translators.apsim.core;

import java.util.ArrayList;
import java.util.List;

import org.agmip.translators.apsim.util.Util;
import org.codehaus.jackson.annotate.JsonIgnore;
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
    
    // id
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("wst_id")
    private String id="?";
    public String getId() { return id; }
    public void setId(String value) {id = value;}

    // name
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("wst_name")
    private String name="?";
    public String getName() { 
        if ("?".equals(name))
            return id;
        else
            return name; 
    }
    public void setName(String value) {name = value;}
    
    // site
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("wst_site")
    private String site="?";
    public String getSite() { return site; }
 
    // dist
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("wst_dist")
    private String dist="?";
    public String getDist() { return dist; }

    // elevation
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("wst_elev")
    private double elevation = Util.missingValue;
    public double getElevation() { return elevation; }
    
    // longName    
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("wst_source") 
    private String source = "?";
    public String getSource() { return source; }
    
    // latitude
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("wst_lat")	
    private double latitude = Util.missingValue;
    public double getLatitude() { return latitude; }
    public void setLatitude(double value) { latitude = value; }
    
    // longitude
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("wst_long") 
    private double longitude = Util.missingValue;
    public double getLongitude() { return longitude; }
	public void setLongitude(double value) {longitude = value;}
		    
    // averageTemperature
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("tav") 
    private double tav = Util.missingValue;
    public double getTav() { return tav; }
    public void setTav(double value) {tav = value;}

    // AMP
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("tamp") 
    private double amp = Util.missingValue;
    public double getAmp() { return amp; }
    public void setAmp(double value) {amp = value;}

    // CO2
    @JsonIgnore 
    public double getCo2() { 
    	if (CO2Y == Util.missingValue)
    		return ACO2;
    	else
    		return CO2Y; 
    	}
     
    // CO2
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("ACO2") 
    private double ACO2 = Util.missingValue;
    
    // CO2
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("CO2Y") 
    private double CO2Y = Util.missingValue;
    
    // records
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
    @JsonProperty("dailyWeather") 
    private List<DailyWeather> records = new ArrayList<DailyWeather>();
    public List<DailyWeather> getRecords() { return records; }
    public void setRecords(List<DailyWeather> value) { records = value; }

    
    
    // default constructor - Needed for Jackson
    public Weather() {}

	
    
    /* 
     * I assume that two weathers are the same if they have the same ID.
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other) {
        if (other instanceof Weather) {
        	Weather otherWeather = (Weather) other;
            return id.equals(otherWeather.id);
        }
        return false;
    }
        
    

	
}

