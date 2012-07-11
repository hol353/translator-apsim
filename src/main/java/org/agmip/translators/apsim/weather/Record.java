package org.agmip.translators.apsim.weather;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Record {
	
	
	@JsonProperty("w_date")
	String date ="?";
	
	@JsonProperty("srad")	
	String solarRadiation="?"; //radn
	
	@JsonProperty("tmax")
	String maxTemperature="?"; //maxt

	@JsonProperty("tmin")
	String minTemperature="?"; //mint

	@JsonProperty("rain")
	String rainfall="?";

	@JsonProperty("wind")
	String windSpeed="?";

	@JsonProperty("dewp")
	String dewPoint="?";
	
	@JsonProperty("vprs")
	String vaporPressure="?"; //vers

	@JsonProperty("rhum")
	String relativeHumidity="?"; //rh




}
