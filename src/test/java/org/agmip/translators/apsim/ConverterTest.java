package org.agmip.translators.apsim;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

import junit.framework.TestCase;

import org.agmip.translators.apsim.core.SimulationRun;
import org.agmip.translators.apsim.util.Converter;
import org.agmip.util.JSONAdapter;
import org.agmip.util.MapUtil;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 */
public class ConverterTest extends TestCase{
	
	SimulationRun sim = new SimulationRun();
	File outputPath = new File("src/test/resources/gen");
	public void setUp() throws Exception{
//		FileUtils.deleteDirectory(outputPath);
		
		ObjectMapper mapper = new ObjectMapper();
                File F = new File("src/test/resources/simulation.json");     
                URL resource = this.getClass().getResource("/simulation.json");
                String json = new Scanner(new File(resource.getPath()), "UTF-8").useDelimiter("\\A").next();
                String newJson = (JSONAdapter.toJSON(MapUtil.decompressAll(JSONAdapter.fromJSON(json))));
		 sim = mapper.readValue(newJson, SimulationRun.class);
	}
	
	@Test
	public void testReadJSONFile() {
		assertEquals("Millhopper Fine Sand", sim.soil.getName());
		assertEquals("26/02/1982", sim.getManagement().getEvents().get(0).getDate());
		assertEquals(0.026, sim.getSoil().getLayers()[0].getLowerLimit());
	}
	
	@Test
	public void testWriteAPSIMFile() {
		long ping = System.currentTimeMillis();
		try {
			Converter.generateAPSIMFile(outputPath, sim);
		} catch (Exception e) {
			assertTrue(e.toString(),false);
		}
		assertTrue("APSIM file generated in " +(System.currentTimeMillis()-ping) +" ms", true);

	}
	

	@Test
	public void testWriteMETFile() {
		long ping = System.currentTimeMillis();
		try {
			Converter.generateMetFile(outputPath, sim);
		} catch (Exception e) {
			assertTrue(e.toString(),false);
		}
		assertTrue("MET file generated in " +(System.currentTimeMillis()-ping) +" ms", true);

	}


		
		

}
