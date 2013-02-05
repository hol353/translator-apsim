package org.agmip.translators.apsim;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.agmip.core.types.TranslatorOutput;
import org.agmip.translators.apsim.core.SimulationCollection;
import org.agmip.translators.apsim.core.SimulationRun;
import org.agmip.translators.apsim.core.Weather;
import org.agmip.translators.apsim.util.Util;
import static org.agmip.util.JSONAdapter.toJSON;
import org.agmip.util.MapUtil;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.codehaus.jackson.map.ObjectMapper;


/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @author Chris Villalobos, AgMIP IT
 * @since Jul 13, 2012
 */

public class ApsimOutput implements TranslatorOutput {
	static final int BUFFER = 2048;


	@SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
	public void writeFile(String filePath, Map input) {
		File path = new File(filePath);
		ObjectMapper mapper = new ObjectMapper();
		try {

			String temp = toJSON(MapUtil.decompressAll(input));

			ArrayList<String> files = new ArrayList<String>();

			SimulationCollection collection = mapper.readValue(temp,SimulationCollection.class);
			collection.initialise();
            
			generateMetFiles(path, collection, files);
            generateAPSIMFile(path, collection, files);
            
			BufferedInputStream origin = null;

			if (files.size() > 1) {

				File zipfile = new File(path, collection.getExperiments().firstElement().getExperimentName() + "_apsim.zip");

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

    public static void generateMetFiles(File path, SimulationCollection collection, ArrayList<String> files) throws Exception {
        path.mkdirs();
        for(Weather weather:collection.getWeathers()){
            String fileName = weather.getName()+".met";
            File file = new File(path, fileName);
            files.add(fileName);
            file.createNewFile();
            Velocity.init();
            VelocityContext context = new VelocityContext();
            context.put("weather", weather);
            FileWriter writer = new FileWriter(file);
            Velocity.evaluate(context, writer, "Generate Met", Util.class.getClassLoader().getResourceAsStream("template.met"));
            writer.close();
        }
    }
    
    
    public static void generateAPSIMFile(File path, SimulationCollection collection, ArrayList<String> files)
            throws Exception {
        path.mkdirs();
        File file = new File(path, "AgMip.apsim");
        files.add("AgMip.apsim");        
        file.createNewFile();
        Velocity.init();
        VelocityContext context = new VelocityContext();
        FileWriter writer;
        try {
            context.put("collection", collection);
            writer = new FileWriter(file);
            Velocity.evaluate(context, writer, "Generate APSIM", Util.class.getClassLoader().getResourceAsStream("template.apsim"));
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }    
}
