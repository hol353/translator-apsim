package org.agmip.translators.apsim.management;

import java.io.File;

import org.agmip.translators.apsim.core.SimulationRun;
import org.agmip.translators.apsim.util.Converter;
import org.codehaus.jackson.map.ObjectMapper;



public class TestEvent {
	
	public static void main(String...args) throws Exception{
		 ObjectMapper mapper = new ObjectMapper();  
//	    mapper.setPropertyNamingStrategy(new CamelCaseNamingStrategy());  
		 SimulationRun sim =   mapper.readValue(new File("src/test/resources/simulation.json"), SimulationRun.class); 
	    
		    System.out.println(mapper.writeValueAsString(sim));  
		    System.out.println(sim.soil.getName());
		    
		    
		    Converter.generateAPSIMFile(new File("src/test/resources"), sim);
		    Converter.generateWeatherFiles(new File("src/test/resources"), sim.weather);

		    
	}
	
//	testOutpy(){
//		
//		fromJSON("example");
//	}

}
