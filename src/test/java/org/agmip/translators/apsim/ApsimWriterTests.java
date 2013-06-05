package org.agmip.translators.apsim;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import junit.framework.TestCase;

import org.agmip.translators.apsim.core.ACE;
import org.agmip.translators.apsim.core.Simulation;
import org.junit.Test;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 */
public class ApsimWriterTests extends TestCase{

    File outputPath = new File("src/test/resources/gen");

    private ACE fileResourceToACE(String FileName) throws Exception{
        URL resource = this.getClass().getResource(FileName);
        String json = new Scanner(new File(resource.getPath()), "UTF-8").useDelimiter("\\A").next();
        return ApsimWriter.jsonToACE(json);
    }



    @Test
    public void testReadJSONFile() throws Exception {
    	ACE ace = fileResourceToACE("/json-samples/MaizeIncomplete.json");

        Simulation sim = ace.getExperiments().iterator().next();
        assertEquals(0.10, sim.getSoil().getSalb());
        assertEquals("18/03/1981", sim.getManagement().getEvents().get(0).getDate());
        assertEquals(0.12, sim.getSoil().getLayers()[0].getLowerLimit());
        assertEquals("Maize", sim.getManagement().plantingCropName());
        assertEquals("Maize", sim.getInitialCondition().getResidueType());
    }

    @Test
    public void testWriteAPSIMFile() throws Exception {
    	ACE ace = fileResourceToACE("/json-samples/MaizeIncomplete.json");

        ArrayList<String> files = new ArrayList<String>();
        ApsimWriter.generateAPSIMFile(outputPath, ace, files);
        ApsimWriter.generateMetFiles(outputPath, ace, files);
        
        assertEquals(files.size(), 2);
        assertEquals(files.get(0), "AgMip.apsim");
        assertEquals(files.get(1), "MK10.met");
    }  

 

}
