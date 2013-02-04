package org.agmip.translators.apsim;

import static org.agmip.util.JSONAdapter.toJSON;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.agmip.core.types.TranslatorOutput;
import org.agmip.translators.apsim.core.SimulationCollection;
import org.agmip.translators.apsim.core.SimulationRun;
import org.agmip.translators.apsim.core.Soil;
import org.agmip.translators.apsim.core.Weather;
import org.agmip.translators.apsim.util.Converter;
import org.agmip.util.MapUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @author Chris Villalobos, AgMIP IT
 * @since Jul 13, 2012
 */

public class ApsimOutput implements TranslatorOutput {
	static final int BUFFER = 2048;
	private static final Logger LOG = LoggerFactory.getLogger(ApsimOutput.class);



	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void writeFile(String filePath, Map input) {
		File path = new File(filePath);
		ObjectMapper mapper = new ObjectMapper();
		try {

			String temp = toJSON(MapUtil.decompressAll(input));
			SimulationCollection collection = mapper.readValue(temp,SimulationCollection.class);

			ArrayList<String> files = new ArrayList<String>();

			collection.initialize();
			
			
			for(SimulationRun sim:collection.getSimulationExperiments()){
				
				
				if (sim.getWeather() != null) {
					
					// Support if there is no weather data.
					Converter.generateMetFile(path, sim);
					String baseName;
					if (sim.getExperimentName().equals("default")) {
						baseName = sim.getWeather().getName();
					} else {
						baseName = sim.getExperimentName();
					}
					files.add(baseName+".met");
				}
				if (sim.getLatitude() != null) {
					// Support for weather file only.
					Converter.generateAPSIMFile(path, sim);
					files.add(sim.getExperimentName()+".apsim");
				}
			}

			BufferedInputStream origin = null;

			if (files.size() > 1) {

				File zipfile = new File(path, collection.getSimulationExperiments().firstElement().getExperimentName() + "_apsim.zip");

				if (zipfile.exists())
					zipfile.delete();

				FileOutputStream dest = new FileOutputStream(zipfile);
				ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
						dest));
				byte data[] = new byte[BUFFER];
				// get a list of files from current directory

				for (int i = 0; i < files.size(); i++) {
					// System.out.println("Adding: " + files[i]);
					File rawFile = new File(path, files.get(i));
					if( rawFile.exists()) {
						FileInputStream fi = new FileInputStream(new File(path,
								files.get(i)));
						origin = new BufferedInputStream(fi);
						ZipEntry entry = new ZipEntry(files.get(i));
						out.putNextEntry(entry);
						int count;
						while ((count = origin.read(data, 0, BUFFER)) != -1) {
							out.write(data, 0, count);
						}
						origin.close();
					}
				}
				out.close();
				dest.close();

				for (int i = 0; i < files.size(); i++) {
					File f = new File(path, files.get(i));
					f.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}    
}
