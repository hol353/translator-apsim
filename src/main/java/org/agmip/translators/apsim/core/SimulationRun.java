package org.agmip.translators.apsim.core;

import java.text.ParseException;
import java.util.Calendar;
import org.agmip.translators.apsim.events.Event;
import org.agmip.translators.apsim.events.Planting;
import org.agmip.translators.apsim.util.Converter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author Dean Holzworth, CSIRO
 * @author Ioannis N. Athanasiadis, DUTh
 * @since Jul 13, 2012
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimulationRun {
    
    // Initial condition
    @JsonProperty("initial_condition")
    private InitialCondition initialCondition;
    public InitialCondition getInitialCondition() { return initialCondition; }
    
    // weather
    private Weather weather;
    public Weather getWeather() { return weather; }
    
    // management.
    private Management management;
    public Management getManagement() { return management; }
    
    // soil
    private Soil soil;
    public Soil getSoil() { return soil; }

    // experimentName
    @JsonProperty("exname")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
    private String experimentName = "default";
    public String getExperimentName() { return experimentName; }
    
    // latitude
    @JsonProperty("fl_lat")
    private String latitude = "?";
    public String getLatitude() { return latitude; }
    
    // longitude
    @JsonProperty("fl_long")
    private String longitude = "?";
    public String getLongitude() { return longitude; }

    // log
    private String log;
    public String getLog() { return log; }
    
    // startDate
    public String getStartDate() {
        if (management != null) {
            for (Event event : management.getEvents()) {
                if (event instanceof Planting) {
                    return event.getDate();
                }
            }
            return "?";
        } else {
            return "?";
        }
    }

    // endDate
    public String getEndDate() throws ParseException {
        if ("?".equals(getStartDate())) 
            return "?";
        Calendar endDate = Converter.toCalendar(getStartDate());
        endDate.add(Calendar.YEAR, 1);
        return Converter.toApsimDateString(endDate);
    }

    
    
    
    
    
    
    
    
    
    
    // initialise the SimulationRun instance.
    public void initialise() throws Exception {
        log = "";
        
        // Check the start and end date.
        if ("?".equals(getStartDate()))
           log += "  * Clock ERROR: Missing a simulation start date.\r\n";
        else
           log += "  * Clock ASSUMPTION: end date assumed to be one year after start date.\r\n";

        // Check the weather latitude.
        if (weather == null)
            log += "  * Met ERROR: Missing weather data.\r\n";
        else if ("?".equals(weather.getLatitude())) {
            if ("?".equals(latitude))
                log += "  * Met ERROR: No latitude found in weather data or experiment.\r\n";
            else {
                log += "  * Met ASSUMPTION: No latitude found in weather data. Using experiment latitude instead.\r\n";
                weather.setLatitude(latitude);
            }
        }
        log += "  * Met ERROR: No TAV and AMP have been specified in met file.\r\n";
        
        // Check the soil
        if (soil == null)
            log += "  * Soil: No soil found in AgMIP dataset.\r\n";
        else {
            soil.initialise();
            log += soil.getLog();
        }        
        
        if (initialCondition == null) {
            initialCondition = new InitialCondition();
            log += "  * Initial conditions ERROR: Missing initial conditions (NO3, NH4, SW, SurfaceOM.residueWeight)\r\n";
        }
        if (soil != null) {
            initialCondition.initialise(soil.getLayers());
            log += initialCondition.getLog();
        }
        
        if (management == null)
            log += "  * Operations ERROR: Missing all management events.\r\n";
        else {
            management.initialise();
            log += management.getLog();
        }
    }
}
