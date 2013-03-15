package org.agmip.translators.apsim;

import static org.junit.Assert.assertEquals;

import org.agmip.translators.apsim.core.Weather;
import org.agmip.translators.apsim.readers.WeatherReader;
import org.junit.Test;

public class WeatherReaderTest {

	@Test
	public void testReader() throws Exception{
		WeatherReader	weatherReader = new WeatherReader();
		Weather weather = weatherReader.read("src/test/resources/apsim/Yucheng.met");
		assertEquals("36.68", weather.getLatitude());
		assertEquals("Yucheng",weather.getName());
		assertEquals(1189, weather.getRecords().size());
		//assertEquals("20021102", weather.getRecords().get(1).getDate());
		assertEquals("14.7", weather.getRecords().get(1).getMinTemperature());
		assertEquals("23.1", weather.getRecords().get(1).getMaxTemperature());
		
	}

	
	
}
