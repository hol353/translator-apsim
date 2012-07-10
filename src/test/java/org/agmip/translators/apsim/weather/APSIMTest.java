package org.agmip.translators.apsim.weather;


import java.io.File;
import java.io.IOException;

import java.util.Set;

import junit.framework.TestCase;

import org.agmip.translators.apsim.weather.APSIM;
import org.agmip.translators.apsim.weather.Record;
import org.agmip.translators.apsim.weather.Station;
import org.agmip.translators.apsim.weather.Weather;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.junit.Test;

public class APSIMTest extends TestCase{
	Weather w= new Weather();
	
	public void setUp() throws Exception{
		 Station ws1 = new Station();
		 w.getData().add(ws1);
		 ws1.setTav("11");
		 ws1.setWsta_site("Station Name, Somewhere in the Galaxy");
		 ws1.setWsta_insi("STA0001");
		 ws1.setWsta_lat("-11.2345");
		 ws1.setWsta_long("-11.2345");
		 
		 Set<Record> recs = ws1.getRecords();
		 
		 
		 {
		 Record r = new Record();
		 r.w_date = "2012-01-02";
		 r.srad  = "0"; //radn
		 r.tmax = "13"; //maxt
		 r.tmin = "-12"; //mint
		 r.rain ="0.2";
		 recs.add(r);
		 }
		 
		 {
		 Record r = new Record();
		 r.w_date = "2012-01-01";
		 r.srad  = "0.3"; //radn
		 r.tmax = "18"; //maxt
		 r.tmin = "0.2"; //mint
		 r.rain ="0.2";
		 recs.add(r);
		 }
	
	
	
		
	}

	@Test
	public void testGenerateWeatherFiles()  {
		try {
			APSIM.generateWeatherFiles(new File("src/test/resources/"), w);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetYear() {
		assertEquals("2012", APSIM.GetYear("2012-12-12"));
		assertEquals("2000", APSIM.GetYear("2000-2-1"));
	}

	@Test
	public void testGetDay() {
		assertEquals("1",APSIM.GetDay("2012-1-1"));
		assertEquals("32",APSIM.GetDay("2012-2-1"));
	}
	
	
	@Test
	public void testJSON(){
	 
	 ObjectMapper mapper = new ObjectMapper();
	 mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
     try {
		System.out.println( mapper.writeValueAsString(w) );
	} catch (Exception e) {
			assertTrue(false);
	}
		
	}

}
