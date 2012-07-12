package org.agmip.translators.apsim;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.agmip.core.types.TranslatorOutput;
import org.agmip.translators.apsim.core.SimulationRun;
import org.agmip.translators.apsim.util.Converter;
import org.agmip.util.MapUtil;
import org.agmip.util.MapUtil.BucketEntry;
import static org.agmip.util.JSONAdapter.toJSON;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;




public class ApsimOutput implements TranslatorOutput {
    
    

    
    public void writeFile(String filePath, Map input) {
    	 
    		 File path = new File(filePath);
		 ObjectMapper mapper = new ObjectMapper();  
	     SimulationRun sim;
		try {
			sim = mapper.readValue(toJSON(MapUtil.decompressAll(input)), SimulationRun.class);
		     Converter.generateWeatherFiles(path, sim.weather);
		     Converter.generateAPSIMFile(path, sim);

		} catch (Exception e) {
			e.printStackTrace();
		} 
	     
    	   

          
        
    }
}
