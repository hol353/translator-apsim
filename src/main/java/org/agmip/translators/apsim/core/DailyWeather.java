package org.agmip.translators.apsim.core;

import org.agmip.translators.apsim.util.Converter;
import org.agmip.translators.apsim.util.DateDeserializer;
import org.agmip.translators.apsim.util.DateSerializer;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyWeather {
	
	
	@JsonProperty("w_date")
	@JsonDeserialize(using=DateDeserializer.class)
	@JsonSerialize(using=DateSerializer.class)
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSolarRadiation() {
		return solarRadiation;
	}

	public void setSolarRadiation(String solarRadiation) {
		this.solarRadiation = solarRadiation;
	}

	public String getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(String maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public String getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(String minTemperature) {
		this.minTemperature = minTemperature;
	}

	public String getRainfall() {
		return rainfall;
	}

	public void setRainfall(String rainfall) {
		this.rainfall = rainfall;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	public String getDewPoint() {
		return dewPoint;
	}

	public void setDewPoint(String dewPoint) {
		this.dewPoint = dewPoint;
	}

	public String getVaporPressure() {
		return vaporPressure;
	}

	public void setVaporPressure(String vaporPressure) {
		this.vaporPressure = vaporPressure;
	}

	public String getRelativeHumidity() {
		return relativeHumidity;
	}

	public void setRelativeHumidity(String relativeHumidity) {
		this.relativeHumidity = relativeHumidity;
	}


	


}
