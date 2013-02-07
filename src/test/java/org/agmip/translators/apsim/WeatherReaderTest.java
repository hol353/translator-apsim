package org.agmip.translators.apsim;

import static org.junit.Assert.assertEquals;

import org.agmip.translators.apsim.core.Weather;
import org.agmip.translators.apsim.readers.WeatherReader;
import org.junit.Test;

public class WeatherReaderTest {

	@Test
	public void testReader() throws Exception{
		WeatherReader	weatherReader = new WeatherReader();
		Weather weather = weatherReader.read("src/test/resources/apsim/APS14.met");
		assertEquals("-27.31", weather.getLatitude());
		assertEquals("APS14",weather.getName());
		assertEquals(178, weather.getRecords().size());
		assertEquals("19930705", weather.getRecords().get(1).getDate());
		assertEquals("10.1", weather.getRecords().get(1).getMinTemperature());
		assertEquals("20.5", weather.getRecords().get(1).getMaxTemperature());
		
	}

	
	
}
