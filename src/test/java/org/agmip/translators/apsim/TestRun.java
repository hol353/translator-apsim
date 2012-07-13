package org.agmip.translators.apsim;

import java.io.File;

import junit.framework.TestCase;

import org.agmip.translators.apsim.core.SimulationRun;
import org.agmip.translators.apsim.util.Converter;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 */
public class TestRun extends TestCase{
	
	SimulationRun sim = new SimulationRun();
	public void setUp() throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		 sim = mapper.readValue(new File(
				"src/test/resources/simulation.json"), SimulationRun.class);
	}
	
	@Test
	public void testReadJSONFile() {
		assertEquals("Millhopper Fine Sand", sim.soil.getName());
		assertEquals("26/02/1982", sim.getManagement().getEvents().get(0).getDate());
		assertEquals("0.026", sim.getSoil().getLayers()[0].getLowerLimit());
	}
	
	@Test
	public void testWriteAPSIMFile() {
		long ping = System.currentTimeMillis();
		try {
			Converter.generateAPSIMFile(new File("src/test/resources"), sim);
		} catch (Exception e) {
			assertTrue(e.toString(),false);
		}
		assertTrue("APSIM file generated in " +(System.currentTimeMillis()-ping) +" ms", true);

	}
	

	@Test
	public void testWriteMETFile() {
		long ping = System.currentTimeMillis();
		try {
			Converter.generateMetFile(new File("src/test/resources"), sim);
		} catch (Exception e) {
			assertTrue(e.toString(),false);
		}
		assertTrue("MET file generated in " +(System.currentTimeMillis()-ping) +" ms", true);

	}


		
		

}
