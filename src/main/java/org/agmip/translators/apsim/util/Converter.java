package org.agmip.translators.apsim.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.agmip.translators.apsim.ApsimOutput;
import org.agmip.translators.apsim.core.DailyWeather;
import org.agmip.translators.apsim.core.SimulationRun;
import org.agmip.translators.apsim.core.Weather;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;


public class Converter {

	
	public static void generateWeatherFiles(File path, Weather s) throws Exception{
			if(s.shortName==null) s.shortName = "default"; //throw new Exception("Cant create file. Station short name missing");
			
	        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path,s.shortName+".met"))));
	        br.write("!title: ");
	        br.write(s.longName);
	        br.newLine();
	        br.write("tav = ");
	        br.write(s.averageTemperature);
	        br.newLine();
	        br.write("amp = ");
	        br.newLine();
	        br.write("year day radn maxt mint rain wind dewp vers rh \n");
	        br.write("()    ()   (MJ/m2) (oC)  (oC)  (mm)  (km)  (oC)   ()   (%)\n");

	        for(DailyWeather r : s.records){
        	    br.write(GetYear(r.date)+" ");
        	    br.write(GetDay(r.date)+" ");
        	    br.write(r.solarRadiation +" ");
        	    br.write(r.maxTemperature +" ");
        	    br.write(r.minTemperature +" ");
        	    br.write(r.rainfall +" ");
        	    br.write(r.windSpeed +" ");
        	    br.write(r.dewPoint +" ");
        	    br.write(r.vaporPressure +" ");
        	    br.write(r.relativeHumidity +" ");
    	        br.newLine();
	        }
	        
	        br.close();
	    }
	

	public static void generateAPSIMFile(File path, SimulationRun sim) throws Exception{
        // Initialise velocity
        Velocity.init();
        VelocityContext context = new VelocityContext();
        context.put( "experiment", sim);

        Template template = Velocity.getTemplate("src\\main\\resources\\AgMIPTemplate.apsim");
        FileWriter F;
        try {
            
            F = new FileWriter(new File(path,sim.experimentName + ".apsim"));
            template.merge( context, F );
            F.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ApsimOutput.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	
   public static String GetYear(String w_date) {
		String[] part = w_date.split("-");
		return part[0];
	}
	
   public static String GetDay(String w_date) {
		String[] part = w_date.split("-");
		
		
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(part[2]));
        c.set(Calendar.MONTH, Integer.parseInt(part[1])-1);
        c.set(Calendar.YEAR, Integer.parseInt(part[0]));
        return c.get(Calendar.DAY_OF_YEAR) +"";
	}
   

}
