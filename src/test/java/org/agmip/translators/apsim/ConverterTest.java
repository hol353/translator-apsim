package org.agmip.translators.apsim;

import java.io.File;
import java.net.URL;
import java.util.Scanner;
import junit.framework.TestCase;
import org.agmip.translators.apsim.core.SimulationRun;
import org.agmip.translators.apsim.util.Converter;
import org.agmip.util.JSONAdapter;
import org.agmip.util.MapUtil;
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
        String newJson = (JSONAdapter.toJSON(MapUtil.decompressAll(JSONAdapter.fromJSON(json))));
        sim = mapper.readValue(newJson, SimulationRun.class);
    }

    private void loadJSONFromFile(String FileName) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String json = new Scanner(new File(FileName), "UTF-8").useDelimiter("\\A").next();
        String newJson = (JSONAdapter.toJSON(MapUtil.decompressAll(JSONAdapter.fromJSON(json))));
        sim = mapper.readValue(newJson, SimulationRun.class);
    }

    @Test
    public void testReadJSONFile() throws Exception {
        loadJSONFromResource("/simulation.json");
        assertEquals("WP_IND", sim.getSoil().getName());
        assertEquals("22/11/1984", sim.getManagement().getEvents().get(0).getDate());
        assertEquals("0.11", sim.getSoil().getLayers()[0].getLowerLimit());
    }


    @Test
    public void testWriteAPSIMFile() throws Exception {
        loadJSONFromResource("/simulation.json");
        long ping = System.currentTimeMillis();
        try {
                Converter.generateAPSIMFile(outputPath, sim);
        } catch (Exception e) {
                assertTrue(e.toString(),false);
        }
        assertTrue("APSIM file generated in " +(System.currentTimeMillis()-ping) +" ms", true);

    }

    @Test
    public void testWriteAPSIMFile2() throws Exception {
        loadJSONFromFile("C:\\Users\\hol353\\Work\\AgMIP\\json-translation-samples\\ufga8201_multi.json");
        long ping = System.currentTimeMillis();
        try {
                Converter.generateAPSIMFile(outputPath, sim);
        } catch (Exception e) {
                assertTrue(e.toString(),false);
        }
        assertTrue("APSIM file generated in " +(System.currentTimeMillis()-ping) +" ms", true);

    }    

    @Test
    public void testWriteMETFile() throws Exception {
        loadJSONFromResource("/simulation.json");

        long ping = System.currentTimeMillis();
        try {
                Converter.generateMetFile(outputPath, sim);
        } catch (Exception e) {
                assertTrue(e.toString(),false);
        }
        assertTrue("MET file generated in " +(System.currentTimeMillis()-ping) +" ms", true);
    }


}
