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

import org.agmip.translators.apsim.util.DateDeserializer;
import org.agmip.translators.apsim.util.DateSerializer;


/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @since Jul 13, 2012
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyWeather {
	
	public static final SimpleDateFormat agmip = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat apsimWeather = new SimpleDateFormat("yyyy D");
	

	
	
	@JsonProperty("w_date")
	@JsonDeserialize(using=DailyWeatherDeserializer.class)
	@JsonSerialize(using=DateSerializer.class,include=JsonSerialize.Inclusion.NON_DEFAULT)
	public	String date ="1000/01/01";
	
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


	public static class DailyWeatherDeserializer extends JsonDeserializer<String>{			 
		 
	    @Override
	    public String deserialize(JsonParser jp, DeserializationContext ctxt)
	            throws IOException, JsonProcessingException {
					try {
						Date date = agmip.parse(jp.getText());
                        String outputDate = apsimWeather.format(date);
                        if(outputDate.length() == 8) {
                            return outputDate;
                        } else {
                            StringBuffer buf = new StringBuffer(outputDate);
                            buf.insert(5, " ");
                            if (buf.length() == 7) {
                                buf.insert(5, " ");
                            }
                            return buf.toString();
                        }
					} catch (ParseException e) {
						throw new IOException(e);
					}
			
	    	     
	    }
	       

}
	public static class DailyWeatherSerializer extends JsonSerializer<String>{
		
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
