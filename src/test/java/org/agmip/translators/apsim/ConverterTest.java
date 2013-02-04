package org.agmip.translators.apsim;

import java.io.File;
import java.net.URL;
import java.util.Scanner;
import junit.framework.TestCase;

import org.agmip.translators.apsim.core.SimulationCollection;
import org.agmip.translators.apsim.core.SimulationRun;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 */
public class ConverterTest extends TestCase{

	SimulationRun sim = new SimulationRun();
    File outputPath = new File("src/test/resources/gen");

    private void loadJSONFromResource(String FileName) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        URL resource = this.getClass().getResource(FileName);
        String json = new Scanner(new File(resource.getPath()), "UTF-8").useDelimiter("\\A").next();
//        String newJson = (JSONAdapter.toJSON(MapUtil.decompressAll(JSONAdapter.fromJSON(json))));
        SimulationCollection collection = mapper.readValue(json, SimulationCollection.class);
        collection.initialize();
        sim = collection.getSimulationExperiments().firstElement();
    }



    @Test
    public void testReadJSONFile() throws Exception {
        loadJSONFromResource("/json-translation-samples/mach_fast.json");

       
        assertEquals("0.10", sim.getSoil().getSalb());
        assertEquals("18/03/1981", sim.getManagement().getEvents().get(0).getDate());
        assertEquals("0.12", sim.getSoil().getLayers()[0].getLowerLimit());
    }

    

 

}
