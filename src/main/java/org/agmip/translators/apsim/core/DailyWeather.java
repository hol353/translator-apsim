package org.agmip.translators.apsim.core;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @since Jul 13, 2012
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyWeather {
	
	public static final SimpleDateFormat agmip = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat apsimWeather = new SimpleDateFormat("yyyy/MM/dd");
	

	
	
	@JsonProperty("w_date")
	@JsonDeserialize(using=DailyWeatherDeserializer.class)
	@JsonSerialize(using=DailyWeatherSerializer.class,include=JsonSerialize.Inclusion.NON_DEFAULT)
	public	String date ="01/01/1000";
	
	@JsonProperty("srad")
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	public String solarRadiation="?"; //radn
	
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	@JsonProperty("tmax")
	public String maxTemperature="?"; //maxt

	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	@JsonProperty("tmin")
	public String minTemperature="?"; //mint

	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	@JsonProperty("rain")
	public String rainfall="?";

	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	@JsonProperty("wind")
	public String windSpeed="?";

	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	@JsonProperty("dewp")
	public	String dewPoint="?";
	
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	@JsonProperty("vprs")
	public	String vaporPressure="?"; //vers

	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
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


	static class DailyWeatherDeserializer extends JsonDeserializer<String>{			 
		 
	    @Override
	    public String deserialize(JsonParser jp, DeserializationContext ctxt)
	            throws IOException, JsonProcessingException {
					try {
						Date date = agmip.parse(jp.getText());
						return apsimWeather.format(date);
					} catch (ParseException e) {
						throw new IOException(e);
					}
			
	    	     
	    }
	       

}
	class DailyWeatherSerializer extends JsonSerializer<String>{
		
	    @Override
	    public void serialize(String date, JsonGenerator gen, SerializerProvider provider)
	            throws IOException, JsonProcessingException {
	 
	        String formattedDate="";
			try {
				formattedDate = agmip.format(apsimWeather.parse(date));
			} catch (ParseException e) {
				throw new IOException(e);
			}
	 	        		
	        gen.writeString(formattedDate);
	    }

	}
}
