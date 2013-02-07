package org.agmip.translators.apsim.core;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.agmip.translators.apsim.util.DateSerializer;
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
    public static final SimpleDateFormat apsimWeather = new SimpleDateFormat("yyyy D");

    // date
    @JsonProperty("w_date")
    @JsonDeserialize(using=DailyWeatherDeserializer.class)
    @JsonSerialize(using=DateSerializer.class,include=JsonSerialize.Inclusion.NON_DEFAULT)
    private String date ="1000/01/01";
    public String getDate() { return date; }
    public void setDate(String value) {date = value;}

    // solarRadiation
    @JsonProperty("srad")
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    private String solarRadiation="?";
    public String getSolarRadiation() { return solarRadiation; }
    public void setSolarRadiation(String value) {solarRadiation = value;}
    
    // maxTemperature
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("tmax")
    private String maxTemperature="?";
    public String getMaxTemperature() { return maxTemperature; }
    public void setMaxTemperature(String value) {maxTemperature = value;}
    
    // minTemperature
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("tmin")
    private String minTemperature="?";
    public String getMinTemperature() { return minTemperature; }
    public void setMinTemperature(String value) {minTemperature = value;}
    
    // rainfall
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("rain")
    private String rainfall="?";
    public String getRainfall() { return rainfall; }
    public void setRainfall(String value) {rainfall = value;}
    
    // windSpeed
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("wind")
    private String windSpeed="?";
    public String getWindSpeed() { return windSpeed; }
    
    // dewPoint
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("dewp")
    private String dewPoint="?";
    public String getDewPoint() { return dewPoint; }
    
    // vaporPressure
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("vprs")
    private String vaporPressure="?";
    public String getVaporPressure() { return vaporPressure; }

    // relativeHumidity
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("rhum")
    private String relativeHumidity="?";
    public String getRelativeHumidity() { return relativeHumidity; }

    // Needed for Jackson
    public DailyWeather() {}
    
    
    
    
    

    public static class DailyWeatherDeserializer extends JsonDeserializer<String> {			 

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
    
    
    
    public static class DailyWeatherSerializer extends JsonSerializer<String> {

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
