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
    
    // id
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("wst_id")
    private String id="?";
    public String getId() { return id; }

    // name
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("wst_name")
    private String name="?";
    public String getName() { return name; }
        
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
    private String elevation="?";
    public String getElevation() { return elevation; }
    
    // longName    
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("wst_source") 
    private String source = "?";
    public String getSource() { return source; }
    
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
    private String tav = "?";
    public String getTav() { return tav; }

    // AMP
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("tamp") 
    private String amp = "?";
    public String getAmp() { return amp; }
    
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

    // Needed for Jackson
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

