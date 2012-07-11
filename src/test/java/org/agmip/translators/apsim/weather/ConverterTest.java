package org.agmip.translators.apsim.weather;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.net.URL;

import java.util.Set;
import java.util.Scanner;

import junit.framework.TestCase;

import org.agmip.translators.apsim.weather.Converter;
import org.agmip.translators.apsim.weather.Record;
import org.agmip.translators.apsim.weather.Weather;
import org.agmip.translators.apsim.weather.Weather;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.junit.Test;

public class ConverterTest extends TestCase {
	Weather w = new Weather();

	public void setUp() throws Exception {
		w.averageTemperature = "11";
		w.longName = "Station Name, Somewhere in the Galaxy";
		w.shortName = "STA0001";
		w.latitude = "-11.2345";
		w.longitude = "-11.2345";

		Set<Record> recs = w.records;

		{
			Record r = new Record();
			r.date = "2012-01-01";
			r.solarRadiation = "0"; // radn
			r.maxTemperature = "13"; // maxt
			r.minTemperature = "-12"; // mint
			r.rainfall = "0.2";
			recs.add(r);
		}

		{
			Record r = new Record();
			r.date = "2012-01-02";
			r.solarRadiation = "0.3"; // radn
			r.maxTemperature = "18"; // maxt
			r.minTemperature = "0.2"; // mint
			r.rainfall = "0.2";
			recs.add(r);
		}

	}

	@Test
	public void testGenerateWeatherFiles() {
		try {
			Converter.generateWeatherFiles(new File("src/test/resources/"), w);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetYear() {
		assertEquals("2012", Converter.GetYear("2012-12-12"));
		assertEquals("2000", Converter.GetYear("2000-2-1"));
	}

	@Test
	public void testGetDay() {
		assertEquals("1", Converter.GetDay("2012-1-1"));
		assertEquals("32", Converter.GetDay("2012-2-1"));
	}

	@Test
	public void testJSON() {

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		try {
			PrintWriter out = new PrintWriter(new FileWriter(new File(
					"src/test/resources/test01.json")));
			out.print(mapper.writeValueAsString(w));
			out.close();

		} catch (Exception e) {
			assertTrue(false);
		}

	}
	
	@Test
	public void testJSONReader()  throws Exception{

		ObjectMapper mapper = new ObjectMapper();
		Weather value = mapper.readValue(new File("src/test/resources/test02.json"), Weather.class);
			assertEquals("-11.2345",value.latitude);


	}



}
