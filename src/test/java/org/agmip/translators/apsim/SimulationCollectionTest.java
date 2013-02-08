package org.agmip.translators.apsim;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.agmip.translators.apsim.core.SimulationCollection;
import org.agmip.translators.apsim.core.SimulationRun;
import org.agmip.translators.apsim.core.Soil;
import org.agmip.translators.apsim.readers.SimulationCollectionReader;
import org.agmip.translators.apsim.readers.SoilReader;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

public class SimulationCollectionTest {
	
	@Test
	public void testReader() throws Exception{
		SimulationCollectionReader	sr = new SimulationCollectionReader("src/test/resources/apsim/SmallWheatValidation.apsim");
		SimulationCollection c = sr.readSim();
		assertEquals(3,c.getExperiments().size());
		assertEquals(1,c.getSoils().size());
		assertEquals(1,c.getWeathers().size());
			
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.writeValue(System.out, c);
		
	}

}
