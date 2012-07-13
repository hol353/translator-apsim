package org.agmip.translators.apsim;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import junit.framework.TestCase;

import org.agmip.translators.apsim.core.DailyWeather;
import org.agmip.translators.apsim.core.Weather;
import org.agmip.translators.apsim.util.Converter;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.junit.Test;

public class WeatherTest extends TestCase {
	Weather w = new Weather();

	public void setUp() throws Exception {
		w.averageTemperature = "11";
		w.longName = "Station Name, Somewhere in the Galaxy";
		w.shortName = "STA0001";
		w.latitude = "-11.2345";
		w.longitude = "-11.2345";

		List<DailyWeather> recs = w.records;

		{
			DailyWeather r = new DailyWeather();
			r.date = "2012/01/01";
			r.solarRadiation = "0"; // radn
			r.maxTemperature = "13"; // maxt
			r.minTemperature = "-12"; // mint
			r.rainfall = "0.2";
			recs.add(r);
		}

		{
			DailyWeather r = new DailyWeather();
			r.date = "2012/01/02";
			r.solarRadiation = "0.3"; // radn
			r.maxTemperature = "18"; // maxt
			r.minTemperature = "0.2"; // mint
			r.rainfall = "0.2";
			recs.add(r);
		}

	}

	public void testDate() throws Exception{
		assertEquals("20120130",DailyWeather.agmip.format(DailyWeather.apsimWeather.parse("2012/01/30")));
	}


	@Test
	public void testGetYear() throws Exception {
		assertEquals("2012", Converter.GetYear("12/12/2012"));
		assertEquals("2000", Converter.GetYear("21/09/2000"));
	}

	@Test
	public void testGetDay() throws Exception{
		assertEquals("1", Converter.GetDay("01/01/2012"));
		assertEquals("32", Converter.GetDay("01/02/2012"));
	}

	@Test
	public void testJSON() {

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		try {
			PrintWriter out = new PrintWriter(new FileWriter(new File("src/test/resources/gen/test01.json")));
			out.print(mapper.writeValueAsString(w));
			out.close();
			assertTrue("Wrote json file test01",true);
		} catch (Exception e) {
			assertTrue(false);
		}

	}
	
	@Test
	public void testJSONReader()  throws Exception{

		ObjectMapper mapper = new ObjectMapper();
		Weather value = mapper.readValue(new File("src/test/resources/weather.json"), Weather.class);
			assertEquals("-11.2345",value.latitude);

	}



}
