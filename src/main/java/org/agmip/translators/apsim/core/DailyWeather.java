package org.agmip.translators.apsim.core;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyWeather {
	
	
	@JsonProperty("w_date")
	public	String date ="?";
	
	@JsonProperty("srad")	
	public String solarRadiation="?"; //radn
	
	@JsonProperty("tmax")
	public String maxTemperature="?"; //maxt

	@JsonProperty("tmin")
	public String minTemperature="?"; //mint

	@JsonProperty("rain")
	public String rainfall="?";

	@JsonProperty("wind")
	public String windSpeed="?";

	@JsonProperty("dewp")
	public	String dewPoint="?";
	
	@JsonProperty("vprs")
	public	String vaporPressure="?"; //vers

	@JsonProperty("rhum")
	public	String relativeHumidity="?"; //rh




}
