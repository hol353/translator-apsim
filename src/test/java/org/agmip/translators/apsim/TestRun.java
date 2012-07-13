package org.agmip.translators.apsim;

import java.io.File;

import org.agmip.translators.apsim.core.SimulationRun;
import org.agmip.translators.apsim.util.Converter;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 
 * 
 * @author Ioannis N. Athanasiadis
 */
public class TestRun {

	public static void main(String... args) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		// mapper.setPropertyNamingStrategy(new CamelCaseNamingStrategy());
		SimulationRun sim = mapper.readValue(new File(
				"src/test/resources/simulation.json"), SimulationRun.class);

		System.out.println(mapper.writeValueAsString(sim));
		System.out.println(sim.soil.getName());
		System.out.println(sim.getManagement().getEvents().get(0).getDate());
		System.out.println(sim.getSoil().getLayers()[0].getLowerLimit());

		long ping = System.currentTimeMillis();
		Converter.generateAPSIMFile(new File("src/test/resources"), sim);
		System.out.println("Apsim    :" +(System.currentTimeMillis()-ping) +" ms");

		ping = System.currentTimeMillis();
		Converter.generate2(new File("src/test/resources"), sim);
		System.out.println("Weather 2: " +(System.currentTimeMillis()-ping) +" ms");
		
		long pong = System.currentTimeMillis();
		Converter.generateWeatherFiles(new File("src/test/resources"),sim.weather);
		System.out.println("Weather  :" + (System.currentTimeMillis()-pong) +" ms");
		
		

	}

}
