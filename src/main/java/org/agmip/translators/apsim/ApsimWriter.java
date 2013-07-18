package org.agmip.translators.apsim;

import static org.agmip.util.JSONAdapter.toJSON;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.agmip.core.types.TranslatorOutput;
import org.agmip.translators.apsim.core.ACE;
import org.agmip.translators.apsim.core.Simulation;
import org.agmip.translators.apsim.core.Weather;
import org.agmip.translators.apsim.util.Util;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @author Chris Villalobos, AgMIP IT
 * @since Jul 13, 2012
 */

public class ApsimWriter implements TranslatorOutput {
	static final int BUFFER = 2048;

	
	// Convert a JSON string into a collection of simulations
    public static ACE jsonToACE(String json) throws Exception{       
		ObjectMapper mapper = new ObjectMapper();
        ACE ace = mapper.readValue(json, ACE.class);
        ace.initialise();
        return ace;
    }	

    @SuppressWarnings({"rawtypes"})
    @Override
    public void writeFile(String filePath, Map input) {

        try {
            ACE ace = jsonToACE(toJSON(input));
            File path = new File(filePath);

            // Check to see which files should be generated.
            ArrayList<String> files = new ArrayList<String>();
            if (!ace.getWeathers().isEmpty()) {
                generateMetFiles(path, ace, files);
            }

            if (ace.getSoils().size() > 0 || ace.getExperiments().size() > 0) {
                int maxRun = 1500;
                if (ace.getExperiments().size() <= maxRun) {
                    generateAPSIMFile("AgMip.apsim", path, ace, files);
                } else {
                    List<Simulation> exps = new ArrayList<Simulation>();
                    exps.addAll(ace.getExperiments());
                    
                    for (int i = 0; i * maxRun < exps.size(); i++) {
                        ace.setExperiments(exps.subList(i * maxRun, Math.min((i + 1) * maxRun, exps.size())));
                        generateAPSIMFile(String.format("AgMip%02d.apsim", i + 1), path, ace, files);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void generateMetFiles(File path, ACE ace, ArrayList<String> files) throws Exception {
        path.mkdirs();
        for(Weather weather:ace.getWeathers()){
            String fileName = weather.getName()+".met";
            File file = new File(path, fileName);
            files.add(fileName);
            file.createNewFile();
            Velocity.init();
            VelocityContext context = new VelocityContext();
            context.put("weather", weather);
            FileWriter writer = new FileWriter(file);
            Reader R = new InputStreamReader(Util.class.getClassLoader().getResourceAsStream("template.met"));
            Velocity.evaluate(context, writer, "Generate Met", R);
            writer.close();
        }
    }


    public static void generateAPSIMFile(String fileNameToGenerate, File path, ACE ace, ArrayList<String> files)
            throws Exception {
        path.mkdirs();
        File file = new File(path, fileNameToGenerate);
        files.add(fileNameToGenerate);
        file.createNewFile();
        Velocity.init();
        VelocityContext context = new VelocityContext();
        FileWriter writer;
        try {
            context.put("collection", ace);
            writer = new FileWriter(file);
            Reader R = new InputStreamReader(Util.class.getClassLoader().getResourceAsStream("template.apsim"));
            Velocity.evaluate(context, writer, "Generate APSIM", R);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
