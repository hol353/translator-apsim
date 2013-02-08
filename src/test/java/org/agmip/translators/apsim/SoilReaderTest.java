package org.agmip.translators.apsim;

import static org.junit.Assert.*;

import java.util.List;

import org.agmip.translators.apsim.core.Soil;
import org.agmip.translators.apsim.readers.SoilReader;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;




public class SoilReaderTest {


	@Test
	public void testReader() throws Exception{
		SoilReader	sr = new SoilReader("src/test/resources/apsim/SmallWheatValidation.apsim");
		List<Soil> soils = sr.read();
		assertEquals(2,soils.size());
		assertEquals("Yucheng",soils.get(0).getID());
		assertEquals(10,soils.get(1).getLayers().length);
	}
	
//	@Test
//	public void testReaderBig() throws Exception{
//		SoilReader	sr = new SoilReader("src/test/resources/apsim/WheatValidation.apsim");
//		List<Soil> soils = sr.read();
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.writeValue(System.out, soils);
//		
//	}

}
