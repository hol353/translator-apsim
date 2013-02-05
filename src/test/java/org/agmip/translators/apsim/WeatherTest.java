package org.agmip.translators.apsim;

import java.io.File;
import junit.framework.TestCase;
import org.agmip.translators.apsim.core.DailyWeather;
import org.agmip.translators.apsim.core.Weather;
import org.agmip.translators.apsim.util.Util;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

public class WeatherTest extends TestCase {
    Weather w;

    public void setUp() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        w = mapper.readValue(new File("src/test/resources/json-samples/weather.json"), Weather.class);
    }

    @Test
    public void testDate() throws Exception{
        assertEquals("20120130",DailyWeather.agmip.format(DailyWeather.apsimWeather.parse("2012  30")));
    }


    @Test
    public void testGetYear() throws Exception {
        assertEquals("2012", Util.GetYear("12/12/2012"));
        assertEquals("2000", Util.GetYear("21/09/2000"));
    }

    @Test
    public void testGetDay() throws Exception{
        assertEquals("1", Util.GetDay("01/01/2012"));
        assertEquals("32", Util.GetDay("01/02/2012"));
    }

}
