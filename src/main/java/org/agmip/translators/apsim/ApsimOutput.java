package org.agmip.translators.apsim;

import static org.agmip.util.JSONAdapter.toJSON;

import java.io.File;
import java.util.Map;

import org.agmip.core.types.TranslatorOutput;
import org.agmip.translators.apsim.core.SimulationRun;
import org.agmip.translators.apsim.util.Converter;
import org.agmip.util.MapUtil;
import org.codehaus.jackson.map.ObjectMapper;



/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @since Jul 13, 2012
 */

public class ApsimOutput implements TranslatorOutput {

	public void writeFile(String filePath, Map input) {

		File path = new File(filePath);
		ObjectMapper mapper = new ObjectMapper();
		SimulationRun sim;
		try {
			sim = mapper.readValue(toJSON(MapUtil.decompressAll(input)),
					SimulationRun.class);
			Converter.generateMetFile(path, sim);
			Converter.generateAPSIMFile(path, sim);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
